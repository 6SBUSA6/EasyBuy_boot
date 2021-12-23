package com.gyw.controller;

import com.gyw.bean.BuyProduct;
import com.gyw.bean.BuyProductCategory;
import com.gyw.bean.BuySeller;
import com.gyw.service.ProductCategoryService;
import com.gyw.service.ProductService;
import com.gyw.service.SellerService;
import com.gyw.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author 高源蔚
 * @date 2021/12/14-16:27
 * @describe
 */
@Controller
@RequestMapping("/seller")
public class SellerController {
    @Reference(url = "dubbo://localhost:20881")
    SellerService sellerService;

    @Reference(url = "dubbo://localhost:20882")
    ProductCategoryService productCategoryService;

    @Reference(url = "dubbo://localhost:20882")
    ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(SellerController.class);

    @RequestMapping("/isLogin")
    @ResponseBody
    public Result isLogin(HttpSession session){
        logger.info("这是判断是否登录");
        BuySeller loginSeller =(BuySeller) session.getAttribute("loginSeller");
        if(loginSeller == null){
            logger.info("没有登录");
            return Result.getPageResult(0,"false","没有登录",null);
        }else {
            logger.error("登录了");
            return Result.getPageResult(1,"true","登录了",loginSeller);
        }
    }

    @RequestMapping("/login")
    public String login(BuySeller seller, HttpSession session)
    {
        try {
            logger.info("这是登录"+seller);
            BuySeller loginSeller = sellerService.login(seller.getName());
            if(loginSeller == null || !seller.getPassword().equals(loginSeller.getPassword()))
            {
                System.out.println("登录失败");
                session.setAttribute("msg","用户或者密码错误");
                return "redirect:/login.html";
            }else {
                System.out.println("登录成功");
                session.removeAttribute("msg");
                session.setAttribute("loginSeller",loginSeller);
                return "redirect:/main.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录失败");
        }
        return "forward:/login.html";
    }

    @GetMapping ("/findChildByPid")
    @ResponseBody
    public Result findChildByPid(Integer pid){
        logger.info("这是查找子标签的controller");
        Result result = null;
        try {
            List<BuyProductCategory> categoryList = productCategoryService.findCategoryByPid(pid);
            result = Result.getPageResult(1, "true", "查询成功", categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public Map<String,Object> uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> erroMap = new HashMap<>();
        try {
            // 文件保存目录路径
            String savePath = "D:/IdeaWorkSpace/EasyBuy_Boot/EeasyBuy_seller_web/src/main/resources/static/uploadImg/";
            String saveUrl  = request.getContextPath() + "/images/";
            logger.info("土拍你上传");
            // 定义允许上传的文件扩展名
            HashMap<String, String> extMap = new HashMap<String, String>();
            extMap.put("image", "gif,jpg,jpeg,png,bmp");
            // 最大文件大小
            long maxSize = 1000000;
            response.setContentType("text/html; charset=UTF-8");

            File uploadDir = new File(savePath);
            // 判断文件夹是否存在,如果不存在则创建文件夹
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String dirName = request.getParameter("dir");
            if (dirName == null) {
                dirName = "image";
            }
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();
            String fileName = null;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();
                fileName = mFile.getOriginalFilename();
                // 检查文件大小
                if (mFile.getSize() > maxSize) {
                    erroMap.put("erro",1);
                    erroMap.put("message","图片过大");
                    return erroMap;
                }
                String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
                if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                    erroMap.put("erro",1);
                    erroMap.put("message","扩展名不对");
                    return erroMap;
                }
                UUID uuid = UUID.randomUUID();

                savePath = savePath  + uuid.toString() +"."+ fileExt;
                saveUrl +=uuid.toString() +"."+ fileExt;
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(savePath));
                FileCopyUtils.copy(mFile.getInputStream(), outputStream);
                String serverPath="D:/IdeaWorkSpace/EasyBuy_Boot/EeasyBuy_seller_web/target/classes/static/uploadImg/";
                serverPath = serverPath  + uuid.toString() +"."+ fileExt;
                FileCopyUtils.copy(new File(savePath),new File(serverPath));
                logger.info("图片上传成功");
                /*JSONObject obj = new JSONObject();*/
                erroMap.put("error", 0);
                erroMap.put("url", saveUrl);
                return erroMap;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        erroMap.put("erro",1);
        erroMap.put("message","图片上传异常");
        return erroMap;
    }

    @RequestMapping("/layuiUpload")
    @ResponseBody
    public Object layuiUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        //String path = request.getServletContext().getRealPath("/resources/upimg/");
        String path = "D:\\IdeaWorkSpace\\EasyBuy_Boot\\EeasyBuy_seller_web\\src\\main\\resources\\static\\uploadImg\\";
        try {
            file.transferTo(new File(path + file.getOriginalFilename()));
            map.put("imgurl", file.getOriginalFilename());
            map.put("msg", "上传成功！");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return map;
    }

    @RequestMapping("/addProduct")
    public String addProduct(HttpSession session,BuyProduct product, MultipartFile image,@RequestParam("imgs") List<String> imgs){
        logger.info("这是添加一个新的产品"+product);
        try {
            BuySeller loginSeller = (BuySeller) session.getAttribute("loginSeller");
            if(loginSeller == null){
                //没有登录
                return "redirect:/login.html";
            }
            product.setSellerId(loginSeller.getSellerId());
            String originalFilename = image.getOriginalFilename();
            String fileTx = originalFilename.substring(originalFilename.lastIndexOf("."));
            String path = "D:\\IdeaWorkSpace\\EasyBuy_Boot\\EeasyBuy_seller_web\\src\\main\\resources\\static\\uploadImg";
            String newName = UUID.randomUUID().toString()+fileTx;
            String newPath= path+"\\"+newName;
            image.transferTo(new File(newPath));
            product.setFilename(newName);
            logger.info("添加产品成功");
            productService.addProduct(product,imgs);
            return "redirect:/main.html";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加产品异常");
        }
        return "redirect:/main.html";
    }

}
