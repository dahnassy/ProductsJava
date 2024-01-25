package com.voxesoftware.springboot.controllers;

import com.voxesoftware.springboot.dtos.ProductRecordDto;
import com.voxesoftware.springboot.models.ProductModel;
import com.voxesoftware.springboot.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.voxesoftware.springboot.controllers.ProductController;

import java.util.List;
import java.util.Objects;


@RestController
public class ListProductController{
    @Autowired
    ProductRepository productRepository;
    @PostMapping("/List")
    @Operation(summary = "Criar Produtos")
    public void saveListProduct(@RequestBody List<ProductRecordDto> productRecordDtos){
        var productModel = new ProductModel();
        for(var product : productRecordDtos) {
            BeanUtils.copyProperties(product, productModel);
            System.out.println(productModel.getNome());
            new ProductController().saveProduct(product);

        }

    }



}
