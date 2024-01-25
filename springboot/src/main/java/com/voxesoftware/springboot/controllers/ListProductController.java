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

import java.util.*;


@RestController
public class ListProductController{
    @Autowired
    ProductRepository productRepository;


    @PostMapping("/List")
    @Operation(summary = "Cadastra varios pedidos")
    public void saveListProduct(@RequestBody List<ProductRecordDto> productRecordDtos){
        ProductModel productModel = new ProductModel();
        List<ProductModel> productModels = new List<ProductModel>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<ProductModel> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(ProductModel productModel) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends ProductModel> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ProductModel> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public ProductModel get(int index) {
                return null;
            }

            @Override
            public ProductModel set(int index, ProductModel element) {
                return null;
            }

            @Override
            public void add(int index, ProductModel element) {

            }

            @Override
            public ProductModel remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<ProductModel> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ProductModel> listIterator(int index) {
                return null;
            }

            @Override
            public List<ProductModel> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        for(var product : productRecordDtos) {
            BeanUtils.copyProperties(product, productModel);
            System.out.println(productModel.getNome());
            //new ProductController().productRepository.save(productModel);
            //new ProductController().saveProduct(product);
            //this.productRepository.save(productModel);

            productModels.add(productModel);
            System.out.println(productModels.add(productModel));

        }

        this.productRepository.saveAll(productModels);

    }



}
