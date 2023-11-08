package com.xiatian.mallproduct;

import com.xiatian.mallproduct.entity.Brand;
import com.xiatian.mallproduct.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallProductApplicationTests {
    @Autowired
    BrandService brandService;
    @Test
    void contextLoads() {
        //直写brand警告
        Brand brand = new Brand();
        brand.setName("华为");
        brandService.save(brand);
    }

}
