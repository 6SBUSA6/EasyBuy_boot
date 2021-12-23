package com.gyw.util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 高源蔚
 * @date 2021/12/21-11:06
 * @describe
 */
public class AlipayConfig {
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000118637521";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCpn8jLvXWUu9ZCtaqHxgcj6kxWFpFjC0Czl8OJJDdLLb9wXZxLtj8FwW1nuO9mZSjgU9szjnovYnWWdlNUtrhBa7dyk+q14xiVD2/zgP1wss2TYfem0Id7oYSdViuXJ0arBmPfz3QYlYEzfvqw4/vnxOqEcj1p+Ltxo8+eD1e6Dc/fVYht0rxUwM9KnNw/b5uf6CZC5oIFHyszTAv2BEunOQ4oWW9TdVu10OrrvkwyuoPGyIrD+H7Ud5yt5t/fzt5VK853Grm4epgDiW+XHZbaVyizEJCbsDAf0ay8wAGH5/6ygeqoDAso41p7toFkdW7mgYQ+2Qstnjl1dYM0Y0AHAgMBAAECggEAJo8uJDKTchYpJM0LUQYRRXbTl5ZVwoAiWzEawCg9cZeatz5wcz2gofxQlI2TzmEco7IsWFIBMJhfRq7rToyi4ixMbX12k5ZmVixGNMKT2nSrCt/wXjl1MwOKlAGssxW4mNRBC/Wq3JPP5RbughuxfgRjk2wszdQ/OBwW7EVsmdx+uPmVMc6oyfJhM1hysrZRL2FDNyf91BNlWc19yF8CW6Av8GRfMPQejanR1tItTeoLSaW62eaSyRG2raMG5QcyieE93u7GxL3UWJA100N4GKON/LNtyYEU4E1+R709em4YBEd3OtbC3lPJnS0XBLKztFHA8u3K6E2CkbtrXCaBAQKBgQDmJtpv2p+dATI73vSW0OAYGbMnPl707nlVPhRhOvgQOq+nI0cLnEhxrJ058AWKVX99LeYOzmmy9v05fXXKIsTJc3vEWFtXqHFrYkwFFZ99gu0h3a/Szz740azbGXjP//W8p+DcgWdE64n/nIy2uZOg65nP+EatPrSft5Mf1g8KxwKBgQC8rLANfCRVlN5bPDPVwOL1a/ovFwhXglVBQ/jirrsUPBHhoaSGeOE/GRuBaIcGqlgdLoBSyzo93eHEj+sc9F5kLqOjJ0s6Rh6ILUancpbjfIlb0ZuI9d+73pUEaT2Mvy52GeWgPW3cYz6eUVGEQMu763vN5tjJrBiH6xPBO3LgwQKBgG+eWEH3HYHfmqU+tBCQrJ4DmMdnkFXpch7NRMUC/Fuimp3MliAjY17+ACKhLY6xeGsVX4W+xbRt5NGE7yNhSV8KCvRmhaY1owwZWBT/L6KHZMoP4b2zlS7HQbXg3J7D3EHqiTtn5tWeBgdZvUTBK7/Xg3lBR8Wtq5vqRAgkoJbxAoGAaBtZYXd4jQiB0RHdADtYgFc+SzGIVtEBveuFr37+l2G19Cw/Hjwg/bIa7UMCDnl7+U4pXIXq8yw1YUKQVv+3/cuGW4ZZCiSawEVLUSX3gDq9iD6WdfJFq+Jbk32Git0LA/uyKsL6lbrlWHJCapleUajUPX79a/pb5tOdqOfmsYECgYATaZgS9AVB6jZpQTOVmBDd/aS6ng1ME07gdSjnqRaApUPLpXuRnHGvJ6kB3UdOTz5YzPv5FEZE/86cdYkWICRm98umbvc7Uu9Tty685Sp478I/tsG6hUPEJdSSVCpoXy2gtP8qL59bco58OBLzd7JrXKdvzzROjZFAvy5nM0m2tg==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAht1oAqpYYx10APLulX4qf8H4jbIdYUjOYq0nv1gCoPXta2+ft2SZOdpX+l78ukPfZS7p4eP9JHl0rURXNTfQlc7GfiZTffWX24elu4CfxpvwOEK6w3MU2fYi0ESWaHBLqQRcZd2/g5DRHyMcd+j1vZG7oOPfba+BmOFfN5pSKXX7GTn8HezYFa5bnDoOe5Zq7s2xHNPX2qKW9l+/pWjZ9U+2bRqnM0pQzKSFDSTcr7vOXjhBRBgwqjYAxDkAJJIsWRCVT4n/jOoqEHIxfPs7J2Q1yNFr3uOz7fC6RxQCP41nyO9pqnkOvOkXNdoinb/hDfRcytRTyD/gQOjijwLp8wIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8092/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8092/order/return";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
