package org.testTask.consoleQuery;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

enum RequestElements {
	QUERY(new String[] {"What is", "Who is"}),
	SHOW_COMMAND(new String[] {"Show"}),
	STATISTIC(new String[] {"statistic"}),
	SALARY(new String[] {"salary"}),
	DEPARTMENT(new RequestElementInitializer().getDepartments()),
	HEAD(new String[] {"head", "heads"}),
	SEARCH_COMMAND(new String[] {"Search"}),
	COUNT(new String[] {"Count"}),
	BY(new String[] {"by"}),
	STATISTICS(new String[] {"statistics"});//needed for search function
	
	String[] vocabulary;
	
	RequestElements(String[] list) {
		vocabulary = list;
	}
}

class RequestElementInitializer {
	List<String> departments = new LinkedList<>();
	
	public RequestElementInitializer(){
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
		//Actions go here
		//Transaction tx = null;
	    try {

	        S.beginTransaction();
	        
	        CriteriaBuilder CB = S.getCriteriaBuilder();
	        CriteriaQuery<DepartmentInfo> CQ = CB.createQuery(DepartmentInfo.class);
	        
	        CQ.from(DepartmentInfo.class);
	        
	        List<DepartmentInfo> list = S.createQuery(CQ).getResultList();
	        //System.out.println(list.get(0).getDepartmentName());
	        S.getTransaction().commit();
	        for(DepartmentInfo DI: list) {
	        	departments.add(DI.getDepartmentName());
	        }
	        S.close();
	        
	        

	    } catch (HibernateException ex) {
	        if (S.getTransaction() != null) {
	            S.getTransaction().rollback();
	        }            
	        Logger.getLogger("con").info("Exception: " + ex.getMessage());
	        ex.printStackTrace(System.err);
	    } finally {
	        S.close(); 
	    }
	}
	
	public String[] getDepartments() {
		String[] vocabulary = new String[departments.size()];
		int i = 0;
		for(String S : departments) {
			vocabulary[i++] = S;
		}
		return vocabulary;
	}
}