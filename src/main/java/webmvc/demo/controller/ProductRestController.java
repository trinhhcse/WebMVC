package webmvc.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webmvc.demo.exception.ProductNotFoundException;
import webmvc.demo.model.Product;
import webmvc.demo.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> findProductById(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.FOUND).body(productService.findProductById(id));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<Object> create(@RequestBody @Valid Product product) {
		Product productSaved = productService.saveOrUpdate(product);
		return ResponseEntity.ok().body(productSaved);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") int id,@RequestBody Product product) {
		Product productSaved = productService.saveOrUpdate(product);
		return ResponseEntity.ok().body(productSaved);
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable int id) {
		try {
			productService.deleteProduct(id);
			return ResponseEntity.ok().build();
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
