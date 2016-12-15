package crac.notifier.notifications;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.springframework.data.repository.CrudRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import crac.daos.CracUserDAO;
import crac.daos.TaskDAO;
import crac.daos.UserRelationshipDAO;
import crac.models.CracUser;
import crac.models.relation.UserRelationship;
import crac.notifier.Notification;
import crac.notifier.NotificationHelper;
import crac.notifier.NotificationType;

public class FriendRequest extends Notification{
	
	private long senderId;
	
	public FriendRequest(Long senderId, Long targetId){
		super("Friend Request", NotificationType.REQUEST, targetId);
		this.senderId = senderId;
	}
	
	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	@Override
	public String toJSon() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString((FriendRequest)this);
		} catch (JsonProcessingException e) {
			return e.toString();
		}
	}

	@Override
	public String accept(HashMap<String, CrudRepository> map) {
		
		UserRelationshipDAO userRelationshipDAO = (UserRelationshipDAO) map.get("userRelationshipDAO");
		CracUserDAO userDAO = (CracUserDAO) map.get("userDAO");
		
		UserRelationship ur = new UserRelationship();
		
		ur.setC1(userDAO.findOne(senderId));
		ur.setC2(userDAO.findOne(getTargetId()));
		ur.setFriends(true);
		ur.setLikeValue(1.2);
		
		userRelationshipDAO.save(ur);
		
		NotificationHelper.deleteNotification(this.getNotificationId());
		System.out.println("Friend-request accepted");
		return "accepted";
	}

	@Override
	public String deny() {
		NotificationHelper.deleteNotification(this.getNotificationId());
		System.out.println("Friend-request denied");
		return "denied";
		
	}
	
}
