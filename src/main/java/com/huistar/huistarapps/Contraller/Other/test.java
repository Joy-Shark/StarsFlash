package com.huistar.huistarapps.Contraller.Other;

import com.huistar.huistarapps.Enty.Test;
import com.huistar.huistarapps.Mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class test {
    @Autowired
    private TestMapper testMapper;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/user")
    public List<Test> user(){
        return testMapper.selectList(null);
    }
}
