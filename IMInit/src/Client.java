
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.TBranch;
import model.TBranchMember;
import model.TCity;
import model.TMember;
import model.TOrgan;
import model.TPosition;
import model.TProvince;
import model.TRolePriv;
import model.TSex;

/**
 * hibernate3操作方式
 */
public class Client {
	private static SessionFactory factory = null;

	public static void main(String[] args) {
		init();
		//initSex();
		//initProvince();
		//initCity();
		//initOrgan();
		//initBranch();
		//initPosition();
		//initSuperMember();
		initRolePrivilege();
	}

	public static void init() {
		Configuration cfg = new Configuration();
		cfg.addResource("model/TCity.hbm.xml");
		cfg.addResource("model/TProvince.hbm.xml");
		cfg.addResource("model/TSex.hbm.xml");
		cfg.addResource("model/TOrgan.hbm.xml");
		cfg.addResource("model/TBranch.hbm.xml");
		cfg.addResource("model/TBranchMember.hbm.xml");
		cfg.addResource("model/TPosition.hbm.xml");
		cfg.addResource("model/TMember.hbm.xml");
		cfg.addResource("model/TRolePriv.hbm.xml");
		factory = cfg.buildSessionFactory();
	}
	
	public static void initRolePrivilege() {
		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();
			for(int i = 15; i <= 53; i++) {
				TRolePriv tp = new TRolePriv();
				tp.setPrivId(i);
				tp.setRoleId(1);
				session.save(tp);
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}


	// 初始化职位
	public static void initPosition() {
		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();
			TPosition tp = new TPosition();
			tp.setListorder(1);
			tp.setName("董事长");
			tp.setOrganId(1);
			session.save(tp);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}
	
	// 初始化超级管理员
	public static void initSuperMember() {
		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();
			TMember tm = new TMember();
			tm.setAccount("admin");
			tm.setAddress("");
			tm.setBirthday("");
			tm.setEmail("");
			tm.setFullname("admin");
			tm.setGroupmax(100);
			tm.setGroupuse(0);
			tm.setIntro("");
			tm.setLogo("");
			tm.setMobile("");
			tm.setOrganId(1);
			tm.setPassword("21232f297a57a5a743894a0e4a801fc3");
			tm.setPinyin("admin");
			tm.setSex("1");
			tm.setTelephone("");
			tm.setWorkno("");
			session.save(tm);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}

	// 初始化部门
	public static void initBranch() {
		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();
			TBranch tb = new TBranch();
			tb.setOrganId(1);
			tb.setParentId(0);
			tb.setName("管理部");
			tb.setManagerId(10001);
			tb.setAddress("");
			tb.setWebsite("");
			tb.setTelephone("");
			tb.setFax("");
			tb.setIntro("");
			tb.setListorder(1);
			session.save(tb);
			
			TBranchMember tm = new TBranchMember();
			tm.setBranchId(101);
			tm.setIsMaster("1");
			tm.setListorder(1);
			tm.setMemberId(10001);
			tm.setPositionId(1);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}

	// 初始化组织,待补全
	public static void initOrgan() {

		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();
			TOrgan to = new TOrgan();
			to.setCode("tfxx");
			to.setName("天坊信息");
			to.setShortname("tfxx");
			to.setEnglishname("tfxx");
			to.setLogo("");
			to.setDomain("");
			to.setProvinceId(1);
			to.setCityId(1);
			to.setAddress("");
			session.save(to);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}

	// 初始市
	public static void initCity() {
		String provinceFile = "Cities.xml";
		Map<String, String> privinceMap = new ContextUtils().load(provinceFile);

		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();

			for (Map.Entry<String, String> pm : privinceMap.entrySet()) {
				String key = pm.getKey();
				int id = 0;
				int pid = 0;

				if (key.indexOf("_") != -1) {
					String[] split = key.split("_");
					id = Integer.parseInt(split[0]);
					pid = Integer.parseInt(split[1]);
				}

				TCity tp = new TCity();
				tp.setProvinceId(pid);
				tp.setName(pm.getValue());
				tp.setListorder(id);
				session.save(tp);
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}

	// 初始省
	public static void initProvince() {
		String provinceFile = "Provinces.xml";
		Map<String, String> privinceMap = new ContextUtils().load(provinceFile);

		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();

			for (Map.Entry<String, String> pm : privinceMap.entrySet()) {
				TProvince tp = new TProvince();
				tp.setName(pm.getValue());
				tp.setListorder(Integer.parseInt(pm.getKey()));
				session.save(tp);
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}

	// 初始性别
	public static void initSex() {

		Session session = null;

		try {
			session = factory.openSession();
			session.beginTransaction();
			String[] sex = { "男", "女" };

			for (int i = 0; i < 2; i++) {
				TSex tsex = new TSex();
				tsex.setName(sex[i]);
				tsex.setListorder(i + 1);
				session.save(tsex);
			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				if (session.isOpen()) {
					session.close();
				}
			}
		}
	}
}