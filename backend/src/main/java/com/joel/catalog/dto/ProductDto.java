package com.joel.catalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.joel.catalog.entities.Category;
import com.joel.catalog.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 5 , max = 60, message = "Deve ter entre 5 e 60 caracteres")
	@NotBlank(message = "Campor requerido")
	private String name;
	
	@NotBlank(message = "Campo requerido")
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Positive(message = "O preço deve ser um valor positivo.")
	private Double price;
	
	private String imgUrl;
	
	
	@PastOrPresent(message = "A data do produto não pode ser futura.")
	private Instant date;
	
	private List<CategoryDto> categories = new ArrayList<>();
	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
		this.date = product.getDate();
	}
	
	public ProductDto(Product product, Set<Category> categories) {
		this(product);
		categories.forEach(category -> this.categories.add(new CategoryDto(category)));
	}

}











