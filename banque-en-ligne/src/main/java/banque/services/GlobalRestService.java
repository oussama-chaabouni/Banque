package banque.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import banque.entities.ScheduledInfo;
import banque.repositories.ScheduledInfoRepo;
import banque.repositories.TransactionRepository;
import banque.scheduler.JobData;
import banque.scheduler.ScheduledJob; 

@Service
public class GlobalRestService {
	
	@Autowired
	TransactionRepository transactionRepo;
	
	@Autowired
	Scheduler quartzScheduler;
	
	@Autowired
	ScheduledInfoRepo scheduledInfoRepo;
	
	@PostConstruct public void postContruct() { try {quartzScheduler.start(); }
	catch(SchedulerException exception) {
		System.out.println("scheduler throws exception" +exception); } 
	}
	
/*	@PostConstruct public void preDestroy() { try {quartzScheduler.shutdown(); }
	catch(SchedulerException exception) {
		System.out.println("scheduler throws exception" +exception); } 
	} */
	
	


public void schedule(JobData data) {
	
	String transferFrom = data.getTransferFrom();
	String transferTo = data.getTransferTo();
	float montant = data.getMontant();
	String motif = data.getMotif();
	LocalDateTime startTime = data.getStartTime();
	
	
	//int counter = data.getCounter(); //counter = 9addech tet3awed men mara //par ex tetexecuta kol 30j
	//int gapDuration = data.getGapDuration(); //bin counters 9adech testanna bech tetexecuta
	
	ZonedDateTime zonedDateTime =  ZonedDateTime.of(data.getStartTime(),ZoneId.of("Europe/Paris"));
	
	JobDataMap dataMap = new JobDataMap();
	//dataMap.put("test", "this is a test");
	
	
	ScheduledInfo  scheduledInfo = new ScheduledInfo();
	scheduledInfo.setTransferFrom(transferFrom);
	scheduledInfo.setTransferTo(transferTo);
	scheduledInfo.setMontant(montant);
	scheduledInfo.setMotif(motif);
	scheduledInfo.setStartTime(startTime);
	
	JobDetail detail = JobBuilder.newJob(ScheduledJob.class)
			.withIdentity(transferFrom,transferTo)
			.usingJobData(dataMap)
			.storeDurably(false)
			.build();
	
	Trigger trigger = TriggerBuilder.newTrigger().withIdentity(transferFrom,transferTo)
			.startAt(Date.from(zonedDateTime.toInstant()))
			.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).withRepeatCount(0))
			.build();
	
	/*Trigger tt = TriggerBuilder.newTrigger().withIdentity(fruitName)
			.startAt(Date.from(zonedDateTime.toInstant()))
			.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(gapDuration).withRepeatCount(24*counter))
			.build();
	*/
	
	
	try {
		quartzScheduler.scheduleJob(detail,trigger);
		scheduledInfoRepo.save(scheduledInfo);
		
	} catch (SchedulerException e) {
		
		e.printStackTrace();
	}
	
	
}


	

}
