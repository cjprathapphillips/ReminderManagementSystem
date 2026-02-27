package edu.prathap.reminder.controller;

import edu.prathap.reminder.entity.RmsUser;
import edu.prathap.reminder.entity.Reminder;
import edu.prathap.reminder.entity.ReminderType;
import edu.prathap.reminder.repo.ReminderRepo;
import edu.prathap.reminder.repo.ReminderTypeRepo;
import edu.prathap.reminder.repo.UserRepo;
import edu.prathap.reminder.service.EmailService;
import edu.prathap.reminder.service.KafkaProducerService;
import edu.prathap.reminder.service.ReminderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.servlet.Session;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.logging.Logger;

@Slf4j
@Controller
@RequestMapping("reminder")
@Transactional
public class ReminderController {

	@Autowired
	private ReminderRepo reminderRepo;
	@Autowired
	private ReminderTypeRepo reminderTypeRepo;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private KafkaProducerService producerService;
    @Autowired
    ReminderService reminderService;


	@RequestMapping("/All")
	public ModelAndView all(HttpServletRequest httpServletRequest,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseOrderByRenewDate();
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
	public ModelAndView reminderRenewable(HttpServletRequest httpServletRequest,String message,Authentication authentication) {
		final String topic ="TESTKAFKA";
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		SimpleDateFormat gridFormat = new SimpleDateFormat("dd-MMM-yyyy");
        List<Reminder> reminderList = reminderService.reminderRenewable();
//		Boolean messageSent = producerService.sendMessage(topic, "Starting New List ================="+System.currentTimeMillis());
//        log.trace("messageSent:::"+messageSent);
		reminderList.forEach(reminder->{
			Date renuewDate=reminder.getRenewDate();
			Date today = new Date();
			long dateDiff=renuewDate.getTime()-today.getTime();
			long diffInDays = TimeUnit.MILLISECONDS.toDays(dateDiff);
            reminder.setUrgentCountDays(TimeUnit.MILLISECONDS.toDays(dateDiff));
			reminder.setRenewDateString(gridFormat.format(reminder.getRenewDate()));
			if(diffInDays<=30) {
				reminder.setUrgent(true);
			}else{
				reminder.setUrgent(false);
			}
//			if(messageSent)
//			    producerService.sendMessage(topic, reminder.getName() + "---" + reminder.getReminderType() + "---" + reminder.getRenewDate());

		});
		ModelAndView modelAndView = new ModelAndView("reminder");
		if(null!=message)
			modelAndView.addObject("message", message);
		modelAndView.addObject("reminderList", reminderList);
		return modelAndView;
	}


	@RequestMapping("/All Non-Renewable")
	public ModelAndView allNonRenewable(HttpServletRequest httpServletRequest, Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat gridFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		List<Reminder> reminderList=reminderService.allNonRenewable();
		reminderList.forEach(reminder->{
			long renewDate=Long.parseLong(dateFormat.format(reminder.getRenewDate()));
			long todayDate=Long.parseLong(dateFormat.format(new Date()));
			long renewMonth=Long.parseLong(monthFormat.format(reminder.getRenewDate()));
			long todayMonth=Long.parseLong(monthFormat.format(new Date()));
			reminder.setUrgentCountMonth(renewMonth-todayMonth);
			reminder.setUrgentCountDays(renewDate-todayDate);
			reminder.setRenewDateString(gridFormat.format(reminder.getRenewDate()));
		});
		return new ModelAndView("reminderNonRenew", "reminderList",reminderList);
	}

	@RequestMapping("/Vehicle Insurance")
	public ModelAndView vehicleInsurance(HttpServletRequest httpServletRequest) {
		List<Reminder> reminderList=reminderRepo.findAllByDeletedFalseAndReminderTypeVehicleInsuranceOrderByRenewDate();
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
	public ModelAndView add(HttpServletRequest httpServletRequest,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		List<ReminderType> reminderTypeList= reminderTypeRepo.findAllByDeletedFalse();
		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		return new ModelAndView("addReminder","reminderTypeList",reminderTypeList);
	}

	@RequestMapping(value="/saveReminder",method = RequestMethod.POST)
	public ModelAndView saveReminder(@ModelAttribute Reminder reminder,HttpServletRequest httpServletRequest,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

        reminder.setUser(customUser);
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
		return reminderRenewable(httpServletRequest,save!=null?"Reminder Saved Successfully":"Reminder Save Cancelled",authentication);
	}

	@Transactional
	@RequestMapping(value="/editView")
	public ModelAndView edit(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		Optional<Reminder> reminderOptional=reminderRepo.findById(id);
		return new ModelAndView("editReminder", "reminder",reminderOptional.get());
	}

	@RequestMapping(value="/edit",method = RequestMethod.POST)
	public ModelAndView edit(@ModelAttribute Reminder reminder,HttpServletRequest request,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		String save=request.getParameter("save");
		String cancel=request.getParameter("cancel");
		if(null!=request.getParameter("save")) {
			Reminder reminderDb = reminderRepo.getReferenceById(reminder.getId());
			Optional<RmsUser> userOptional= userRepo.findById(customUser.getUserId());
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
		return reminderRenewable(request,save!=null?"Reminder Edited Successfully":"Reminder Edit Cancelled",authentication);
	}

	@RequestMapping(value="/delete")
	public ModelAndView delete(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		Long userId=(Long)httpServletRequest.getSession().getAttribute("userId");
		Optional<Reminder> reminderOptional=reminderRepo.findById(id);
		reminderRepo.delete(reminderOptional.get());
		return reminderRenewable(httpServletRequest,"Reminder deleted Sucessfully",authentication);
	}

	@RequestMapping(value="/renew")
	public ModelAndView renew(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		SimpleDateFormat gridFormat = new SimpleDateFormat("dd-MMM-yyyy");
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
			reminder.setRenewDateString(gridFormat.format(reminder.getRenewDate()));
			reminder.setRenewDate(java.sql.Date.valueOf(localDate));
		}
		reminderRepo.save(reminder);
		return reminderRenewable(httpServletRequest,"Reminder Renewed Successuflly",authentication);
	}

	@RequestMapping(value="/sendReminderMail")
	public ModelAndView sendReminderMail(@RequestParam(name = "id") Long id,HttpServletRequest httpServletRequest,Authentication authentication) {
		if(null==authentication) return new ModelAndView("index");
		RmsUser customUser=(RmsUser)authentication.getPrincipal();
		if(null==customUser.getUserId()) return new ModelAndView("index");

		Reminder reminder = reminderRepo.getReferenceById(id);
		emailService.sendReminderMail(id);
		return reminderRenewable(httpServletRequest,"Email sent Successfully to "+reminder.getUser().getUsername(),authentication);
	}
}
