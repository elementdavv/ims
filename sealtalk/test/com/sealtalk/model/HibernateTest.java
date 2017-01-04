package com.sealtalk.model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.sealtalk.common.BaseDao;
import com.sealtalk.model.User;
import com.sealtalk.service.UserService;
import com.sealtalk.service.impl.UserServiceImpl;

public class HibernateTest extends BaseDao{

	@Test
	public void hibernateAcc() {
		User u = new User();
		u.setUserName("zs");
		u.setUserPwd("123");
		Configuration cf = new AnnotationConfiguration();
		SessionFactory sf = cf.configure().buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(u);
		session.getTransaction().commit();
		session.close();
		sf.close();
	}
	
	@Test
	public void hibernateSpring(){
		User u = new User();
		u.setUserName("zs");
		u.setUserPwd("123");
		UserService us = new UserServiceImpl();
		us.add(u);
	}
}
