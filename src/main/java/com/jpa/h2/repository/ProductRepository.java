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
			LOG.info("create: ", product);
			return product;
		} else {
			LOG.info("update: ", product);
			return this.getEm().merge(product);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Product product) {
		Product oldProduct = findById(product.getId());
		this.getEm().remove(oldProduct);
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

	public Image save(Product product, Image image) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void save(Product product, List<Image> images) {
		// TODO Auto-generated method stub
		
	}
	
	public void delete(Image image) {
		// TODO Auto-generated method stub
		
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