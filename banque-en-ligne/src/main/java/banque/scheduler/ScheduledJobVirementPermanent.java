package banque.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import banque.entities.ScheduledInfo;
import banque.repositories.CompteCourantRepository;
import banque.repositories.ScheduledInfoRepo;
import banque.repositories.ScheduledInfoVirementPermanentRepo;
import banque.repositories.TransactionRepository;

@Component
public class ScheduledJobVirementPermanent extends QuartzJobBean{

	LocalDateTime currentDateTime = LocalDateTime.now();
	
	@Autowired
	TransactionRepository transactionRep;
	
	@Autowired
	CompteCourantRepository compteCourantRep;
	
	@Autowired
	ScheduledInfoVirementPermanentRepo scheduledInfoVirementPermanentRep;
	
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap mergedJobDataMap= context.getMergedJobDataMap();
		for(String key : mergedJobDataMap.getKeys())
		{
			System.out.println("from scheduled job::" + mergedJobDataMap.get(key));
			
		}	
		
//je recupere de ma table
		long transactionIdPermanent= scheduledInfoVirementPermanentRep.getTransactionIdPermanent();
		String transferFrom = scheduledInfoVirementPermanentRep.getTransferFrom(transactionIdPermanent);
		String transferTo = scheduledInfoVirementPermanentRep.getTransferTo(transactionIdPermanent);
		float montant = scheduledInfoVirementPermanentRep.getMontant(transactionIdPermanent);
		String motif = scheduledInfoVirementPermanentRep.getMotif(transactionIdPermanent);		

		int duree = scheduledInfoVirementPermanentRep.getDuree(transactionIdPermanent);
		int periode = scheduledInfoVirementPermanentRep.getPeriode(transactionIdPermanent);
		
		
		
		//update solde
		float currentSoldeofCompteCourantTransferingFrom = compteCourantRep.getSoldeCompteCourant(transferFrom);
		float currentSoldeofCompteCourantTransferingTo = compteCourantRep.getSoldeCompteCourant(transferTo);
		float newSoldetransferFrom = currentSoldeofCompteCourantTransferingFrom - montant;
		float newSoldetransferTo = currentSoldeofCompteCourantTransferingTo + montant;
		
		//update comptecourant
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferFrom, transferFrom);
		compteCourantRep.ChangeSoldeCompteCourantByRib(newSoldetransferTo, transferTo);
		transactionRep.ajouterTransaction(transferFrom,transferTo, "Virement_Permanent", montant, motif, "virement Permanent effectué avec succès", montant +" Dinars Transféré", currentDateTime);
		
		System.out.println(currentDateTime);
			
		
		
		
	}

}
