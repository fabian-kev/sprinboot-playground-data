package com.fabiankevin.springboot_jpa_stream.web;

import com.fabiankevin.springboot_jpa_stream.services.CustomerServiceUsingStream;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceUsingStream customerService;

    @PostMapping("/batch")
    void startBatch(){
        customerService.process();
    }
}
