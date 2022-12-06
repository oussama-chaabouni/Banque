package banque.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

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


import banque.entities.ScheduledInfoVirementPermanent;
import banque.repositories.ScheduledInfoVirementPermanentRepo;
import banque.scheduler.JobDataVirementPermanent;
import banque.scheduler.ScheduledJobVirementPermanentEpargne; 

@Service
public class GlobalRestServiceVirementPermanentEpargne {
	
	@Autowired
	Scheduler quartzScheduler;
	
	@Autowired
	ScheduledInfoVirementPermanentRepo scheduledInfoVirementPermanentRepo;
	
	@PostConstruct public void postContruct() { try {quartzScheduler.start(); }
	catch(SchedulerException exception) {
		System.out.println("scheduler throws exception" +exception); } 
	}
	
/*	@PostConstruct public void preDestroy() { try {quartzScheduler.shutdown(); }
	catch(SchedulerException exception) {
		System.out.println("scheduler throws exception" +exception); } 
	} */
	
	


public void schedule(JobDataVirementPermanent data) {
	
	String transferFrom = data.getTransferFrom();
	String transferTo = data.getTransferTo();
	float montant = data.getMontant();
	String motif = data.getMotif();
	LocalDateTime startTime = data.getStartTime();
	int duree = data.getDuree(); //counter = 9addech tet3awed men mara //par ex tetexecuta kol 30j
	int periode = data.getPeriode(); //bin counters 9adech testanna bech tetexecuta
	
	ZonedDateTime zonedDateTime =  ZonedDateTime.of(data.getStartTime(),ZoneId.of("Europe/Paris"));
	
	JobDataMap dataMap = new JobDataMap();
	//dataMap.put("test", "this is a test");
	
	
	ScheduledInfoVirementPermanent  scheduledInfoVirementPermanent = new ScheduledInfoVirementPermanent();
	scheduledInfoVirementPermanent.setTransferFrom(transferFrom);
	scheduledInfoVirementPermanent.setTransferTo(transferTo);
	scheduledInfoVirementPermanent.setMontant(montant);
	scheduledInfoVirementPermanent.setMotif(motif);
	scheduledInfoVirementPermanent.setStartTime(startTime);
	scheduledInfoVirementPermanent.setDuree(duree);
	scheduledInfoVirementPermanent.setPeriode(periode);
	
	
	
	JobDetail detail = JobBuilder.newJob(ScheduledJobVirementPermanentEpargne.class)
			.withIdentity(transferFrom,transferTo)
			.usingJobData(dataMap)
			.storeDurably(false)
			.build();
	
	Trigger trigger = TriggerBuilder.newTrigger().withIdentity(transferFrom,transferTo)
			.startAt(Date.from(zonedDateTime.toInstant()))
			//.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(periode*24).withRepeatCount(duree))
			.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(periode).withRepeatCount(duree))
			.build();
	
	/*Trigger tt = TriggerBuilder.newTrigger().withIdentity(fruitName)
			.startAt(Date.from(zonedDateTime.toInstant()))
			.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(gapDuration).withRepeatCount(24*counter))
			.build();
	*/
	
	
	try {
		quartzScheduler.scheduleJob(detail,trigger);
		scheduledInfoVirementPermanentRepo.save(scheduledInfoVirementPermanent);
		
	} catch (SchedulerException e) {
		
		e.printStackTrace();
	}
	
	
}


	

}
