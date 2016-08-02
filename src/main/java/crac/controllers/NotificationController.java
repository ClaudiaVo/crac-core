package crac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import crac.daos.CracUserDAO;
import crac.models.CracUser;
import crac.notifier.Notification;
import crac.notifier.NotificationHelper;
import crac.utility.JSonResponseHelper;

@RestController
@RequestMapping("/notifications")

public class NotificationController {
	
	@Autowired
	private CracUserDAO userDAO;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getNotifications() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CracUser user = userDAO.findByName(userDetails.getUsername());
		return ResponseEntity.ok().body(NotificationHelper.notificationsToString(NotificationHelper.getUserNotifications(user)));
	}
	
	@RequestMapping(value = { "/admin/", "/admin" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getAllNotifications() {
	return ResponseEntity.ok().body(NotificationHelper.notificationsToString(NotificationHelper.getAllNotifications()));
	}
	
	@RequestMapping(value = { "/friend/{user_id}/add", "/friend/{user_id}/add/" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> addFriend(@PathVariable(value = "user_id") Long id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CracUser sender = userDAO.findByName(userDetails.getUsername());
		CracUser receiver = userDAO.findOne(id);
		NotificationHelper.createFriendRequest(sender, receiver);
		return JSonResponseHelper.successfullFriendRequest(receiver);
	}

	@RequestMapping(value = { "/{notification_id}/accept", "/friend/{notification_id}/accept/" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> acceptFriend(@PathVariable(value = "notification_id") String notificationId) {
		Notification n = NotificationHelper.getNotificationByNotificationId(notificationId);
		
		if(n != null){
			n.accept();
			return ResponseEntity.ok().body(NotificationHelper.notificationsToString(n));
		}else{
			return JSonResponseHelper.noSuchNotification();
		}
		

	}
	
	@RequestMapping(value = { "/{notification_id}/deny", "/{notification_id}/deny/" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> denyFriend(@PathVariable(value = "notification_id") String notificationId) {
		Notification n = NotificationHelper.getNotificationByNotificationId(notificationId);
		
		if(n != null){
			n.deny();
			return ResponseEntity.ok().body(NotificationHelper.notificationsToString(n));
		}else{
			return JSonResponseHelper.noSuchNotification();
		}
	}
	
}
