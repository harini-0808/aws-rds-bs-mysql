package com.handson.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.handson.jpa.model.Product;
import com.handson.jpa.service.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
	
	@Autowired
	ProductService service;

//	@PostMapping("/product")
	@PostMapping
	@RequestMapping(path="product",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addProduct(@RequestBody Product product){
		return ResponseEntity.status(201).body(service.save(product));
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> getProducts(){
		return ResponseEntity.status(200).body(service.getProducts());
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable long id){
		Product product = service.getProductByID(id);
		if(product==null)
		return ResponseEntity.status(404).body("no such product :( ");
		return ResponseEntity.status(200).body(product);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProductByID(@PathVariable long id){
		if(service.deleteProductByID(id))
			return ResponseEntity.status(200).body("Successfully deleted :)");
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no such product found :( ");
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable long id,@RequestBody Product product){
		product.setProductID(id);
		if(service.updateProduct(id, product)==null)
			return ResponseEntity.status(404).body("no such product");
		return ResponseEntity.status(200).body(product);
	}
}
