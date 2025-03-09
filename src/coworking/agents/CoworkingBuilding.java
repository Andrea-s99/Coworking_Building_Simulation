package coworking.agents;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;


public class CoworkingBuilding {

	    private int id; // Identificativo unico del coworking
	    
	    private String name; // Nome del coworking
	    
	    private int capacity; // Capacità massima del coworking
	    
	    private boolean isActive; // Stato attivo o inattivo del coworking
	    
	    private Coordinate location;
	    
	    private int currentOccupancy; // Numero di agenti nel coworking
	    
	    // Lista dei coworker attualmente nel coworking
	    private List<Coworker> currentCoworkers;
	    
	    // Costruttore
	    public CoworkingBuilding(int id, String name, int capacity, Coordinate coord) {
	        
	    	this.id = id;
	        
	    	this.name = name;
	        
	        this.capacity = capacity;
	        
	        this.location = coord;
	        
	        this.isActive = true; // Per default, il coworking è attivo
	        
	        this.currentOccupancy = 0;
	    
	        this.currentCoworkers = new ArrayList<>();
	    
	    }

	    // Getter e Setter
	    public int getId() {
	        return id;
	    }

	    public  String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public synchronized int getCapacity() {
	        return capacity;
	    }

	    public void setCapacity(int capacity) {
	        this.capacity = capacity;
	    }

	    public boolean isActive() {
	        return isActive;
	    }

	    public void setActive(boolean active) {
	        isActive = active;
	    }

	    public Coordinate getLocation() {
	        return location;
	    }

	    public void setLocation(Coordinate location) {
	        this.location = location;
	    }
	    
	    public void incrementOccupancy() {
	        if (currentOccupancy < capacity) {
	            currentOccupancy++;
	            //System.out.println(" incremented occupancy. CurrentOccupancy = " + currentOccupancy);
	        }
	    }

	    // Metodo per decrementare il numero di agenti nel coworking
	    public void decrementOccupancy() {
	        if (currentOccupancy > 0) {
	            currentOccupancy--;
	            //System.out.println(" decremented occupancy. CurrentOccupancy = " + currentOccupancy);
	        }
	    }

	    // Verifica se il coworking è pieno
	    public boolean isFull() {
	        return currentOccupancy >= capacity;
	    }
	    
	    public int getOccupancy() {
	    	return currentOccupancy;
	    }
	    
	    // Metodo per aggiungere un coworker al coworking
	    public  void addCoworker(Coworker coworker) {
	        
	    	if (!isFull() && !currentCoworkers.contains(coworker)) {
	    		currentCoworkers.add(coworker);
	            
	    		incrementOccupancy();	            
	            //System.out.println("Coworker added: " + coworker.getId());
	    	}
	    	
	    }

	    // Metodo per rimuovere un coworker dal coworking
	    public void removeCoworker(Coworker coworker) {
	        
	    	if (currentCoworkers.remove(coworker)) {
	            decrementOccupancy();
	            //System.out.println("Coworker removed: " + coworker.getId());
	        }
	    	
	    }

	    // Metodo per ottenere la lista di coworker attualmente nel coworking
	    public List<Coworker> getCurrentCoworkers() {
	        return new ArrayList<>(currentCoworkers); // Ritorna una copia della lista per evitare modifiche esterne
	    }
	    
	    
		public int simulateCollaborations(double collaborationProbability) {
			    	
			    	int collaborations = 0;
			    	
			        List<Coworker> currentCoworkers = getCurrentCoworkers();
		
			        // Verifica se ci sono almeno due coworker 
			        if (currentCoworkers.size() < 2) {
			            
			            return collaborations; 
			        
			        }
			        
			        if (Math.random() <= collaborationProbability) {
			        	
			        	collaborations++;
			        
			        }
		
			        return collaborations;
		}    
}