package com.xiatian.mallproduct;

import com.aliyun.oss.OSSClient;
import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.Resource;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
class MallProductApplicationTests {

    @Resource
    OSSClient ossClient;

    @Test
    public void testUpload() throws IOException {

        // 通过client发起上传文件的请求
        String bulkName ="mall-xiatian";
        String objectName= "5b5e74d0978360a1.jpg"; //文件的唯一标识，通常是不重复的文件名
        InputStream in = Files.newInputStream(
                Paths.get("C:\\Users\\XT189\\Desktop\\pics\\5b5e74d0978360a1.jpg"));
        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（key）。
        ossClient.putObject(bulkName, objectName, in);
        // 关闭连接
        ossClient.shutdown();
    }
    @Test
    public void testRedis(){
        //三步即可使用
        //导入包
        //配置host
        //Stringredistemplate




        return ;
    }
}
