package webmvc.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
//@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Basic
	@NotNull
	@Size(min = 1,max = 50)
	private String name;
	@Basic
	@DecimalMin("1.0")
	@DecimalMax("100000000.0")
	private double price;
	@Basic
	@Min(1)
	@Max(1000)
	private int quantity;
	@Basic

	private byte[] image;
}
