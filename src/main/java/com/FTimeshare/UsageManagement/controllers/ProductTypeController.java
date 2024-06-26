package com.FTimeshare.UsageManagement.controllers;


import com.FTimeshare.UsageManagement.dtos.ProductTypeDto;
import com.FTimeshare.UsageManagement.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
//@CrossOrigin(origins = "https://pass-timeshare.vercel.app")
//@CrossOrigin(origins = "https://pass-timeshare-tuandat-frontends-projects.vercel.app")
@RequestMapping("/api/productType")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping("/customer/viewproductType")
    public ResponseEntity<List<ProductTypeDto>> getAllProject() {
        List<ProductTypeDto> productType = productTypeService.getAllProductType();
        return ResponseEntity.ok(productType);
    }
}
