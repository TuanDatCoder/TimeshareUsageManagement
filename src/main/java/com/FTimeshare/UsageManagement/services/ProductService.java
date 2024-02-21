package com.FTimeshare.UsageManagement.services;

import com.FTimeshare.UsageManagement.dtos.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface  ProductService {
    List<ProductDto> getAllProducts();

}
