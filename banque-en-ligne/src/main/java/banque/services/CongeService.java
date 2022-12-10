package banque.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banque.entities.Conge;
import banque.entities.Employe;
import banque.entities.HolidaysCalendar;
import banque.repositories.CongeRepo;
import banque.repositories.EmployeRepo;

@Service
public class CongeService implements ICongeService{
@Autowired
CongeRepo congerep;
@Autowired
EmployeRepo emrep;

List<HolidaysCalendar> holidaysList = new ArrayList<HolidaysCalendar>() {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

{
    
    add(new HolidaysCalendar(LocalDate.of(2023,1,1),"Nouvel An"));
    add(new HolidaysCalendar(LocalDate.of(2023,3,20), "	Independence Day"));
    add(new HolidaysCalendar(LocalDate.of(2023,4,9), "	Martyrs' Day"));
    add(new HolidaysCalendar(LocalDate.of(2023,4,21), " 	Eid al-Fitr"));
    add(new HolidaysCalendar(LocalDate.of(2023,4,22), "	Eid al-Fitr Holiday "));
    add(new HolidaysCalendar(LocalDate.of(2023,4,23), "	Eid al-Fitr Holiday"));
    add(new HolidaysCalendar(LocalDate.of(2023,5,1), "Labour Day"));
    add(new HolidaysCalendar(LocalDate.of(2023,5,28), "Eid al-Adha"));
    add(new HolidaysCalendar(LocalDate.of(2023,5,29), "Eid al-Adha Holiday"));
    add(new HolidaysCalendar(LocalDate.of(2023,7,19), "Islamic New Year"));
    add(new HolidaysCalendar(LocalDate.of(2023,7,25), "Republic Day"));
    add(new HolidaysCalendar(LocalDate.of(2023,8,13), "Women's Day"));
    add(new HolidaysCalendar(LocalDate.of(2023,9,27), "Prophet Muhammad's Birthday"));
    add(new HolidaysCalendar(LocalDate.of(2023,10,15), "Evacuation Day"));
    add(new HolidaysCalendar(LocalDate.of(2023,12,17), "Revolution and Youth Day"));
   }};


	@Override
	public List<Conge> retrieveAllConges() {
		return (List<Conge>) congerep.findAll();
	}

	@Override
	public Conge addConge(Conge c, Long idEmploye) {
		Employe e = emrep.findById(idEmploye).orElse(null);
		e.getConges().add(c);
		c.setEmployeConges(e);
		 congerep.save(c);
		 return c;
	}

	@Override
	public void deleteConge(Long id) {
		 congerep.deleteById(id);
		
	}

	@Override
	public Conge updateConge(Conge conge) {
		congerep.save(conge);
		return conge;
	}

	@Override
	public Conge retrieveCongeById(Long id) {
		congerep.findById(id);
		return congerep.findById(id).orElse(null);
	}

	/*@Override
	public List<Conge> getCongeByEmployee(Long idEmploye) {
		
		return congerep.getCongeByEmploye(idEmploye);
	}*/
	
	@Override
    public void acceptConge(Conge conge, Long idConge) {
	Conge conge1=retrieveCongeById(idConge);
            String res1 = "Congé accepté";
            String res2="Congé refusé";
			int nbr = 0;
            long days = ChronoUnit.DAYS.between(conge1.getStartDateConge(), conge1.getEndDateConge());
            System.out.println(days);
 if (conge1.getSoldeConge() >= days){
                    for (HolidaysCalendar day : holidaysList) {
                           if ((day.getDate().isAfter(conge1.getStartDateConge()) && (day.getDate().isBefore(conge1.getEndDateConge()))))
                            		{
                                    nbr +=1;
                            }
                    }
                    conge1.setConfirmation(true);
                    conge1.setSoldeConge((int) ((conge1.getSoldeConge())-(days+nbr)));
                    congerep.save(conge1);
                    
                    
                    }
                    		
                    
		
            }        
    

	@Override
	public List<Conge> getCongeByEmployee(Long idEmploye) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
