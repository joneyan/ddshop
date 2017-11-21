/*package com.yj.upload;

import com.yj.ddshop.common.util.FtpUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class FtpUploadTest {
    @Test
    public void testFTPClient() throws Exception{
        //创建一个FTP客户端对象
        FTPClient ftpClient = new FTPClient();
        //发送链接
        ftpClient.connect("192.168.59.128",21);
        //登录方法
        ftpClient.login("ftpuser1","ftp1234ftp");
        //封装一个输入流
        FileInputStream  file=new FileInputStream(new File("f:\\1111.png"));
        //配置上传参数
        //配置上传到的路径
        ftpClient.changeWorkingDirectory("/home/ftpuser1/www/images");

        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        ftpClient.storeFile("hello.png",file);
        file.close();
        ftpClient.logout();
    }


    @Test
    public  void testFtpUtils() throws Exception{
        FileInputStream fileInputStream = new FileInputStream(new File("f:\\1111.png"));
        FtpUtils.uploadFile("192.168.59.128",21,"ftpuser1","ftp1234ftp","/home/ftpuser1/www/images","/2017/11/170","hello2.png",fileInputStream);
        fileInputStream.close();
    }
}*/
