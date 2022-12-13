package com.example.reactive.Reactive_Using_Router.router;

import com.example.reactive.Reactive_Using_Router.handler.ProductHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;




    @Configuration
    public class RouterConfig {

    @Autowired(required = true)
    private ProductHandler producthandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/products/getAllProducts", producthandler::getProducts)
                .POST("/router/products/save", producthandler:: saveProduct)
                .GET("/router/getProduct/{id}" , producthandler::getProduct)
                .PUT("/router/updateProduct/{id}", producthandler::updateProducts)
                .GET("/router/priceRange", producthandler::productRange)
                .DELETE("router/deleteProduct/{id}", producthandler:: deleteProduct )
                .build();
    }

}
