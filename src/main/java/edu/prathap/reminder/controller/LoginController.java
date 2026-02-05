package edu.prathap.reminder.controller;

import edu.prathap.reminder.entity.CustomUser;
import edu.prathap.reminder.entity.User;
import edu.prathap.reminder.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView showLoginPage(CustomUser user, HttpServletRequest httpServletRequest){
        CustomUser userDb=userRepo.findAllByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(null!=userDb && userDb.getUsername().equals(user.getUsername()) && userDb.getPassword().equals(user.getPassword())){
            httpServletRequest.getSession().setAttribute("userId",userDb.getUserId());
            return new ModelAndView("mainMenu");
        }else{
            return new ModelAndView("index","errorMessage","Invalid User Name and Password");
        }
    }

}
