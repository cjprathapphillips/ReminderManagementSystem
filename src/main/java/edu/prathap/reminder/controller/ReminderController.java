package edu.prathap.reminder.controller;

import edu.prathap.reminder.entity.CustomUser;
import edu.prathap.reminder.entity.Reminder;
import edu.prathap.reminder.entity.ReminderType;
import edu.prathap.reminder.entity.User;
import edu.prathap.reminder.repo.ReminderRepo;
import edu.prathap.reminder.repo.ReminderTypeRepo;
import edu.prathap.reminder.repo.UserRepo;
import edu.prathap.reminder.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("reminder")
public class ReminderController {
	@Autowired
	private ReminderRepo reminderRepo;
	@Autowired
	private ReminderTypeRepo reminderTypeRepo;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserRepo userRepo;

	@RequestMapping("/All")
	public ModelAndView all(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseOrderByRenewDate();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		if(null==userId) return new ModelAndView("index");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		return new ModelAndView("reminder", "reminderList",reminderList);
	}


	@RequestMapping("/All Renewable")
	public ModelAndView reminderRenewable(HttpServletRequest httpServletRequest,String message) {
		List<Reminder> reminderList=reminderRepo.reminderRenewable();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		if(null==userId) return new ModelAndView("index");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		ModelAndView modelAndView = new ModelAndView("reminder");
		if(null!=message)
			modelAndView.addObject("message", message);
		modelAndView.addObject("reminderList", reminderList);
		return modelAndView;
	}

	@RequestMapping("/All Non-Renewable")
	public ModelAndView allNonRenewable(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.allNonRenewable();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		if(null==userId) return new ModelAndView("index");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
			Integer renewMonth=Integer.parseInt(monthFormat.format(renuewDate));
			Integer todayMonth=Integer.parseInt(monthFormat.format(new Date()));
			reminder.setUrgentCountMonth(renewMonth-todayMonth);
		});
		return new ModelAndView("reminderNonRenew", "reminderList",reminderList);
	}

	@RequestMapping("/Vehicle Insurance")
	public ModelAndView vehicleInsurance(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseAndReminderTypeVehicleInsuranceOrderByRenewDate();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		return new ModelAndView("reminder", "reminderList",reminderList);
	}



	@RequestMapping("/House Tax")
	public ModelAndView houseTax(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseAndReminderTypeHouseTaxOrderByRenewDate();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		return new ModelAndView("reminder", "reminderList",reminderList);
	}

	@RequestMapping("/Marriage Anniversary")
	public ModelAndView marriageAnniversary(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseAndReminderTypeMarriageAnniversaryOrderByRenewDate();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		return new ModelAndView("reminderNonRenew", "reminderList",reminderList);
	}

	@RequestMapping("/Vehicle Registration Certificate")
	public ModelAndView vehicleRegistrationCertificate(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseAndReminderTypeVehicleRegistrationCertificateOrderByRenewDate();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		return new ModelAndView("reminder", "reminderList",reminderList);
	}

	@RequestMapping("/Driving Licence")
	public ModelAndView drivingLicence(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseAndReminderTypeDrivingLicenceOrderByRenewDate();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		return new ModelAndView("reminder", "reminderList",reminderList);
	}

	@RequestMapping("/Birthday")
	public ModelAndView birthday(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseAndReminderTypeBirthdayOrderByRenewDate();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(diffInDays);
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
		});
		return new ModelAndView("reminderNonRenew", "reminderList",reminderList);
	}

	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest httpServletRequest) {
		List<ReminderType> reminderTypeList= reminderTypeRepo.findAllByDeletedFalse();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		return new ModelAndView("addReminder","reminderTypeList",reminderTypeList);
	}

	@RequestMapping(value="/saveReminder",method = RequestMethod.POST)
	public ModelAndView saveReminder(@ModelAttribute Reminder reminder,HttpServletRequest httpServletRequest) {
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		String save=httpServletRequest.getParameter("save");
		String cancel=httpServletRequest.getParameter("cancel");
		if(null!=httpServletRequest.getParameter("save")) {
            ReminderType reminderType=null;
            if(null!=reminder.getFrequencyTypeCode()) {
                reminderType = reminderTypeRepo.getReferenceById(reminder.getFrequencyTypeCode());
                reminder.setReminderTypeId(reminderType);
                reminder.setReminderType(reminderType.getName());
            }
            reminder.setCreatedDate(Timestamp.from(Instant.now()));
			try {
				SimpleDateFormat dateformatFromUi = new SimpleDateFormat("yyyy-MM-dd");
				reminder.setRenewDate(new java.sql.Date(dateformatFromUi.parse(reminder.getRenewDateString()).getTime()));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			reminder.setDeleted(false);
			reminderRepo.save(reminder);
		}
//		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseOrderByRenewDate();
//		return new ModelAndView("reminder", "reminderList",reminderList);
		return reminderRenewable(httpServletRequest,save!=null?"Reminder Saved Successfully":"Reminder Save Cancelled");
	}

	@RequestMapping(value="/editView")
	public ModelAndView edit(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest) {
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		Optional<Reminder> reminderOptional=reminderRepo.findById(id);
		if(null==userId) return new ModelAndView("index");
		return new ModelAndView("editReminder", "reminder",reminderOptional.get());
	}

	@RequestMapping(value="/edit",method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute Reminder reminder,HttpServletRequest request) {
		Long userId=(Long)request.getSession().getAttribute("userId");
		if(null==userId) return new ModelAndView("index");
		String save=request.getParameter("save");
		String cancel=request.getParameter("cancel");
		if(null!=request.getParameter("save")) {
			Reminder reminderDb = reminderRepo.getReferenceById(reminder.getId());
			Optional<CustomUser> userOptional= userRepo.findById(userId);
			reminder.setReminderType(reminderDb.getReminderType());
			reminder.setCreatedDate(Timestamp.from(Instant.now()));
			reminder.setDeleted(false);
            try {
				SimpleDateFormat dateformatFromUi = new SimpleDateFormat("yyyy-MM-dd");
				reminder.setRenewDate(new java.sql.Date(dateformatFromUi.parse(reminder.getRenewDateString()).getTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

			reminder.setUser(userOptional.get());
			reminderRepo.save(reminder);
		}
//		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseOrderByRenewDate();
//		return new ModelAndView("reminder", "reminderList",reminderList);
		return reminderRenewable(request,save!=null?"Reminder Edited Successfully":"Reminder Edit Cancelled");
	}

	@RequestMapping(value="/delete")
	public ModelAndView delete(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest) {
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		Optional<Reminder> reminderOptional=reminderRepo.findById(id);
		reminderRepo.delete(reminderOptional.get());
		return reminderRenewable(httpServletRequest,"Reminder deleted Sucessfully");
	}

	@RequestMapping(value="/renew")
	public ModelAndView renew(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest) {
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		Optional<Reminder> reminderOptional=reminderRepo.findById(id);
		Reminder reminder=reminderOptional.get();
		if(reminder.getFrequency().equals("Year")){
			Date renewDate=reminder.getRenewDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(renewDate);
			calendar.add(Calendar.YEAR, reminder.getUnit());
			LocalDate localDate = calendar.getTime().toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate();
			reminder.setRenewDate(java.sql.Date.valueOf(localDate));
		}
		reminderRepo.save(reminder);
		return reminderRenewable(httpServletRequest,"Reminder Renewed Successuflly");
	}

	@RequestMapping(value="/sendReminderMail")
	public ModelAndView sendReminderMail(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest) {
		Reminder reminder = reminderRepo.getReferenceById(id);
		emailService.sendReminderMail(id);
		return reminderRenewable(httpServletRequest,"Email sent Successfully to "+reminder.getUser().getUsername());
	}
}
