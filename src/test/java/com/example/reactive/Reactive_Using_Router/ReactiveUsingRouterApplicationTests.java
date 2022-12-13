package com.example.reactive.Reactive_Using_Router;

import com.example.reactive.Reactive_Using_Router.controller.productController;
import com.example.reactive.Reactive_Using_Router.dto.ProductDto;
import com.example.reactive.Reactive_Using_Router.router.RouterConfig;
import com.example.reactive.Reactive_Using_Router.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
//@WebFluxTest(RouterConfig.class)
class ReactiveUsingRouterApplicationTests {

	@Autowired(required = true)
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;


	// add/save products
	@Test
	public void addProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "mobile", 1, 10000));
		when(productService.saveProduct(productDtoMono)).thenReturn(productDtoMono);
		webTestClient.post().uri("/router/products/save")
				.exchange()
				//.expectStatus().isOk(); // 200
				.expectBody();

	}

	//get all products
	@Test
	public void getProductsTest() {
		Flux<ProductDto> productDtoFlux = Flux.just(new ProductDto("3", "Mouse", 2, 350),
				new ProductDto("4", "Tv", 7, 14000));
		when(productService.getProducts()).thenReturn(productDtoFlux);

		Flux<ProductDto> responseBody = webTestClient.get().uri("/router/products/getAllProducts").exchange()
				.expectStatus().isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("3", "Mouse", 2, 350))
				.expectNext(new ProductDto("4", "Tv", 7, 14000))
				.verifyComplete();
	}


	//get single products
	@Test
	public void getProduct() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("3", "Mouse", 2, 350));
		when(productService.getProduct(any())).thenReturn(productDtoMono);

		Flux<ProductDto> responseBody = webTestClient.get().uri("/router/getProduct/102" )
				.exchange()
				.expectStatus().isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("3", "Mouse", 2, 350))
				.verifyComplete();
	}


	// update products
	@Test
	public void updateProducts() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Keyboard", 3, 700));
		when(productService.updateProduct(productDtoMono, "1")).thenReturn(productDtoMono);
		webTestClient.put().uri("/router/updateProduct/1")
				.body(Mono.just(productDtoMono), ProductDto.class)
				.exchange()
				//.expectStatus().isOk()
				.expectBody();
	}

	@Test
	public void deleteProduct() {
		//ono<ProductDto> productDtoMono = Mono.just(new ProductDto("1", "Keyboard", 2, 709));
		when(productService.deleteProduct("1")).thenReturn(Mono.empty());
		webTestClient.delete().uri("/products/delete/1")
				.exchange()
				.expectStatus().isOk();
	}

//		given(productService.deleteProduct(any())).willReturn(Mono.empty());
//		webTestClient.delete().uri("router/deleteProduct/1")
//				.exchange()
//				.expectStatus().isOk();


}