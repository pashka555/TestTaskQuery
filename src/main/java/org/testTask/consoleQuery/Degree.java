package org.testTask.consoleQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Entity
@Table(name = "Degrees")
public class Degree {
	@Id
	@GeneratedValue
	@Column(name = "degreeId")
	private int degreeId;
	
	@OneToMany(mappedBy = "degree")
	Set<LectorInfo> lecturers = new HashSet<>();
	
	
	@Column(name = "degreeName")
	private String degreeName;


	public int getDegreeId() {
		return degreeId;
	}


	public void setDegreeId(int degreeId) {
		this.degreeId = degreeId;
	}


	public Set<LectorInfo> getLecturers() {
		return lecturers;
	}


	public void setLecturers(Set<LectorInfo> lecturers) {
		this.lecturers = lecturers;
	}


	public String getDegreeName() {
		return degreeName;
	}


	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	
	public static List<Degree> getAllDegrees() {
		SessionFactory SF = HibernateUtil.getSessionFactory();
		
		Session S = SF.openSession();
	        
        //create a criteria query
        CriteriaBuilder CB = S.getCriteriaBuilder();
        CriteriaQuery<Degree> CQ = CB.createQuery(Degree.class);
	
        Root<Degree> root = CQ.from(Degree.class);
        CQ.select(root);
        
        List<Degree> list = S.createQuery(CQ).getResultList();
        return(list);
	}
	
}
