package com.example.demo.controller;

import com.example.demo.model.MyResponse;
import com.example.demo.model.StatisticData;
import com.example.demo.repository.MailListRepository;
import com.example.demo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistic")
public class StatisticController {
    @Autowired
    MailListRepository mailRepository;

    @Autowired
    ProductRepository repository;

    @GetMapping("")
    @Operation(summary = "Get report data")
    public MyResponse getProduct() {
        StatisticData data = new StatisticData();
        data.setReleasedProduct(repository.countByIsRelease(true));
        data.setUnReleasedProduct(repository.countByIsRelease(false));
        data.setSubscription(mailRepository.count());

        return MyResponse.ok(data);
    }
}
