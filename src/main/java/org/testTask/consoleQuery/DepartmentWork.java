package org.testTask.consoleQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

//Used to map lectors to departments with an extra argument of being a head of a dep or not
@Entity
@Table(name = "LectorDepartment")
public class DepartmentWork {
	@Id
	@GeneratedValue
	private int DepWorkId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lectorId")
	private LectorInfo lector;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departmentId")
	private DepartmentInfo department;
	
	@Column(name = "isHead")
	private boolean head;
	
	@Column(name = "salary")
	private float salary;
	
	public DepartmentWork() {};
	
	public DepartmentWork(LectorInfo _lector,
			 DepartmentInfo _department, boolean _head,
			 float _salary) {
		lector = _lector;
		department = _department;
		head = _head;
		salary = _salary;
	}

	public int getDepWorkId() {
		return DepWorkId;
	}

	public void setDepWorkId(int depWorkId) {
		DepWorkId = depWorkId;
	}

	public LectorInfo getLector() {
		return lector;
	}

	public void setLector(LectorInfo lector) {
		this.lector = lector;
	}

	public DepartmentInfo getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentInfo department) {
		this.department = department;
	}

	public boolean isHead() {
		return head;
	}

	public void setHead(boolean isHead) {
		this.head = isHead;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}
	
	
}
