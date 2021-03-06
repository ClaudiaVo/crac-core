package crac.models.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "evaluations")
public class Evaluation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evaluation_id")
	private long id;
	
	@ManyToOne
	@JsonIdentityReference(alwaysAsId=true)
	@JoinColumn(name = "task_id")
	private Task task;
	
	@Column(name="like_val_others")
	private double likeValOthers;
	
	@Column(name="like_val_task")
	private double likeValTask;
	
	@Column(name="like_val_organisation")
	private double likeValOrganisation;
	
	private String feedback;
	
	@NotNull
	@Column(name="notification_id")
	private String notificationId;
	
	@NotNull
	private boolean filled;
	
	/**
	 * Defines a many to one relationship with the CracUser-entity
	 */
	
	@ManyToOne
	@JsonIdentityReference(alwaysAsId=true)
	@JoinColumn(name = "user_id")
	private CracUser user;
	
	/**
	 * constructors
	 */
	
	public Evaluation(CracUser user, Task task) {
		this.user = user;
		this.filled = false;
		this.task = task;
	}

	public Evaluation() {
	}

	/**
	 * getters and setters
	 */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CracUser getUser() {
		return user;
	}

	public void setUser(CracUser user) {
		this.user = user;
	}

	public double getLikeValOthers() {
		return likeValOthers;
	}

	public void setLikeValOthers(double likeValOthers) {
		this.likeValOthers = likeValOthers;
	}

	public double getLikeValTask() {
		return likeValTask;
	}

	public void setLikeValTask(double likeValTask) {
		this.likeValTask = likeValTask;
	}

	public double getLikeValOrganisation() {
		return likeValOrganisation;
	}

	public void setLikeValOrganisation(double likeValOrganisation) {
		this.likeValOrganisation = likeValOrganisation;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
		
}
