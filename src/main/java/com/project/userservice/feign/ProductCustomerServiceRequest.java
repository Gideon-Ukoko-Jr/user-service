package com.project.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "product-service")
public interface ProductCustomerServiceRequest {

    @PostMapping("/add")
    void createCustomer(@RequestBody Object requestBody);
}
