package com.example.dispatch.controller;

import com.example.dispatch.entity.FillData;
import com.example.dispatch.serivice.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

/**
 * @author liuhp
 * @Description 接口层 通过调用接口运行函数
 * @date 2022/2/17 22:15
 */
@RestController()
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    @GetMapping("/list")
    public List<FillData> list() throws IOException {
        return dispatchService.read();
    }
}
