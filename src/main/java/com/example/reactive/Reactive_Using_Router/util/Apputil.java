package com.example.reactive.Reactive_Using_Router.util;

import com.example.reactive.Reactive_Using_Router.dto.ProductDto;
//import com.javaTechie.reactive.dto.ProductDto;
//import com.javaTechie.reactive.entity.Product;
import com.example.reactive.Reactive_Using_Router.entity.Product;
import org.springframework.beans.BeanUtils;

public class Apputil {
    public static ProductDto entityToDto(Product product) {
        ProductDto productDto =  new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }


    public static Product dtoToEntity(ProductDto productDto) {
        Product product =  new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }

}
