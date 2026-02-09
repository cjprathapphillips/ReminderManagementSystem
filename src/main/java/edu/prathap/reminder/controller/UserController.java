package edu.prathap.reminder.controller;

import edu.prathap.reminder.entity.RmsUser;
import edu.prathap.reminder.repo.UserRepo;
import edu.prathap.reminder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest httpServletRequest, Authentication authentication) {
        if(null==authentication) return new ModelAndView("index");
        RmsUser customUser=(RmsUser)authentication.getPrincipal();
        if(null==customUser.getUserId()) return new ModelAndView("index");

        List<RmsUser> userList=userRepo.findAll();

        return new ModelAndView("user", "userList",userList);
    }

    @RequestMapping(value="/editView")
    public ModelAndView edit(@RequestParam(name = "userId",required = false) Long userId, HttpServletRequest httpServletRequest, Authentication authentication) {
        if(null==authentication) return new ModelAndView("index");
        RmsUser customUser=(RmsUser)authentication.getPrincipal();
        if(null==customUser.getUserId()) return new ModelAndView("index");
        Optional<RmsUser> userOptional=null;
        if(null!=userId)  userOptional=userRepo.findById(userId);
        if(null!=userOptional && userOptional.isPresent()){
            return new ModelAndView("addUser", "user",userOptional.get());
        }else{
            return new ModelAndView("addUser");
        }
    }

    @RequestMapping(value="/save")
    public ModelAndView save(@ModelAttribute RmsUser user, HttpServletRequest httpServletRequest, Authentication authentication) {
        if(null==authentication) return new ModelAndView("index");
        RmsUser customUser=(RmsUser)authentication.getPrincipal();
        if(null==customUser.getUserId()) return new ModelAndView("index");
        user.setAuthorities(user.getAuthorities().toString().replace("[", "").replace("]", ""));
        RmsUser dbUser=userRepo.findByUsername(user.getUsername());
        if(null!=dbUser){
            dbUser.setPassword(user.getPassword());
            user=userRepo.save(dbUser);
        }else{
            userService.create(user.getUsername(),user.getPassword(),user.getAuthorities().toString());
        }

        List<RmsUser> userList=userRepo.findAll();
        return new ModelAndView("user", "userList",userList);
    }

    @RequestMapping(value="/delete")
    public ModelAndView delete(@RequestParam(name = "userId") Long userId, HttpServletRequest httpServletRequest, Authentication authentication) {
        if(null==authentication) return new ModelAndView("index");
        RmsUser customUser=(RmsUser)authentication.getPrincipal();
        if(null==customUser.getUserId()) return new ModelAndView("index");
        Optional<RmsUser> userOptional=null;
        if(null!=userId)  userOptional=userRepo.findById(userId);
        userRepo.delete(userOptional.get());
        List<RmsUser> userList=userRepo.findAll();
        return new ModelAndView("user", "userList",userList);
    }
}
