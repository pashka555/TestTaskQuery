package org.testTask.consoleQuery;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.testTask.consoleQuery.RequestElements;

public class RequestProcessor {
	String request;
	Map<RequestElements,Integer> result = new HashMap<RequestElements,Integer>();
	String intent;
	
	public RequestProcessor(String req) {
		request = req;
	}
	
	public RequestProcessor() {}
	
	public void processTextForIntent() {
		for (RequestElements RE: RequestElements.values()) {
			int i = 0;
			for(String s: RE.vocabulary) {
				if(request.contains(s)) {
					result.put(RE,i);
				}
				i++;
			}
		}
		
		//for (RequestElements RE: result) {
		//	System.out.println(RE.name());
		//}
	}
	
	public void executeIntent() {
		if(result.isEmpty()) {
			processTextForIntent();
		}
		
		//WHAT ARE HEADS?
		if(result.containsKey(RequestElements.SHOW_COMMAND) &&
				result.containsKey(RequestElements.HEAD))
			showHeads();
		
		//SHOW AVERAGE SALARY
		if((result.containsKey(RequestElements.QUERY) || 
				result.containsKey(RequestElements.SHOW_COMMAND)) && 
				result.containsKey(RequestElements.SALARY) && 
				result.containsKey(RequestElements.DEPARTMENT)) {
			showAvgSalaryOfDep(RequestElements.DEPARTMENT
			.vocabulary[result.get(RequestElements.DEPARTMENT)]);
		}
		
		
		//SEARCH
		if(result.containsKey(RequestElements.SEARCH_COMMAND) && 
				result.containsKey(RequestElements.BY)) {
			String keyword = request.substring(request.indexOf("by ") + 3);
			//System.out.println(keyword);
			globalSearch(keyword);
		}
		
		//SHOW STATS
		if((result.containsKey(RequestElements.QUERY) || 
				result.containsKey(RequestElements.SHOW_COMMAND)) && 
				result.containsKey(RequestElements.STATISTIC) && 
				result.containsKey(RequestElements.DEPARTMENT)) {
			showStats(RequestElements.DEPARTMENT
			.vocabulary[result.get(RequestElements.DEPARTMENT)]);
		}
	}
	
	//public String executeIntent() {
	//	
	//}
	
	//DRYed department methods
	public List<DepartmentInfo> getAllDepartments() {
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
	        
        //create a criteria query
        CriteriaBuilder CB = S.getCriteriaBuilder();
        CriteriaQuery<DepartmentInfo> CQ = CB.createQuery(DepartmentInfo.class);
	
        Root<DepartmentInfo> root = CQ.from(DepartmentInfo.class);
        CQ.select(root);
        
        List<DepartmentInfo> list = S.createQuery(CQ).getResultList();
        
        S.close();
        
        return(list);
	}
	
	public List<DepartmentInfo> getDepartment(String DepartmentName) {
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
	        
        //create a criteria query
        CriteriaBuilder CB = S.getCriteriaBuilder();
        CriteriaQuery<DepartmentInfo> CQ = CB.createQuery(DepartmentInfo.class);
	        //create a "WHERE"
        Root<DepartmentInfo> root = CQ.from(DepartmentInfo.class);
        CQ.select(root).where(CB.like(root.get("departmentName"), DepartmentName));
        
        List<DepartmentInfo> list = S.createQuery(CQ).getResultList();

        S.close();
        
        return(list);
	}
	
	//SHOW HEADS OF DEPARTMENTS
	
	public void showHeads() {
		
		List<DepartmentInfo> list = getAllDepartments();
		
		for(DepartmentInfo DI: list) {
	        	System.out.print(DI.getHeadOfDepartment());
	        	System.out.print(" --- ");
	        	System.out.println(DI.getDepartmentName());
	    }
	    //S.close();
	}
	
	//Show head of a select department
	public void showHeadOfDep(String DepartmentName) {

		List<DepartmentInfo> list = getDepartment(DepartmentName);
		
        for(DepartmentInfo DI: list) {
        	System.out.println(DI.getHeadOfDepartment().getLecturerName());
        }
	}
	
	
	//Show salary of a department
	//TODO fix the error that occurs when moving Session stuff out of there
	public void showAvgSalaryOfDep(String DepartmentName) {
		
		//List<DepartmentInfo> list = getDepartment(DepartmentName);
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
	        
        //create a criteria query
        CriteriaBuilder CB = S.getCriteriaBuilder();
        CriteriaQuery<DepartmentInfo> CQ = CB.createQuery(DepartmentInfo.class);
	        //create a "WHERE"
        Root<DepartmentInfo> root = CQ.from(DepartmentInfo.class);
        CQ.select(root).where(CB.like(root.get("departmentName"), DepartmentName));
        
        List<DepartmentInfo> list = S.createQuery(CQ).getResultList();

        
        for(DepartmentInfo DI: list) {
        	System.out.println(DI.getAvgSalary());
        }
        
        S.close();
	}
	
	public void showStats(String DepartmentName) {
		
		/*DepartmentInfo dep = getDepartment(DepartmentName).get(0);
		
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
		
		CriteriaBuilder CB = S.getCriteriaBuilder();
		CriteriaQuery<DepartmentWork> CQ = CB.createQuery(DepartmentWork.class);
		
		Root<DepartmentWork> RDI = CQ.from(DepartmentWork.class);
		CQ.select(RDI).where(CB.equal(RDI.get("department"), dep.getDepartmentId() ));
		
		List<DepartmentWork> DWList = S.createQuery(CQ).getResultList();
		
		Map<Degree,Integer> degreeCount = new HashMap<>();
		for (DepartmentWork DW : DWList) {
			LectorInfo tempL = DW.getLector();
			Degree temp = tempL.getDegree();
			if(degreeCount.containsKey(temp)) {
				degreeCount.replace(temp, degreeCount.get(temp) + 1);
			} else degreeCount.put(temp, 1);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Statistics for ")
			.append(dep.getDepartmentName())
			.append("\n");
		degreeCount.forEach((x,y) -> {
			sb.append(x.getDegreeName())
				.append(" : ")
				.append(y.toString())
				.append("\n");
		});
		
		
		
        S.close();
        
        System.out.println(sb.toString()); */
		
		//List<DepartmentInfo> list = getDepartment(DepartmentName);
				SessionFactory SF = HibernateUtil.getSessionFactory();
				
				Session S = SF.openSession();
			        
		        //create a criteria query
		        CriteriaBuilder CB = S.getCriteriaBuilder();
		        CriteriaQuery<DepartmentInfo> CQ = CB.createQuery(DepartmentInfo.class);
			        //create a "WHERE"
		        Root<DepartmentInfo> root = CQ.from(DepartmentInfo.class);
		        CQ.select(root).where(CB.like(root.get("departmentName"), DepartmentName));
		        
		        List<DepartmentInfo> list = S.createQuery(CQ).getResultList();

		        
		        for(DepartmentInfo DI: list) {
		        	System.out.println(DI.getStats());
		        }
		        
		        S.close();
	}
	
	//Search Methods
	public void searchDeps(String keyword) {
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
	        
        //create a criteria query
        CriteriaBuilder CB = S.getCriteriaBuilder();
        CriteriaQuery<DepartmentInfo> CQ = CB.createQuery(DepartmentInfo.class);
	        //create a "WHERE"
        Root<DepartmentInfo> root = CQ.from(DepartmentInfo.class);
        CQ.select(root).where(CB.like(root.get("departmentName"), "%"+keyword+"%"));
        
        List<DepartmentInfo> list = S.createQuery(CQ).getResultList();

        for(DepartmentInfo DI: list) {
        	System.out.println(DI.getDepartmentName());
        }	        
	}
	
	public void searchLecs(String keyword) { 
		
		SessionFactory SF = HibernateUtil.getSessionFactory();
			
		Session S = SF.openSession();
		        
        //create a criteria query
        CriteriaBuilder CB = S.getCriteriaBuilder();
        CriteriaQuery<LectorInfo> CQ = CB.createQuery(LectorInfo.class);
        //create a "WHERE"
        Root<LectorInfo> root = CQ.from(LectorInfo.class);
        CQ.select(root).where(CB.like(root.get("lecturerName"), "%"+keyword+"%"));
        
        List<LectorInfo> list = S.createQuery(CQ).getResultList();
        for(LectorInfo LI: list) {
        	System.out.println(LI.getLecturerName());
        }		     
	}
	
	
	
	
	public void globalSearch(String keyword) {
		searchDeps(keyword);
		searchLecs(keyword);
	}
}
