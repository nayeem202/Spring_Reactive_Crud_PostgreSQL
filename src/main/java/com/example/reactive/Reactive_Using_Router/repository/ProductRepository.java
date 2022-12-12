package com.example.reactive.Reactive_Using_Router.repository;

import com.example.reactive.Reactive_Using_Router.dto.ProductDto;
import com.example.reactive.Reactive_Using_Router.entity.Product;
//import com.javaTechie.reactive.dto.ProductDto;
//import com.javaTechie.reactive.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository <Product, String>{
    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);

}
