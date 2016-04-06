package crac.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class CracUser {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Autowired
	@Column(name="user_id")
    private long id;
	
	@NotNull
	@Autowired
	private String name;
	
	@NotNull
	@Autowired
	private String password;
	
	@Autowired
	@OneToMany(mappedBy="creator", cascade=CascadeType.ALL)  
    private Set<Task> createdTasks; 
	
	@Autowired
	@OneToMany(mappedBy="creator", cascade=CascadeType.ALL)  
    private Set<Competence> createdCompetences; 
	
	@Autowired
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "user_competencies", joinColumns = { @JoinColumn(name = "user_id") },
    inverseJoinColumns = { @JoinColumn(name = "competence_id") })
    private Set<Competence> competencies;
	
	@Autowired
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "user_tasks", joinColumns = { @JoinColumn(name = "user_id") },
    inverseJoinColumns = { @JoinColumn(name = "task_id") })
    private Set<Task> openTasks;

	public CracUser(String name, String password) {
		this.name = name;
		this.password = password;
		this.competencies = null;
		this.openTasks = null;
	}

	public CracUser() {
		this.name = "";
		this.password = "";
		this.competencies = null;
		this.openTasks = null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
		this.password = bcryptEncoder.encode(password);
	}

	public Set<Competence> getCompetencies() {
		return competencies;
	}

	public void setCompetencies(Set<Competence> competencies) {
		this.competencies = competencies;
	}

	public Set<Task> getOpenTasks() {
		return openTasks;
	}

	public void setOpenTasks(Set<Task> openTasks) {
		this.openTasks = openTasks;
	}

	public Set<Task> getCreatedTasks() {
		return createdTasks;
	}

	public void setCreatedTasks(Set<Task> createdTasks) {
		this.createdTasks = createdTasks;
	}

	public Set<Competence> getCreatedCompetences() {
		return createdCompetences;
	}

	public void setCreatedCompetences(Set<Competence> createdCompetences) {
		this.createdCompetences = createdCompetences;
	}
	
	

}
