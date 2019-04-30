package org.testTask.consoleQuery;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.spi.SessionFactoryBuilderFactory;

public class MainProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DepartmentInfo dep1 = new DepartmentInfo();
		dep1.setDepartmentName("Department of Sociology");
		DepartmentInfo dep2 = new DepartmentInfo();
		dep2.setDepartmentName("Department of Applied Maths");
		
		
		LectorInfo lec1 = new LectorInfo();
		lec1.setLecturerName("Ivan Nazviy");
		LectorInfo lec2 = new LectorInfo();
		lec2.setLecturerName("Vasiliy Vaniy");
		LectorInfo lec3 = new LectorInfo();
		lec3.setLecturerName("Natalya Yanovska");
		LectorInfo lec4 = new LectorInfo();
		lec4.setLecturerName("Lidiya Mikhailiva");
		//lec1.setDegree(degree);
		
		Degree deg1 = new Degree();
		deg1.setDegreeName("Professor");
		Degree deg2 = new Degree();
		deg2.setDegreeName("Associate Professor");
		Degree deg3 = new Degree();
		deg3.setDegreeName("Assistant");
		//Degree deg4 = new Degree();
		//deg4.setDegreeName("Professor");
		
		lec1.setDegree(deg1);
		lec2.setDegree(deg3);
		lec3.setDegree(deg2);
		lec4.setDegree(deg2);
		
		DepartmentWork dw1 = new DepartmentWork(lec1,dep1,true,1200);
		DepartmentWork dw2 = new DepartmentWork(lec2,dep1,false, 600);
		DepartmentWork dw3 = new DepartmentWork(lec2,dep2,true, 800);
		DepartmentWork dw4 = new DepartmentWork(lec3,dep2,false, 1200);
		DepartmentWork dw5 = new DepartmentWork(lec4,dep2,false,800);
		//lec1.getDepartments().add(dw1);
		//lec2.getDepartments().add(dw2);
		//lec1.getDepartments().add(dep1);
		
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
		
		
		S.beginTransaction();
		S.save(deg1);
		S.save(deg2);
		S.save(deg3);
		S.save(dep1);
		S.save(dep2);
		S.save(lec1);
		S.save(lec2);
		S.save(lec3);
		S.save(lec4);
		S.save(dw1);
		S.save(dw2);
		S.save(dw3);
		S.save(dw4);
		S.save(dw5);
		S.getTransaction().commit();
		
		//new RequestProcessor().showHeadOfDep("Department of SOciology");
		
		//sample executes here
		System.out.println("Executing 'Search by li'");
		
		new RequestProcessor("Search by li")
			.executeIntent();
		
		System.out.println();
		System.out.println("Executing 'Show statistics for Department of Sociology'");
		new RequestProcessor("Show statistics for Department of Sociology")
			.executeIntent();
		
		
		System.out.println();
		System.out.println("Directly executing showStats() for Department of Applied Maths");
		new RequestProcessor().showStats("Department of Applied Maths");
		
		System.out.println();
		System.out.println("Executing 'What is the salary of Department of Applied Maths'");
		new RequestProcessor("What is the salary of Department of Applied"
				+" Maths").executeIntent();	
			
		S.close();
		//Set up values
		
		//SessionFactory SF = HibernateUtil.getSessionFactory();
		
		//Session S = SF.openSession();
		//Actions go here
		/*Transaction tx = null;
	    try {

	        tx = S.beginTransaction();        

	        // deprecated
	        //List<LectorInfo> list = S.createCriteria(LectorInfo.class).list();
	        
	        CriteriaBuilder CB = S.getCriteriaBuilder();
	        CriteriaQuery<LectorInfo> CQ = CB.createQuery(LectorInfo.class);
	        
	        CQ.from(LectorInfo.class);
	        
	        List<LectorInfo> list = S.createQuery(CQ).getResultList();
	        DepartmentWork DI = list.get(0).getDepartments()
    		.iterator().next();
	        
	        System.out.println(list.get(0).getDepartments()
	        		.iterator()
	        		.next()
	        		.getDepartment()
	        		.getDepartmentName());
	        tx.commit();
	        S.close();
	        
	        

	    } catch (HibernateException ex) {
	        if (tx != null) {
	            tx.rollback();
	        }            
	        Logger.getLogger("con").info("Exception: " + ex.getMessage());
	        ex.printStackTrace(System.err);
	    } finally {
	        S.close(); 
	    }
		//S.getTransaction().commit();
		//S.close(); */
	}

}
