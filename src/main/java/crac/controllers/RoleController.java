package crac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import crac.daos.RoleDAO;
import crac.models.CracUser;
import crac.models.Role;
import crac.utility.JSonResponseHelper;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleDAO roleDAO;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> index() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		Iterable<Role> roles = roleDAO.findAll();

		try {
			return ResponseEntity.ok().body(mapper.writeValueAsString(roles));
		} catch (JsonProcessingException e) {
			System.out.println(e.toString());
			return JSonResponseHelper.jsonWriteError();
		}
	}

	
}