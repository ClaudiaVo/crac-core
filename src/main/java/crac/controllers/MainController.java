package crac.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

  @RequestMapping("/")
  @ResponseBody
  public String index() {
	  return "Hello World! Spring Boot + Hibernate! <br/> Click on <a href=\"hello-world/getAll\">me</a> to get all safed HelloWorld-data sets";
  }

}