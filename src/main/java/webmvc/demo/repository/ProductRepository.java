package webmvc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webmvc.demo.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	Optional<Product> findById(int id);
	
}
