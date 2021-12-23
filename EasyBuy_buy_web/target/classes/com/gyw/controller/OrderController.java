package com.gyw.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gyw.bean.*;
import com.gyw.service.OrderService;
import com.gyw.service.ProductService;
import com.gyw.service.Userservice;
import com.gyw.util.AlipayConfig;
import javassist.bytecode.stackmap.BasicBlock;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author 高源蔚
 * @date 2021/12/20-21:46
 * @describe
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Reference(url = "dubbo://localhost:20884")
    OrderService orderService;

    @Reference(url = "dubbo://localhost:20883")
    Userservice userservice;

    @Reference(url = "dubbo://localhost:20882")
    ProductService productService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/addOrder")
    public void addOrder(BuyOrder order, @RequestParam("ids") List<Integer> ids, @CookieValue(required = false) String uuid, HttpServletResponse response, HttpServletRequest request, String ch){
        logger.info("这是添加订单");
        try {
            List<BuyOrderDetail> orderDetailsList = new ArrayList<>();
            BuyUser loginUser = userservice.getUserFromRedis(uuid);
            if(loginUser == null){
                System.out.println("没有登录不饿能结算");
                return;
            }
            //点击了新增地址
            //System.out.println("这是前端传过来的ck"+ch);
            if(ch == null || "".equals(ch)){
                //新增地址
                BuyUserAddress address = new BuyUserAddress();
                address.setUserid(loginUser.getId());
                address.setAddress(order.getUseraddress());
                userservice.addAddress(address);
            }
            Date date = new Date();
            order.setCreatetime(date);
            order.setLoginname(loginUser.getUsername());
            //从redis中获取提交表单的商品信息
            for (Integer id : ids) {
                BuyOrderDetail orderDetail = new BuyOrderDetail();
                CarItem carItem = productService.findCarItemFromRedisByPidAndUid(id,loginUser.getId());
                orderDetail.setCost(carItem.getBuyNum()*carItem.getProduct().getPrice());
                orderDetail.setQuantity(carItem.getBuyNum());
                orderDetail.setProductid(id);
                orderDetailsList.add(orderDetail);
            }
            Integer orderId = orderService.addOrder(order,orderDetailsList);
            //从redis中把购物车中的删除
            for (Integer id : ids) {
                productService.deleteFromCar(id,loginUser.getId());
            }
            //调用到支付宝的支付功能
            logger.info("调用到支付宝的支付功能");
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = orderId+"";
            //付款金额，必填
            String total_amount = order.getCost()+"";
            //订单名称，必填
            String subject = loginUser.getUsername()+"的订单,地址是在:"+order.getUseraddress();
            //商品描述，可空
            String productIds = "";
            for (Integer id : ids) {
                productIds+=id+";";
            }
            String body = "购买的商品有："+productIds;

            alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                    + "\"total_amount\":\""+ total_amount +"\","
                    + "\"subject\":\""+ subject +"\","
                    + "\"body\":\""+ body +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
            //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
            //		+ "\"total_amount\":\""+ total_amount +"\","
            //		+ "\"subject\":\""+ subject +"\","
            //		+ "\"body\":\""+ body +"\","
            //		+ "\"timeout_express\":\"10m\","
            //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();

            response.setContentType("text/html;charset=" + AlipayConfig.charset);
            response.getWriter().write(result);// 直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/return")
    public String Alipayreturn(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("进来了支付宝的回调");
        boolean signVerified = false; //调用SDK验证签名
        try {
            //获取支付宝GET过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map<String,String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);

            //——请在这里编写您的程序（以下代码仅作参考）——
            if(signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

                /*response.setContentType("text/html;charset=" + AlipayConfig.charset);
                response.getWriter().write("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);// 直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();*/
                //out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);


                model.addAttribute("out_trade_no",out_trade_no);
                model.addAttribute("trade_no",trade_no);
                model.addAttribute("total_amount",total_amount);
                System.out.println(out_trade_no+";"+trade_no+";"+total_amount);
                BuyOrder order = new BuyOrder();
                order.setSerialnumber(trade_no);
                order.setId(Integer.valueOf(out_trade_no));
                orderService.updateOrder(order);
                logger.info("支付成功");
                return "/BuyCar_Three.html";
            }else {
                logger.info("支付失败");
                return "erro.html";
            }
            //——请在这里编写您的程序（以上代码仅作参考）——
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("支付出现异常");
        }
        return "null";


    }


}
