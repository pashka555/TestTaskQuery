package org.testTask.consoleQuery;

//import java.*;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "Lectors")
public class LectorInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "lectorId")
	private int lectorId; // pkey(?) Id 
	
	@Column(name = "lectorName")
	private String lecturerName; // Lecturer's name
	
	@OneToMany(
		mappedBy = "lector",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	Set<DepartmentWork> departments = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "degreeId")
	private Degree degree;

	public int getLectorId() {
		return lectorId;
	}

	public void setLectorId(int lectorId) {
		this.lectorId = lectorId;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public Set<DepartmentWork> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<DepartmentWork> departments) {
		this.departments = departments;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}
	
	
	
}
