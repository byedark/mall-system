package com.xiatian.mallproduct.controller;

import com.xiatian.mallproduct.utils.R;
import com.xiatian.mallproduct.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;

import javax.annotation.Resource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandRelationControllerTest {

    @Resource
    BrandRelationController brandRelationController;

    @Test
    void brandsList() {
        R result = brandRelationController.brandsList(225L);
        System.out.println(result);
    }
}