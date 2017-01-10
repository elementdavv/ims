package com.sealtalk.model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.sealtalk.common.BaseDao;
import com.sealtalk.service.member.MemberService;
import com.sealtalk.service.member.impl.MemberServiceImpl;

public class HibernateTest extends BaseDao{

	@Test
	public void hibernateAcc() {
		TMember m = new TMember();
		m.setAccount("admin");
		m.setPassword("123");
		Configuration cf = new AnnotationConfiguration();
		SessionFactory sf = cf.configure().buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
		session.close();
		sf.close();
	}
	
	@Test
	public void hibernateSpring(){
		TMember m = new TMember();
		m.setAccount("admin");
		m.setPassword("123");
		MemberService us = new MemberServiceImpl();
		//us.add(m);
	}
}