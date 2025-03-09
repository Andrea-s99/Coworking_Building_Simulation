package coworking.agents;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.gis.Geography;
import repast.simphony.util.ContextUtils;

public class Coworker {
	
	private int stepCount = 0; // Contatore per il numero di tick
	
	private int id;
	
	private boolean hasReachedTarget = false;
	
	private  String startCity;
	
	private CoworkingBuilding assignedCoworking; // Coworking assegnato
	
	private Service allService;
	
    private Valle valle;
	
	private String nameCoworking; //nome città Coworking

	private Coordinate locationCoworking;
	
    private List<Coordinate> path; // Percorso assegnato
    
    private int currentTargetIndex = 0; // Indice del punto corrente nel percorso
	
	private double totalDistance = 0.0;  // Variabile per la distanza totale percorsa
	
	private boolean isReturning = false;
	
	private double totalFuelCost = 0.0; // Costo totale della benzina
	
	private double totalCO2Emission = 0.0; // Emissioni totali di CO2
	
	private int totalWorkingHours = 0;  // Variabile per ore di lavoro
	
	private int totalTravelHours = 0;   // Variabile per ore di viaggio

	private int stopTime = -1;  // Variabile per tenere traccia del tempo di lavoro (in passi)
	
	private boolean hasIncrementedOccupancy = false; // Flag per evitare doppio incremento
	
	private boolean inTravelAosta = false;
	
	private int completedCycles = 0; // Conta i cicli completati
	
	private int MAX_CYCLES = 0; 
	
	private List<Coordinate> originalPath; // Salva il percorso originale
	
	private double n_satisfaction;
	
	double n_totalFuelCost;
	
	double n_totalCO2Emission; 
	
	double n_totalDistance; 
	
	private Map<String, Coordinate> activeCoworkings = new HashMap<>(); //HashMap per salvare i coworking e le coordinate dei coworking attivi
	
	// Recupera i parametri dal file di configurazione
	Parameters params = RunEnvironment.getInstance().getParameters();

	// Recupera i valori dei parametri
	double fuelCostPerKm = params.getDouble("fuelCostPerKm");
	
	// Recupera i range di valori dai parametri di configurazione
	double co2MinEmissionPerLitre = params.getDouble("co2MinEmissionPerLitre");
	
	double co2MaxEmissionPerLitre = params.getDouble("co2MaxEmissionPerLitre");
	
	double co2EmissionPerLitre = co2MinEmissionPerLitre + Math.random() * (co2MaxEmissionPerLitre - co2MinEmissionPerLitre);
	
	private static final double FUEL_CONSUMPTION_PER_KM = 1.00; // Litri di benzina per km
	
	public Coworker(int id, CoworkingBuilding assignedCoworking, Service allService, Valle valle) {
        this.id = id;
        this.assignedCoworking = assignedCoworking;
        this.allService = allService;
        this.valle = valle;
    }
	
	// Variabili per memorizzare i dati intermedi
    private  List<Integer> totalTravelHoursList = new ArrayList<>();
    private  List<Integer> totalWorkingHoursList = new ArrayList<>();
    private  List<Double> totalFuelCostList = new ArrayList<>();
    private  List<Double> totalCO2EmissionList = new ArrayList<>();
    private  List<Double> totalDistanceList = new ArrayList<>();
    private  List<Double> totalSatisfactionList = new ArrayList<>();
    
    int rate;
    
    // Metodo per stampare tutti i valori delle variabili
    public  void printAllValues(int id) {
        System.out.println("\nValori per Coworker " + id + ":");
        
        System.out.println("\nCittà Coworker: " + startCity);
        
        

        System.out.println("\nTotal Travel Hours:");
        for (int i = 0; i < totalTravelHoursList.size(); i++) {
            System.out.println("Viaggio " + (i+1) + ": " + totalTravelHoursList.get(i) + " minuti");
        }

        System.out.println("\nTotal Working Hours:");
        for (int i = 0; i < totalWorkingHoursList.size(); i++) {
            System.out.println("Lavoro " + (i+1) + ": " + totalWorkingHoursList.get(i) + " ore");
        }

        System.out.println("\nTotal Fuel Cost:");
        for (int i = 0; i < totalFuelCostList.size(); i++) {
            System.out.println("Carburante " + (i+1) + ": " + String.format("%.2f", totalFuelCostList.get(i)) + " €");
        }

        System.out.println("\nTotal CO2 Emissions:");
        for (int i = 0; i < totalCO2EmissionList.size(); i++) {
            System.out.println("CO2 Emissione " + (i+1) + ": " + String.format("%.2f", totalCO2EmissionList.get(i)) + " g");
        }

        System.out.println("\nTotal Distance:");
        for (int i = 0; i < totalDistanceList.size(); i++) {
            System.out.println("Distanza " + (i+1) + ": " + String.format("%.2f", totalDistanceList.get(i)) + " km ");
        }
        
        System.out.println("\nTotal Satisfaction:");
        for (int i = 0; i < totalSatisfactionList.size(); i++) {
            System.out.println("Grado di soddisfazione " + (i+1) + ": " + String.format("%.2f", totalDistanceList.get(i)) + " % ");
        }
    }

    public List<Integer> getTotalTravelHoursList() {
        return totalTravelHoursList;
    }

    public List<Integer> getTotalWorkingHoursList() {
        return totalWorkingHoursList;
    }

    public List<Double> getTotalFuelCostList() {
        return totalFuelCostList;
    }

    public List<Double> getTotalCO2EmissionList() {
        return totalCO2EmissionList;
    }

    public List<Double> getTotalDistanceList() {
        return totalDistanceList;
    }
    
    public List<Double> getTotalSatisfactionList() {
        return totalSatisfactionList;
    }
	
	public int getId() {
	    return this.id;
	}
	
	private CoworkingBuilding getAssignedCoworking() {
        return assignedCoworking;
    }
	
	// Metodo per impostare il percorso
    public void setPath(List<Coordinate> path) {
        this.path = path;
        this.currentTargetIndex = 0; // Resetta l'indice del percorso
        this.hasReachedTarget = false; // Reset dello stato del coworker
    }

    // Metodo per ottenere il percorso 
    private List<Coordinate> getPath() {
        return this.path;
    }

    // Metodo per ottenere l'indice corrente
    public int getCurrentTargetIndex() {
        return this.currentTargetIndex;
    }

    // Metodo per verificare se ha raggiunto il target
    public boolean hasReachedTarget() {
        return this.hasReachedTarget;
    }
    
    public double getTotalFuelCost() {
        //return totalFuelCost;
    	return n_totalFuelCost;
    }

    public double getTotalCO2Emission() {
        //return totalCO2Emission;
    	return n_totalCO2Emission;
    }

    public double getTotalDistance() {
        //return totalDistance;
    	return n_totalDistance;
    }
    
    public int getTotalWorkingHours() {
    	return totalWorkingHours;
    }
    
    public int getTotalTravelHours() {
    	return totalTravelHours;
    }
    
   
    public double getSatisfaction() {
    	return n_satisfaction;
    }
 
    
    public String getStartCity() {
    	return startCity;
    }
    
 // Metodo per verificare se il coworker sta lavorando
    public boolean isWorking() {
        return stopTime > 0; // Se stopTime > 0, il coworker è impegnato a lavorare
    }

    // Metodo per verificare se il coworker è in viaggio
    public boolean isTraveling() {
        // Sta viaggiando se non sta lavorando e il coworker non è arrivato alla destinazione finale
        return stopTime <= 0 && (currentTargetIndex >= 0 && currentTargetIndex < path.size());
    }
    
    public boolean isAtHome() {
    	return currentTargetIndex < 0;
    }
    
    private List<Collaboration> collaborations = new ArrayList<>();
    
    public void addCollaboration(Coworker otherCoworker, String type) {
        Collaboration collaboration = new Collaboration(this, otherCoworker, type);
        collaborations.add(collaboration);
    }

    public boolean hasCollaboratedWith(Coworker otherCoworker) {
        return collaborations.stream()
                .anyMatch(c -> c.getCoworker2().equals(otherCoworker));
    }

    public List<Collaboration> getCollaborations() {
        return collaborations;
    }
    
    
    
	@ScheduledMethod(start = 0)
	public void init() {
		
	    Context<Object> context = ContextUtils.getContext(this);
	    Geography<Object> geography = (Geography<Object>) context.getProjection("Geography");

	    // Mappa dei percorsi per le città 
	    Map<String, List<Coordinate>> valleyPaths = ContextCreator.getValleyPaths(ContextCreator.getCoworking1Activate(),ContextCreator.getCoworking2Activate(), ContextCreator.getCoworking3Activate(),ContextCreator.getCoworking4Activate(), ContextCreator.getCoworking5Activate(),ContextCreator.getCoworking6Activate(), ContextCreator.getCoworking7Activate(),ContextCreator.getCoworking8Activate());
	    
	    // Seleziona una città ciclicamente in base all'ID del coworker
	    List<String> cityNames = new ArrayList<>(valleyPaths.keySet());
	    
	    String cityName = cityNames.get(id % cityNames.size());
	    
	    this.startCity = cityName;
	    

	    if (ContextCreator.getCoworking1Activate() && startCity == "Saint-Rhémy") {
	        activeCoworkings.put("Saint-Rhémy", new Coordinate(7.1773, 45.8217)); // Coworking
	    }
	    if (ContextCreator.getCoworking2Activate()  && startCity == "Cogne") {
	        activeCoworkings.put("Cogne", new Coordinate(7.3421, 45.6155));
	    }
	    
	    
	    if(assignedCoworking != null) {
	    	nameCoworking = assignedCoworking.getName();
	    	locationCoworking = assignedCoworking.getLocation();
	    }
	    
	    // Ottieni il percorso per la città selezionata
	    path = valleyPaths.get(cityName);
    
    
	    if (path == null || path.isEmpty()) {
	        throw new RuntimeException("Percorso non trovato per la città: " + cityName);
	    }
	    
	    // Salva il percorso originale
	    this.originalPath = new ArrayList<>(path);
	    
	    Parameters params = RunEnvironment.getInstance().getParameters();
		int timeSimulation = params.getInteger("timeSimulation");
	    
	    MAX_CYCLES = timeSimulation;
	    
	    rate = params.getInteger("rate");
	    
	    // Posiziona il coworker al primo punto del percorso
	    Coordinate startCoord = path.get(0);
	    Point startLocation = new GeometryFactory().createPoint(startCoord);
	    geography.move(this, startLocation);
	    
	}
	
	
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
			
		    // Incrementa il contatore di step
		    stepCount++;
		
		    Context<Object> context = ContextUtils.getContext(this);
		    Geography<Object> geography = (Geography<Object>) context.getProjection("Geography");
		
		    // Recupera la posizione attuale
		    Point location = (Point) geography.getGeometry(this);
		    
		        if((ContextCreator.enableCoworking1 || ContextCreator.enableCoworking2 || ContextCreator.enableCoworking3 || ContextCreator.enableCoworking4 || ContextCreator.enableCoworking5 || ContextCreator.enableCoworking6 || ContextCreator.enableCoworking7 || ContextCreator.enableCoworking8) && nameCoworking == startCity && assignedCoworking != null) {
		        	
		        	if (isReturning) {
		    
		        		handleReturn(location, geography);
		            
		        	} else if (isArrivedAtCoworkingOffice()) {
		            
		        		handleArrivalAtCoworking(nameCoworking, geography, location);
		            
		        	} else {
		            
		        		moveTowardsNextTarget(location, geography);
		            
		        	}
		
		        } else {
		        	
		        	handleNoActiveCoworking(location, geography);
		        	
		        }

    }
	
	
	private void handleReturn(Point location, Geography<Object> geography) {
	    
		if (currentTargetIndex < 0) {
	    
			totalTravelHours = stepCount;
	        
			printStatistics(startCity, totalTravelHours, totalWorkingHours, totalFuelCost, totalCO2Emission, totalDistance);
	        
			resetVariables();
	        
	        return;
	    
		}
	    
	    if((ContextCreator.enableCoworking1 || ContextCreator.enableCoworking2 || ContextCreator.enableCoworking3 || ContextCreator.enableCoworking4 || ContextCreator.enableCoworking5 || ContextCreator.enableCoworking6 || ContextCreator.enableCoworking7 || ContextCreator.enableCoworking8) && nameCoworking == startCity && hasIncrementedOccupancy && !inTravelAosta && assignedCoworking != null) {
	        
	    	assignedCoworking.removeCoworker(this);

    	    List<Coworker> currentCoworkers = assignedCoworking.getCurrentCoworkers();
    	    
	        hasIncrementedOccupancy = false; // Reset del flag dopo il ritorno
	        
	    }
	    	
	    Coordinate targetCoord = path.get(currentTargetIndex);
	    moveTowardsTarget(location, targetCoord, geography, isReturning);
	    
	}
	
	private void moveTowardsNextTarget(Point location, Geography<Object> geography) {
	    Coordinate targetCoord = path.get(currentTargetIndex);
	    moveTowardsTarget(location, targetCoord, geography, isReturning);
	}
	
	private boolean isArrivedAtCoworkingOffice() {
	    return currentTargetIndex >= path.size();
	}

	private void handleArrivalAtCoworking(String coworkingName, Geography<Object> geography, Point location) {
		
		if (stopTime == -1 && assignedCoworking.isFull()) {
			
	        redirectToAosta(startCity, geography, location);
	        
	        return;
	    
		} else {
	    	
	    	if (!hasIncrementedOccupancy && assignedCoworking.getName().equals(coworkingName) && !inTravelAosta && assignedCoworking != null) {
	    	    
	    	    List<Coworker> currentCoworkers = assignedCoworking.getCurrentCoworkers();

	    	    if (!currentCoworkers.contains(this)) {
	    	        assignedCoworking.addCoworker(this);
	    	    }

	    	    hasIncrementedOccupancy = true; // Imposta il flag a true per evitare incrementi successivi
	    	}

	
		    if (stopTime == -1) {
		        stopTime = new Random().nextInt(5) + 4; // Tempo casuale tra 4 e 8 ore
		        totalWorkingHours = stopTime;
		        
		        // Simula collaborazioni con una probabilità del 80% 
		        if(assignedCoworking.simulateCollaborations(0.8) != 0)
		        	valle.setCollaborations(valle.getCollaborations() + 1);
		        
		        //System.out.println("Coworker " + id + " ha raggiunto il coworking. Lavora per " + stopTime + " ore.");
		    }
	
		    if (stopTime > 0) {
		        
		    	stopTime--;
		        
		    	//System.out.println("Coworker " + id + " sta lavorando. Ore rimaste: " + stopTime);
		        return;
		    
		    }
	
		    isReturning = true;
		    currentTargetIndex = path.size() - 2; // Imposta il ritorno
		    //System.out.println("Coworker " + id + " ha finito di lavorare. Inizia il viaggio di ritorno.");
	    
		}
		    
	}

	private void redirectToAosta(String startCity, Geography<Object> geography, Point location) {
		
		// Imposta la variabile per indicare che il coworker è in viaggio verso Aosta
	    this.inTravelAosta = true;

	    // Imposta il nuovo percorso
	    this.path = ContextCreator.createNewValleyPaths(startCity);
	    
	    // Trova dinamicamente l'indice della coordinata desiderata nel percorso
	    int targetIndex = findIndexByCoordinateWithNaN(path, locationCoworking);

	    // Se l'indice viene trovato, usa la coordinata target per muovere il coworker
	    if (targetIndex != -1) {
	        
	    	Coordinate targetCoord = this.path.get(targetIndex);
	    
	        currentTargetIndex = 0;
	    
	        moveTowardsTarget(location, targetCoord, geography, isReturning); // Muovi il coworker verso il target
	    
	    } else {
	        
	    	System.err.println("Errore: La coordinata specificata non è stata trovata nel percorso.");
	    
	    }
	}

	private int findIndexByCoordinateWithNaN(List<Coordinate> path, Coordinate targetCoordinate) {
	    
		for (int i = 0; i < path.size(); i++) {
	    
			Coordinate coord = path.get(i);

	        // Confronta la longitudine e latitudine; ignora la componente Z (altezza) o considera NaN uguale a NaN
	        if (Double.compare(coord.getX(), targetCoordinate.getX()) == 0 &&
	            Double.compare(coord.getY(), targetCoordinate.getY()) == 0 &&
	            (Double.isNaN(coord.getZ()) && Double.isNaN(targetCoordinate.getZ()) || Double.compare(coord.getZ(), targetCoordinate.getZ()) == 0)) {
	            
	        	return i; // Restituisce l'indice trovato
	        
	        }
	    }
		
	    return -1; // Restituisce -1 se la coordinata non viene trovata
	
	}
	
	private void handleNoActiveCoworking(Point location, Geography<Object> geography) {
	    
		if (isReturning) {
	    
			handleReturn(location, geography);
	    
		} else if (isArrivedAtCoworkingOffice()) {
	    
			if (stopTime == -1) {
	        
				stopTime = new Random().nextInt(5) + 4;
	            totalWorkingHours = stopTime;
	            //System.out.println("Coworker " + id + " ha raggiunto Aosta. Lavora per " + stopTime + " ore.");
	        
			}

	        if (stopTime > 0) {
	        
	        	stopTime--;
	            //System.out.println("Coworker " + id + " sta lavorando. Ore rimaste: " + stopTime);
	            
	        	return;
	        
	        }

	        isReturning = true;
	        currentTargetIndex = this.path.size() - 2;
	        //System.out.println("Coworker " + id + " ha finito di lavorare. Inizia il viaggio di ritorno.");
	        
	    } else {
	    	
	        moveTowardsNextTarget(location, geography);
	        
	    }
		
	}

	
	private void printStatistics(String startCity, int totalTravelHours, int totalWorkingHours, double totalFuelCost, double totalCO2Emission, double totalDistance) {

		this.n_satisfaction = 100 * (1 - ((double) totalTravelHours / ((double) totalWorkingHours * 60)));
		
		if(allService.getCity().equals(startCity) && assignedCoworking != null && !this.inTravelAosta) {
			
			allService.setEarnings(allService.getEarnings() + (totalWorkingHours * 0.50) + allService.getEarnings()  * 0.0000003 * Math.pow(rate, 1.5));
			
			float tmpResidenti = (float) (valle.getResidenti() + (this.n_satisfaction/100) * 0.0022 * rate);

	    	valle.setResidenti(tmpResidenti);

	    }
		
		// Aggiungi i dati intermedi alle liste
        totalTravelHoursList.add(totalTravelHours);
        
        totalWorkingHoursList.add(totalWorkingHours);
        
        totalFuelCostList.add(n_totalFuelCost);
        
        totalCO2EmissionList.add(n_totalCO2Emission);
        
        totalDistanceList.add(n_totalDistance);
        
        totalSatisfactionList.add(n_satisfaction);
        
        this.n_totalFuelCost = 0;
        
        this.n_totalCO2Emission = 0;
        
        this.n_totalDistance = 0;
        
	}
	
	private void resetVariables() {
		
		// Ripristina l'indice per iniziare un nuovo ciclo di andata
        this.isReturning = false;
        
        this.stepCount = 0;
        
        this.totalWorkingHours = 0;
        
        this.totalTravelHours = 0;
        
        this.stopTime = -1;
        
        this.currentTargetIndex = 0; 
        
        this.path = this.originalPath;
        
        this.inTravelAosta = false;    
        
	}
	
	// Metodo per calcolare il movimento verso il target
	private void moveTowardsTarget(Point location, Coordinate targetCoord, Geography<Object> geography, boolean isReturning) {
	    
		double deltaX = targetCoord.x - location.getX();
	    
		double deltaY = targetCoord.y - location.getY();
	    
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	    
		// Se il coworker è vicino al target, passa al prossimo punto (ritorno)
        if (distance < 0.005) {
        	
        	if(isReturning)
        		currentTargetIndex--;
        	else
        		currentTargetIndex++;
            
        	return;
        }

	    double stepSize = 0.01;
	    
	    deltaX = (deltaX / distance) * stepSize;
	    
	    deltaY = (deltaY / distance) * stepSize;

	    // Aggiungi una piccola deviazione casuale sono PIU' DISTANZIATI. Esempio per mostrare i coworking nell'intorno del coworking
	    //deltaX += (Math.random() - 0.5) * 0.01;
	    
	    //deltaY += (Math.random() - 0.5) * 0.01;
	    
	    // Aggiungi una piccola deviazione casuale
	    deltaX += (Math.random() - 0.5) * 0.001;
	    
	    deltaY += (Math.random() - 0.5) * 0.001;

	    Coordinate newCoord = new Coordinate(
	        location.getX() + deltaX,
	        location.getY() + deltaY
	    );

	    double distanceTraveled = 1;
	    
	    double fuelUsed = distanceTraveled * FUEL_CONSUMPTION_PER_KM;
	    
	    double fuelCost = fuelUsed * fuelCostPerKm;
	    
	    double co2Emitted = fuelUsed * co2EmissionPerLitre;

	    totalFuelCost += fuelCost;
	    totalCO2Emission += co2Emitted;
	    totalDistance += distanceTraveled;
	    
		n_totalFuelCost += fuelCost; 
		n_totalCO2Emission += co2Emitted; 
		n_totalDistance += distanceTraveled; 
/*
	    System.out.println("Tick " + stepCount + ": Coworker " + id + " step:");
	    System.out.println(" - Distanza percorsa: " + String.format("%.2f", distanceTraveled) + " km");
	    System.out.println(" - Costo carburante: " + String.format("%.2f", fuelCost) + " €");
	    System.out.println(" - Emissioni CO2: " + String.format("%.2f", co2Emitted) + " g" + "\n");
*/

		// Sposta il coworker nella nuova posizione
        Point newLocation = new GeometryFactory().createPoint(newCoord);
        geography.move(this, newLocation);
        
	}
	

}