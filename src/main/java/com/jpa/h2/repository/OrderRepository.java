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

import com.jpa.h2.entity.Order;

@Repository
public class OrderRepository implements IOrderRepository {

	Logger LOG = LoggerFactory.getLogger(OrderRepository.class);

	@PersistenceContext(unitName = "unitH2", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	private EntityManagerFactory emf;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Order findById(Long id) {
		LOG.info("find order by id {}", id);
		return this.getEm().find(Order.class, id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Order findByCode(String code) {
		LOG.info("find order by code {}", code);
		Query query = this.getEm().createQuery("select o from ORDER o where o.code = :code");
		query.setParameter("code", code);
		return (Order) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Order> listOrders() {
		LOG.info("list orders");
		Query query = this.getEm().createQuery("select os from ORDER os");
		return (List<Order>) query.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Order save(Order order) {
		if (order.getId() == null) {
			this.getEm().persist(order);
			LOG.info("create: ", order);
			return order;
		} else {
			LOG.info("update: ", order);
			return this.getEm().merge(order);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Order order) {
		Order oldOrder = findById(order.getId());
		this.getEm().remove(oldOrder);
	}

	public EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("testDS");
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