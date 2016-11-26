package com.jpa.h2.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity(name = "PRODUCT")
@Table(name = "PRODUCT")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String description;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="PRODUCT_IMAGE", joinColumns={@JoinColumn(name="ID_PRODUCT")}, inverseJoinColumns={@JoinColumn(name="ID_IMAGE")})
	private List<Image> images = new LinkedList<Image>();
	
	protected Product() {}
	
	public Product(String name, BigDecimal price) {
		this(name, price, null);
	}
	
	public Product(String name, BigDecimal price, String description) {
		Assert.hasText(name, "name can not be empty or null!");
		Assert.isTrue(BigDecimal.ZERO.compareTo(price) < 0, "price must be greater than zero!");

		this.name = name;
		this.price = price;
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	public void imagesClear() {
		images.clear();
	}
	
	public void addImages(Image image) {
		if (this.images == null) {
			images = new LinkedList<Image>();
		}
		this.images.add(image);
	}
	
	public void addProducts(List<Image> images) {
		this.images.addAll(images);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}
}