package com.voxesoftware.springboot.controllers;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.voxesoftware.springboot.dtos.ProductRecordDto;
import com.voxesoftware.springboot.models.ProductModel;
import com.voxesoftware.springboot.repositories.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    private ProductModel productModel;

    Date now = new Date(System.currentTimeMillis());


    //cadastrar produtos
    @PostMapping("/products")
    @Operation(summary = "Criar Produtos")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){

        ResponseEntity<List<ProductModel>> products = getAllProducts();
        var productModel = new ProductModel();

        //passando propriedades de validação do dto para o model
        BeanUtils.copyProperties(productRecordDto, productModel);

        //verificando condições e aplicando regras

        for(var product : products.getBody()) {
            if (Objects.equals(product.getNumeroControle(), productModel.getNumeroControle())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body((ProductModel) productRepository.findAll());
            }
        }

        if(productModel.getDataCadastro() == null){
            productModel.setDataCadastro(this.now.toString());
        }

        if(productModel.getQuantidade() == null || productModel.getQuantidade() == 0){
            productModel.setQuantidade(1);
        }
        if(productModel.getQuantidade() >= 10){
            BigDecimal valor = productModel.getValor().multiply(new BigDecimal(0.1));
            productModel.setValor(productModel.getValor().subtract(valor));
        }
        else if(productModel.getQuantidade() > 5){
            BigDecimal valor = productModel.getValor().multiply(new BigDecimal(0.05));
            productModel.setValor(productModel.getValor().subtract(valor));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    //pegar todos os produtos
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    //pegar produto por id
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    //atualizar produto
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    //deletar produto
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);

        if(product0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

}
