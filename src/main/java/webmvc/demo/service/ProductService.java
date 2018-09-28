package webmvc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webmvc.demo.exception.ProductNotFoundException;
import webmvc.demo.model.Product;
import webmvc.demo.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product findProductById(int id) throws ProductNotFoundException {
		return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
	}
	
	public Product saveOrUpdate(Product product) {
		return productRepository.save(product);
	}
	public void deleteProduct(int id) throws ProductNotFoundException {
		productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
		productRepository.deleteById(id);
	}
}
