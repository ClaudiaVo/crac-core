package crac.components.notifier.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import crac.components.notifier.Notification;
import crac.components.notifier.NotificationHelper;
import crac.components.notifier.NotificationType;
import crac.components.utility.DataAccess;
import crac.enums.TaskParticipationType;
import crac.models.db.daos.CracUserDAO;
import crac.models.db.daos.TaskDAO;
import crac.models.db.daos.UserTaskRelDAO;
import crac.models.db.entities.CracUser;
import crac.models.db.entities.Task;
import crac.models.db.relation.UserTaskRel;

public class LeadNomination extends Notification {

	private long senderId;
	private long taskId;

	public LeadNomination(Long senderId, Long targetId, Long taskId) {
		super("Lead Nomination", NotificationType.REQUEST, targetId);
		this.senderId = senderId;
		this.taskId = taskId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
/*
	@Override
	public String toJSon() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString((LeadNomination) this);
		} catch (JsonProcessingException e) {
			return e.toString();
		}
	}*/

	@Override
	public String accept() {
		TaskDAO taskDAO = DataAccess.getRepo(TaskDAO.class);
		UserTaskRelDAO userTaskRelDAO = DataAccess.getRepo(UserTaskRelDAO.class);
		CracUserDAO userDAO = DataAccess.getRepo(CracUserDAO.class);

		Task task = taskDAO.findOne(taskId);
		CracUser user = userDAO.findOne(super.getTargetId());

		UserTaskRel utr = userTaskRelDAO.findByUserAndTaskAndParticipationTypeNot(user, task,
				TaskParticipationType.LEADING);
		
		String message = "";

		if (utr != null) {
			if (utr.getParticipationType() != TaskParticipationType.LEADING) {
				utr.setParticipationType(TaskParticipationType.LEADING);
				userTaskRelDAO.save(utr);
				message = "Found and Changed";
			}
			else{
				message = "Already Leading";
			}
		} else {
			UserTaskRel newRel = new UserTaskRel();
			newRel.setParticipationType(TaskParticipationType.LEADING);
			newRel.setTask(task);
			newRel.setUser(user);
			userTaskRelDAO.save(newRel);
			message = "New Relationship Created";
		}

		NotificationHelper.deleteNotification(this.getNotificationId());
		System.out.println("Leader-Nomination accepted, "+message);
		return message;

	}

	@Override
	public String deny() {
		NotificationHelper.deleteNotification(this.getNotificationId());
		System.out.println("Leader-Nomination denied");
		return "denied";

	}

}
