package pfq.storage.server.controllers.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pfq.storage.server.model.CurrentUser;
import pfq.storage.server.service.impl.SystemInfoServiceImpl;

@Controller
public class HomeController {
	
	@Autowired
	SystemInfoServiceImpl systemInfoService;
	
	@RequestMapping("/")
	public String getHomePage(HttpServletRequest request,Model model) {
		CurrentUser cu = systemInfoService.getCurrentUser(request);
		model.addAttribute("username", cu.getName());
		model.addAttribute("useremail", cu.getEmail());

		return "home";
	}
	
	@RequestMapping("/admin")
	public String getAdminPage(HttpServletRequest request,Model model) {
		CurrentUser cu = systemInfoService.getCurrentUser(request);
		model.addAttribute("username", cu.getName());
		model.addAttribute("useremail", cu.getEmail());
		return "admin";
	}
}
//https://github.com/StarterSquad/startersquad.com/tree/master/examples/angularjs-requirejs-2
