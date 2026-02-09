package edu.prathap.reminder.controller;

import edu.prathap.reminder.entity.Reminder;
import edu.prathap.reminder.repo.ReminderRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {
	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@RequestMapping("/mainMenu")
	public ModelAndView mainMenu() {
		return new ModelAndView("mainMenu");
	}

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest httpServletRequest) {
		httpServletRequest.getSession().invalidate();
		return new ModelAndView("index");
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest httpServletRequest) {
		httpServletRequest.getSession().invalidate();
		return new ModelAndView("index");
	}

}
