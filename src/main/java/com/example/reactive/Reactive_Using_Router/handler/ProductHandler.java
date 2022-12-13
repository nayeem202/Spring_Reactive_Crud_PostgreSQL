package com.example.reactive.Reactive_Using_Router.handler;

import com.example.reactive.Reactive_Using_Router.dto.ProductDto;
import com.example.reactive.Reactive_Using_Router.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class ProductHandler {
    @Autowired
    private ProductService productService;

    public Mono<ServerResponse> getProducts (ServerRequest request){
        Flux<ProductDto> products = productService.getProducts();
        return ServerResponse.ok().body(products, ProductDto.class);
    }

    public Mono<ServerResponse> saveProduct (ServerRequest request){
        Mono<ProductDto> productDtoMono = request.bodyToMono(ProductDto.class);
        Mono<ProductDto> saveResponse = productService.saveProduct(productDtoMono);
        return ServerResponse.ok().body(saveResponse, ProductDto.class).log();
    }

    public Mono<ServerResponse> getProduct (ServerRequest request){
        String productid =  request.pathVariable("id");
        Mono<ProductDto> saveResponse = productService.getProduct(productid);
        return ServerResponse.ok().body(saveResponse, ProductDto.class);
    }

    public Mono<ServerResponse> updateProducts (ServerRequest request){
        String productId =  request.pathVariable("id");
        Mono<ProductDto> productDtoMono = request.bodyToMono(ProductDto.class);
        Mono<ProductDto> saveResponse = productService.updateProduct(productDtoMono, productId);
        return ServerResponse.ok().body(saveResponse,ProductDto.class);
    }


    public Mono<ServerResponse> deleteProduct (ServerRequest request){
        String productId =  request.pathVariable("id");
        Mono<Void> saveResponse = productService.deleteProduct(productId);
        return ServerResponse.ok().body(saveResponse, ProductDto.class);
    }


    public Mono<ServerResponse> productRange (ServerRequest request){

        Double min = Double.parseDouble(request.queryParam("min").get());
        Double max = Double.parseDouble(request.queryParam("max").get());

        Flux<ProductDto> productDtoFlux =  productService.getProductRange(min, max);
        return ServerResponse.ok().body( productDtoFlux, ProductDto.class);
    }

}
