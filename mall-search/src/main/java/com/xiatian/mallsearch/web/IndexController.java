package com.xiatian.mallsearch.web;

import com.xiatian.mallsearch.vo.SearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {


    @GetMapping(value = {"/","/list.html"})
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        return "list";
    }
}
