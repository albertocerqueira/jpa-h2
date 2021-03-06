package com.jpa.h2.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.h2.entity.Image;
import com.jpa.h2.entity.Product;

@Repository
public class ProductRepository implements IProductRepository {

	Logger LOG = LoggerFactory.getLogger(ProductRepository.class);

	@PersistenceContext(unitName = "unitH2", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	private EntityManagerFactory emf;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(List<Product> products) {
		for (Product product : products) {
			save(product);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Product save(Product product) {
		if (product.getId() == null) {
			this.getEm().persist(product);
			LOG.info("create: ", product.toString());
			return product;
		} else {
			LOG.info("update: ", product.toString());
			return this.getEm().merge(product);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Product product) {
		Product oldProduct = findById(product.getId());
		this.getEm().remove(oldProduct);
		LOG.info("delete: ", oldProduct.toString());
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		Product product = findById(id);
		this.getEm().remove(product);
		LOG.info("delete: ", product.toString());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Product> listProducts() {
		LOG.info("list products");
		Query query = this.getEm().createQuery("select ps from PRODUCT ps");
		return (List<Product>) query.getResultList();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Product findById(Long id) {
		LOG.info("find product by id {}", id);
		return this.getEm().find(Product.class, id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Product findByName(String name) {
		LOG.info("find product by name {}", name);
		Query query = this.getEm().createQuery("select p from PRODUCT p where p.name = :name");
		query.setParameter("name", name);
		return (Product) query.getSingleResult();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Image save(Image image, Product product) {
		if (image.getId() == null) {
			this.getEm().persist(image);
			LOG.info("create: ", image.toString());
			this.getEm().createNativeQuery("INSERT INTO PRODUCT_IMAGE (" + image.getId() + ", " + product.getId() + ")");
			return null;
		} else {
			LOG.info("update: ", image.toString());
			return this.getEm().merge(image);
		}
	}
	
	public void save(List<Image> images, Product product) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Image image) {
		Image oldImage = findImageById(image.getId());
		this.getEm().remove(oldImage);
		LOG.info("delete: ", oldImage.toString());
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteImage(Long id) {
		Product product = findById(id);
		this.getEm().remove(product);
		LOG.info("delete: ", product.toString());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Image findImageById(Long id) {
		LOG.info("find image by id {}", id);
		return this.getEm().find(Image.class, id);
	}

	public EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("unitH2");
		}
		return emf;
	}

	public EntityManager getEm() {
		if (em == null && null != getEmf()) {
			em = emf.createEntityManager();
		}
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}