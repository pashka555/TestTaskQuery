package org.testTask.consoleQuery;

import java.util.HashMap;
//import java.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "Departments")
public class DepartmentInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "departmentId")
	private int departmentId; // pkey(?) Id 
	
	@Column(name = "departmentName")
	private String departmentName; // Department's name
	
	@OneToMany(mappedBy = "department")
	//@JoinColumn(name = "depWorkers")
    private Set<DepartmentWork> departmentWorkers = new HashSet<>();

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Set<DepartmentWork> getEmployees() {
		return departmentWorkers;
	}

	public void setEmployees(Set<DepartmentWork> employees) {
		this.departmentWorkers = employees;
	}
	
	public LectorInfo getHeadOfDepartment() {
		for (DepartmentWork DW : departmentWorkers) {
			if(DW.isHead()) return DW.getLector();
		}
		return null;
	}
	
	public float getAvgSalary() {
		int count = 0;
	    float totalSalary = 0;
		for (DepartmentWork DW : departmentWorkers) {
			totalSalary += DW.getSalary();
			count++;
		}
		return(totalSalary/count);
	}

	
	//Everything in DW's acts up as nulled, leaving it up 
	//to db queries
	public String getStats() {
		//List<Degree> degrees = Degree.getAllDegrees();
		Map<Degree,Integer> degreeCount = new HashMap<>();
		for (DepartmentWork DW : departmentWorkers) {
			LectorInfo tempL = DW.getLector();
			Degree temp = tempL.getDegree();
			if(degreeCount.containsKey(temp)) {
				degreeCount.replace(temp, degreeCount.get(temp) + 1);
			} else degreeCount.put(temp, 1);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Statistics for ")
			.append(getDepartmentName())
			.append("\n");
		degreeCount.forEach((x,y) -> {
			sb.append(x.getDegreeName())
				.append(" : ")
				.append(y.toString())
				.append("\n");
		});
		return (sb.toString());
	}
}
