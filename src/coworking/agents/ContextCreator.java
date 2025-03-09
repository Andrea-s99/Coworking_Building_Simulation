package coworking.agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import repast.simphony.context.Context;
import repast.simphony.context.DefaultContext;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;
import repast.simphony.space.graph.Network;


public class ContextCreator extends DefaultContext<Object> implements ContextBuilder<Object> {

	int numPerson;
	static boolean enableCoworking1;
	static boolean enableCoworking2;
	static boolean enableCoworking3;
	static boolean enableCoworking4;
	static boolean enableCoworking5;
	static boolean enableCoworking6;
	static boolean enableCoworking7;
	static boolean enableCoworking8;
	
	
	static ArrayList<CoworkingBuilding> coworking;
	

	static List<Coworker> coworkerList = new ArrayList<>();
	
	List<Service> allServices = new ArrayList<>();
	
	List<Valle> allValli = new ArrayList<>();
	
	
	public static ArrayList<CoworkingBuilding> getCoworkingList() {
        return coworking;
    }
	
	// Metodo che crea i percorsi, prendendo in considerazione lo stato attivo di ogni coworking
    public static Map<String, List<Coordinate>> createValleyPaths(boolean coworking1Active, boolean coworking2Active, boolean coworking3Active, boolean coworking4Active, boolean coworking5Active, boolean coworking6Active, boolean coworking7Active, boolean coworking8Active) {
        Map<String, List<Coordinate>> paths = new HashMap<>();

        // Percorso da Saint-Rhémy (Valle del Gran San Bernardo)
        if (coworking1Active) {
        	
                // Se il coworking non è pieno, il coworker segue il percorso normale
                paths.put("Saint-Rhémy", Arrays.asList(
                    new Coordinate(7.1834, 45.8356), // Saint-Rhémy
                    new Coordinate(7.1873, 45.8332), // Punti intermedi
                    new Coordinate(7.1877, 45.8315), 
                    new Coordinate(7.1874, 45.8273), 
                    new Coordinate(7.1773, 45.8217)  // Coworking
                ));
            
        } else {
            paths.put("Saint-Rhémy", Arrays.asList(
                new Coordinate(7.1834, 45.8356), // Saint-Rhémy
                new Coordinate(7.1889, 45.8234), // Punti intermedi
                new Coordinate(7.2228, 45.8226),
                new Coordinate(7.2376, 45.8164),
                new Coordinate(7.2923, 45.7912),
                new Coordinate(7.3063, 45.7677),
                new Coordinate(7.3074, 45.7736),
                new Coordinate(7.3200, 45.7375)  // Aosta
            ));
        }

        // Percorso da Cogne (Valle di Cogne)
        if (coworking2Active) {
            paths.put("Cogne", Arrays.asList(
                new Coordinate(7.3561, 45.6084), // Cogne
                new Coordinate(7.3421, 45.6155)  // Coworking
            ));
        } else {
            paths.put("Cogne", Arrays.asList(
                new Coordinate(7.3561, 45.6084), // Cogne
                new Coordinate(7.3241, 45.6292), // Punto intermedio 1
                new Coordinate(7.2336, 45.6606), // Punto intermedio 2
                new Coordinate(7.2259, 45.6794), // Punto intermedio 3
                new Coordinate(7.2590, 45.7154), // Punto intermedio 4
                new Coordinate(7.3200, 45.7375)  // Aosta
            ));
        }

     // Percorso da Courmayeur 
        if (coworking3Active) {
        	 // Aggiungi altri percorsi per le altre valli...
	        paths.put("Courmayeur", Arrays.asList(
	            new Coordinate(6.9720, 45.7912), // Courmayeur
	            new Coordinate(6.9678, 45.7928), // Punto intermedio 1
	            new Coordinate(6.9655, 45.7922)  // COWORKING
	        ));
        }else {
	        
	        paths.put("Courmayeur", Arrays.asList(
	            new Coordinate(6.9720, 45.7912), // Courmayeur
	            new Coordinate(6.9960, 45.7651), // Punto intermedio 1
	            new Coordinate(7.0364, 45.7578), // Punto intermedio 2
	            new Coordinate(7.1337, 45.7058), // Punto intermedio 3
	            new Coordinate(7.1783, 45.7060), // Punto intermedio 4
	            new Coordinate(7.2876, 45.7298), // Punto intermedio 5
	            new Coordinate(7.3200, 45.7375)  // Aosta
	        ));
        }
        
        if (coworking4Active) {
        	paths.put("La Thuile", Arrays.asList(
		    	new Coordinate(6.9499, 45.7168), // La Thuile
		    	new Coordinate(6.9478, 45.7165), // Punto intermedio 
		        new Coordinate(6.9457, 45.7166), // Punto intermedio
		        new Coordinate(6.9433, 45.7131)  // COWORKING
		    ));
        }else{
	        // Percorso da La Thuile (Valle di La Thuile)
		    paths.put("La Thuile", Arrays.asList(
		    	new Coordinate(6.9499, 45.7168), // La Thuile
		        new Coordinate(6.9871, 45.7638), // Punto intermedio 1
		        new Coordinate(7.0337, 45.7594), // Punto intermedio 2
		        new Coordinate(7.1300, 45.7057), // Punto intermedio 3
		        new Coordinate(7.1791, 45.7056), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
		    ));
        }
		    
        if (coworking5Active) {
        	// Percorso da Champoluc (Valle di Ayas)
    	    paths.put("Champoluc", Arrays.asList(
    	    	new Coordinate(7.7257, 45.8315), // Champoluc
    	    	new Coordinate(7.7283, 45.8348), // Punto intermedio 
    	    	new Coordinate(7.7325, 45.8385)  // COWORKING
    	    ));
        }else {
        	// Percorso da Champoluc (Valle di Ayas)
    	    paths.put("Champoluc", Arrays.asList(
    	    	new Coordinate(7.7257, 45.8315), // Champoluc
    	    	//PERCORSO PRECISO
    	    	new Coordinate(7.6984, 45.8160), // Punto intermedio 1
    	    	new Coordinate(7.6944, 45.8074), // Punto intermedio 2
    	    	new Coordinate(7.6984, 45.7842), // Punto intermedio 3
    	    	new Coordinate(7.7296, 45.7581), // Punto intermedio 4
    	    	new Coordinate(7.7447, 45.7465), // Punto intermedio 5
    	    	new Coordinate(7.7466, 45.7395), // Punto intermedio 6
    	    	new Coordinate(7.7458, 45.7246), // Punto intermedio 7
    	    	new Coordinate(7.7366, 45.7166), // Punto intermedio 8
    	    	new Coordinate(7.7334, 45.7072), // Punto intermedio 9
    	    	new Coordinate(7.7145, 45.6977), // Punto intermedio 10
    	    	new Coordinate(7.7042, 45.6900), // Punto intermedio 11
    	    	new Coordinate(7.7056, 45.6844), // Punto intermedio 12
    	    	new Coordinate(7.6906, 45.6664), // Punto intermedio 13
    	    	new Coordinate(7.6678, 45.6871), // Punto intermedio 14
    	    	new Coordinate(7.6655, 45.7355), // Punto intermedio 15
    	    	new Coordinate(7.6418, 45.7515), // Punto intermedio 16
    	        new Coordinate(7.3200, 45.7375)  // Aosta
    	    ));
        }
	    
        if (coworking6Active) {
        	// Percorso da Buisson (Valtournenche)
		    paths.put("Buisson", Arrays.asList(
		    	new Coordinate(7.6002, 45.8314),// Buisson
		        new Coordinate(7.6016, 45.8331), // Punto intermedio 
		        new Coordinate(7.6025, 45.8333), // Punto intermedio 
		        new Coordinate(7.6054, 45.8378) // COWORKING
		    ));
        }else {
		    // Percorso da Buisson (Valtournenche)
		    paths.put("Buisson", Arrays.asList(
		    	new Coordinate(7.6002, 45.8314),// Buisson
		        new Coordinate(7.5876, 45.8059), // Punto intermedio 1
		        new Coordinate(7.5925, 45.7765), // Punto intermedio 2
		        new Coordinate(7.6124, 45.7486), // Punto intermedio 3
		        new Coordinate(7.4947, 45.7465), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
		    ));
        }
		
        if (coworking7Active) {
        	// Percorso da Point-Saint-Martin (Bassa Valle)
		    paths.put("Point-Saint-Martin", Arrays.asList(
		    	new Coordinate(7.7983, 45.5950), // Point-Saint-Martin
		        new Coordinate(7.7986, 45.5966), // Punto intermedio 1
		        new Coordinate(7.8001, 45.5985), // Punto intermedio 2
		        new Coordinate(7.7990, 45.5996), // Punto intermedio 3
		        new Coordinate(7.7970, 45.6005) //COWORKING
		    ));
        }else {
		    // Percorso da Point-Saint-Martin (Bassa Valle)
		    paths.put("Point-Saint-Martin", Arrays.asList(
		    	new Coordinate(7.7983, 45.5950), // Point-Saint-Martin
		        new Coordinate(7.6633, 45.6844), // Punto intermedio 1
		        new Coordinate(7.6662, 45.7355), // Punto intermedio 2
		        new Coordinate(7.6324, 45.7526), // Punto intermedio 3
		        new Coordinate(7.4932, 45.7464), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
		    ));
        }
		
        if (coworking8Active) {
        	// Percorso da Gressoney-Saint-Jean (Valle di Gressoney)
		    paths.put("Grosseinay-Saint-Jean", Arrays.asList(
		    	new Coordinate(7.8258, 45.7796), // Grosseinay-Saint-Jean
		    	new Coordinate(7.8269, 45.7777), // Punto intermedio 1
		    	new Coordinate(7.8266, 45.7738), // Punto intermedio 2
		    	new Coordinate(7.8330, 45.7696) //COWORKING
		    ));
        }else {
		    // Percorso da Gressoney-Saint-Jean (Valle di Gressoney)
		    paths.put("Grosseinay-Saint-Jean", Arrays.asList(
		    	new Coordinate(7.8258, 45.7796), // Grosseinay-Saint-Jean
		    	new Coordinate(7.8816, 45.7132), // Punto intermedio 1
		    	new Coordinate(7.8538, 45.6874), // Punto intermedio 2
		    	new Coordinate(7.8597, 45.6479), // Punto intermedio 3
		    	new Coordinate(7.8150, 45.6155), // Punto intermedio 4
		    	new Coordinate(7.7983, 45.5950), // Point-Saint-Martin
		        new Coordinate(7.6633, 45.6844), // Punto intermedio 1
		        new Coordinate(7.6662, 45.7355), // Punto intermedio 2
		        new Coordinate(7.6324, 45.7526), // Punto intermedio 3
		        new Coordinate(7.4932, 45.7464), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
		    ));
        }

	    return paths;
    }

    // Nuovo metodo che restituisce i percorsi delle valli, utilizzando i parametri esterni
    public static Map<String, List<Coordinate>> getValleyPaths(boolean coworking1Active, boolean coworking2Active, boolean coworking3Active, boolean coworking4Active, boolean coworking5Active, boolean coworking6Active, boolean coworking7Active, boolean coworking8Active){
        return createValleyPaths(enableCoworking1, enableCoworking2, enableCoworking3, enableCoworking4, enableCoworking5, enableCoworking6, enableCoworking7, enableCoworking8);  // Restituisce la mappa creata
    }
    
 // Metodo che crea i percorsi, prendendo in considerazione lo stato attivo di ogni coworking
    public static List<Coordinate> createNewValleyPaths(String city) {
        // Inizializza la lista per il percorso specifico
        List<Coordinate> path = new ArrayList<>();

        // Percorso da Saint-Rhémy (Valle del Gran San Bernardo)
        if (city.equals("Saint-Rhémy")) {
            path = Arrays.asList(
                new Coordinate(7.1834, 45.8356), // Saint-Rhémy
                new Coordinate(7.1873, 45.8332), // Punti intermedi
                new Coordinate(7.1877, 45.8315), // Punto intermedio
                new Coordinate(7.1773, 45.8217), // Coworking
                new Coordinate(7.2148, 45.8250), // Punti intermedi
                new Coordinate(7.2923, 45.7912),
                new Coordinate(7.3063, 45.7677),
                new Coordinate(7.3074, 45.7736),
                new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        // Percorso da Cogne (Valle di Cogne)
         if (city.equals("Cogne")) {
            path = Arrays.asList(
                new Coordinate(7.3561, 45.6084), // Cogne
                new Coordinate(7.3421, 45.6155),  // Coworking
                new Coordinate(7.3200, 45.6292), // Punto intermedio 1
                new Coordinate(7.2336, 45.6606), // Punto intermedio 2
                new Coordinate(7.2259, 45.6794), // Punto intermedio 3
                new Coordinate(7.2590, 45.7154), // Punto intermedio 4
                new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        
        
        // Percorso da Courmayeur
         if (city.equals("Courmayeur")) {
            path = Arrays.asList(
                new Coordinate(6.9720, 45.7912), // Courmayeur
                new Coordinate(6.9678, 45.7928), // Punto intermedio 1
                new Coordinate(6.9655, 45.7922),  // Coworking
                new Coordinate(6.9960, 45.7651), // Punto intermedio 1
	            new Coordinate(7.0364, 45.7578), // Punto intermedio 2
	            new Coordinate(7.1337, 45.7058), // Punto intermedio 3
	            new Coordinate(7.1783, 45.7060), // Punto intermedio 4
	            new Coordinate(7.2876, 45.7298), // Punto intermedio 5
	            new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        // Percorso da La Thuile
         if (city.equals("La Thuile")) {
            path = Arrays.asList(
                new Coordinate(6.9499, 45.7168), // La Thuile
                new Coordinate(6.9478, 45.7165), // Punto intermedio
                new Coordinate(6.9457, 45.7166), // Punto intermedio
                new Coordinate(6.9433, 45.7131),  // Coworking
                new Coordinate(6.9871, 45.7638), // Punto intermedio 1
		        new Coordinate(7.0337, 45.7594), // Punto intermedio 2
		        new Coordinate(7.1300, 45.7057), // Punto intermedio 3
		        new Coordinate(7.1791, 45.7056), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        // Percorso da Champoluc (Valle di Ayas)
         if (city.equals("Champoluc")) {
            path = Arrays.asList(
                new Coordinate(7.7257, 45.8315), // Champoluc
                new Coordinate(7.7283, 45.8348), // Punto intermedio
                new Coordinate(7.7325, 45.8385),  // Coworking
                new Coordinate(7.6984, 45.8160), // Punto intermedio 1
    	    	new Coordinate(7.6944, 45.8074), // Punto intermedio 2
    	    	new Coordinate(7.6984, 45.7842), // Punto intermedio 3
    	    	new Coordinate(7.7296, 45.7581), // Punto intermedio 4
    	    	new Coordinate(7.7447, 45.7465), // Punto intermedio 5
    	    	new Coordinate(7.7466, 45.7395), // Punto intermedio 6
    	    	new Coordinate(7.7458, 45.7246), // Punto intermedio 7
    	    	new Coordinate(7.7366, 45.7166), // Punto intermedio 8
    	    	new Coordinate(7.7334, 45.7072), // Punto intermedio 9
    	    	new Coordinate(7.7145, 45.6977), // Punto intermedio 10
    	    	new Coordinate(7.7042, 45.6900), // Punto intermedio 11
    	    	new Coordinate(7.7056, 45.6844), // Punto intermedio 12
    	    	new Coordinate(7.6906, 45.6664), // Punto intermedio 13
    	    	new Coordinate(7.6678, 45.6871), // Punto intermedio 14
    	    	new Coordinate(7.6655, 45.7355), // Punto intermedio 15
    	    	new Coordinate(7.6418, 45.7515), // Punto intermedio 16
    	        new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        // Percorso da Buisson (Valtournenche)
         if (city.equals("Buisson")) {
            path = Arrays.asList(
                new Coordinate(7.6002, 45.8314), // Buisson
                new Coordinate(7.6016, 45.8331), // Punto intermedio
                new Coordinate(7.6025, 45.8333), // Punto intermedio
                new Coordinate(7.6054, 45.8378),  // Coworking
                new Coordinate(7.5876, 45.8059), // Punto intermedio 1
		        new Coordinate(7.5925, 45.7765), // Punto intermedio 2
		        new Coordinate(7.6124, 45.7486), // Punto intermedio 3
		        new Coordinate(7.4947, 45.7465), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        // Percorso da Point-Saint-Martin (Bassa Valle)
         if (city.equals("Point-Saint-Martin")) {
            path = Arrays.asList(
                new Coordinate(7.7983, 45.5950), // Point-Saint-Martin
                new Coordinate(7.7986, 45.5966), // Punto intermedio 1
                new Coordinate(7.8001, 45.5985), // Punto intermedio 2
                new Coordinate(7.7990, 45.5996), // Punto intermedio 3
                new Coordinate(7.7970, 45.6005),  // Coworking
                new Coordinate(7.6662, 45.7355), // Punto intermedio 2
		        new Coordinate(7.6324, 45.7526), // Punto intermedio 3
		        new Coordinate(7.4932, 45.7464), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        // Percorso da Gressoney-Saint-Jean (Valle di Gressoney)
         if (city.equals("Grosseinay-Saint-Jean")) {
            path = Arrays.asList(
                new Coordinate(7.8258, 45.7796), // Grosseinay-Saint-Jean
                new Coordinate(7.8269, 45.7777), // Punto intermedio 1
                new Coordinate(7.8266, 45.7738), // Punto intermedio 2
                new Coordinate(7.8330, 45.7696),  // Coworking
                new Coordinate(7.8816, 45.7132), // Punto intermedio 1
		    	new Coordinate(7.8538, 45.6874), // Punto intermedio 2
		    	new Coordinate(7.8597, 45.6479), // Punto intermedio 3
		    	new Coordinate(7.8150, 45.6155), // Punto intermedio 4
		    	new Coordinate(7.7983, 45.5950), // Point-Saint-Martin
		        new Coordinate(7.6633, 45.6844), // Punto intermedio 1
		        new Coordinate(7.6662, 45.7355), // Punto intermedio 2
		        new Coordinate(7.6324, 45.7526), // Punto intermedio 3
		        new Coordinate(7.4932, 45.7464), // Punto intermedio 4
		        new Coordinate(7.3200, 45.7375)  // Aosta
            );
        }

        return path;
    }

    
    public static boolean getCoworking1Activate() {
    	return enableCoworking1;
    }
    
    public static boolean getCoworking2Activate() {
    	return enableCoworking2;
    }
    
    public static boolean getCoworking3Activate() {
    	return enableCoworking3;
    }
    
    public static boolean getCoworking4Activate() {
    	return enableCoworking4;
    }
    
    public static boolean getCoworking5Activate() {
    	return enableCoworking5;
    }
    
    public static boolean getCoworking6Activate() {
    	return enableCoworking6;
    }
    
    public static boolean getCoworking7Activate() {
    	return enableCoworking7;
    }
    
    public static boolean getCoworking8Activate() {
    	return enableCoworking8;
    }
    
    
 // Funzione per generare uno spostamento casuale
    public static double getRandomOffset() {
        return (Math.random() - 0.5) * 0.002; // Genera un numero casuale tra -0.001 e 0.001
    }

    // Funzione per creare un ristorante in una città
    public static void createRestaurantInCity(Context<Object> context, Geography<Object> geography, String cityName, Coordinate cityCoord) {
        // Calcola lo spostamento casuale per la latitudine e la longitudine
        double latitudeOffset = getRandomOffset();
        double longitudeOffset = getRandomOffset();
        
        // Calcola le nuove coordinate per il ristorante
        double newLatitude = cityCoord.y + latitudeOffset;
        double newLongitude = cityCoord.x + longitudeOffset;
        
        // Crea il ristorante con le nuove coordinate
        Coordinate restaurantCoord = new Coordinate(newLongitude, newLatitude);
        Service restaurant = new Service(1, "Restaurant", cityName, restaurantCoord);
        
        // Aggiungi il ristorante al contesto
        context.add(restaurant);
        
        // Muovi il ristorante alla nuova posizione
        geography.move(restaurant, new GeometryFactory().createPoint(restaurantCoord));
        
        // Puoi aggiungere altre logiche, come la stampa delle nuove coordinate
        System.out.println("Ristorante aggiunto a " + cityName + " alle coordinate: " + restaurantCoord);
    }
    			
    public Context<Object> build(Context<Object> context) {	
    	
    	coworkerList = new ArrayList<>();
    	
    	coworking = new ArrayList<>();
    	
    	// Create the Geography projection that is used to store geographic locations
		// of agents in the model.
		GeographyParameters<Object> geoParams = new GeographyParameters<Object>();
		Geography<Object> geography = GeographyFactoryFinder.createGeographyFactory(null)
				.createGeography("Geography", context, geoParams);

		// Create the Network projection that is used to create the infection network.
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>(
				"coworking network", context, true);
		Network<Object> net = netBuilder.buildNetwork();
		
		// Geometry factory is used to create geometries
		GeometryFactory fac = new GeometryFactory();
		
		Coordinate coordAosta = new Coordinate(7.3200, 45.7375);
		Aosta aosta = new Aosta(coordAosta);
		context.add(aosta);
	    geography.move(aosta, new GeometryFactory().createPoint(coordAosta));
	
        
		// Crea una lista di città con le loro coordinate    
        List<String> cities = Arrays.asList(
        	    "Saint-Rhémy", "Cogne", "Courmayeur", "La Thuile", "Champoluc", "Buisson", "Point-Saint-Martin", "Grosseinay-Saint-Jean"
        );
        
        List<Coordinate> coordinates = Arrays.asList(
            new Coordinate(7.1579, 45.8485), // Saint-Rhémy
            new Coordinate(7.3918, 45.5866), // Cogne
            new Coordinate(6.9542, 45.8199), // Courmayeur
            new Coordinate(6.9618, 45.6825), // La Thuile
            new Coordinate(7.7325, 45.8571), // Champoluc
            new Coordinate(7.6083, 45.8602), // Buisson
            new Coordinate(7.8073, 45.5621), // Point-Saint-Martin
            new Coordinate(7.8248, 45.8179)  // Gressoney-Saint-Jean
        );
		
        for (int i = 0; i < cities.size(); i++) {
                Service service = new Service(i, "Restaurant", cities.get(i), coordinates.get(i));
                context.add(service);
    		    geography.move(service, new GeometryFactory().createPoint(coordinates.get(i)));
                allServices.add(service); // Aggiungi alla lista globale
        }
		
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		
		int timeSimulation = params.getInteger("timeSimulation");
        
        int coworking1Resident = params.getInteger("coworking1Resident");
        int coworking2Resident = params.getInteger("coworking2Resident");
        int coworking3Resident = params.getInteger("coworking3Resident");
        int coworking4Resident = params.getInteger("coworking4Resident");
        int coworking5Resident = params.getInteger("coworking5Resident");
        int coworking6Resident = params.getInteger("coworking6Resident");
        int coworking7Resident = params.getInteger("coworking7Resident");
        int coworking8Resident = params.getInteger("coworking8Resident");
        
        int coworkerV1percentage = params.getInteger("coworkerV1percentage");
        int coworkerV2percentage = params.getInteger("coworkerV2percentage");
        int coworkerV3percentage = params.getInteger("coworkerV3percentage");
        int coworkerV4percentage = params.getInteger("coworkerV4percentage");
        int coworkerV5percentage = params.getInteger("coworkerV5percentage");
        int coworkerV6percentage = params.getInteger("coworkerV6percentage");
        int coworkerV7percentage = params.getInteger("coworkerV7percentage");
        int coworkerV8percentage = params.getInteger("coworkerV8percentage");
        
        enableCoworking1 = params.getBoolean("coworking1");
        enableCoworking2 = params.getBoolean("coworking2");
        enableCoworking3 = params.getBoolean("coworking3");
        enableCoworking4 = params.getBoolean("coworking4");
        enableCoworking5 = params.getBoolean("coworking5");
        enableCoworking6 = params.getBoolean("coworking6");
        enableCoworking7 = params.getBoolean("coworking7");
        enableCoworking8 = params.getBoolean("coworking8");
        
        int coworking1Capacity = params.getInteger("coworking1Capacity");
        int coworking2Capacity = params.getInteger("coworking2Capacity");
        int coworking3Capacity = params.getInteger("coworking3Capacity");
        int coworking4Capacity = params.getInteger("coworking4Capacity");
        int coworking5Capacity = params.getInteger("coworking5Capacity");
        int coworking6Capacity = params.getInteger("coworking6Capacity");
        int coworking7Capacity = params.getInteger("coworking7Capacity");
        int coworking8Capacity = params.getInteger("coworking8Capacity");
 
        Map<String, List<Coordinate>> valleyPaths = createValleyPaths(enableCoworking1, enableCoworking2, enableCoworking3, enableCoworking4, enableCoworking5, enableCoworking6, enableCoworking7, enableCoworking8);
        List<String> cityNames = new ArrayList<>(valleyPaths.keySet());
        
        Map<String, CoworkingBuilding> activeCoworkings = new HashMap<>();

        if (enableCoworking1) {
                GeometryFactory geomFactory = new GeometryFactory();
                Coordinate coord = new Coordinate(7.1773, 45.8217); // Coworking
                CoworkingBuilding coworking1 = new CoworkingBuilding(1, "Saint-Rhémy", coworking1Capacity, coord);
                context.add(coworking1);
                //coworking.add(coworking1);
                
                geography.move(coworking1, geomFactory.createPoint(coord));
                activeCoworkings.put("Saint-Rhémy", coworking1); // Aggiungi alla mappa degli attivi
        }
        
        if(enableCoworking2) {
        	GeometryFactory geomFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(7.3421, 45.6155);  // Coworking
            CoworkingBuilding coworking2 = new CoworkingBuilding(2, "Cogne", coworking2Capacity, coord);
            context.add(coworking2);
            //coworking.add(coworking2);
            
            geography.move(coworking2, geomFactory.createPoint(coord));
            activeCoworkings.put("Cogne", coworking2); // Aggiungi alla mappa degli attivi
        }
        
        if(enableCoworking3) {
        	GeometryFactory geomFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(6.9655, 45.7922);  // Coworking
            CoworkingBuilding coworking3 = new CoworkingBuilding(3, "Courmayeur", coworking3Capacity, coord);
            context.add(coworking3);
            //coworking.add(coworking2);
            
            geography.move(coworking3, geomFactory.createPoint(coord));
            activeCoworkings.put("Courmayeur", coworking3); // Aggiungi alla mappa degli attivi
        }
        
        if(enableCoworking4) {
        	GeometryFactory geomFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(6.9433, 45.7131);  // Coworking
            CoworkingBuilding coworking4 = new CoworkingBuilding(4, "La Thuile", coworking4Capacity, coord);
            context.add(coworking4);
            //coworking.add(coworking2);
            
            geography.move(coworking4, geomFactory.createPoint(coord));
            activeCoworkings.put("La Thuile", coworking4); // Aggiungi alla mappa degli attivi
        }
        
        if(enableCoworking5) {
        	GeometryFactory geomFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(7.7325, 45.8385);  // Coworking
            CoworkingBuilding coworking5 = new CoworkingBuilding(5, "Champoluc", coworking5Capacity, coord);
            context.add(coworking5);
            //coworking.add(coworking2);
            
            geography.move(coworking5, geomFactory.createPoint(coord));
            activeCoworkings.put("Champoluc", coworking5); // Aggiungi alla mappa degli attivi
        }
        
        if(enableCoworking6) {
        	GeometryFactory geomFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(7.6054, 45.8378);  // Coworking
            CoworkingBuilding coworking6 = new CoworkingBuilding(6, "Buisson", coworking6Capacity, coord);
            context.add(coworking6);
            //coworking.add(coworking2);
            
            geography.move(coworking6, geomFactory.createPoint(coord));
            activeCoworkings.put("Buisson", coworking6); // Aggiungi alla mappa degli attivi
        }
        
        if(enableCoworking7) {
        	GeometryFactory geomFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(7.7970, 45.6005);  // Coworking
            CoworkingBuilding coworking7 = new CoworkingBuilding(7, "Point-Saint-Martin", coworking7Capacity, coord);
            context.add(coworking7);
            //coworking.add(coworking2);
            
            geography.move(coworking7, geomFactory.createPoint(coord));
            activeCoworkings.put("Point-Saint-Martin", coworking7); // Aggiungi alla mappa degli attivi
        }
        
        if(enableCoworking8) {
        	GeometryFactory geomFactory = new GeometryFactory();
            Coordinate coord = new Coordinate(7.8330, 45.7696);  // Coworking
            CoworkingBuilding coworking8 = new CoworkingBuilding(8, "Grosseinay-Saint-Jean", coworking8Capacity, coord);
            context.add(coworking8);
            //coworking.add(coworking2);
            
            geography.move(coworking8, geomFactory.createPoint(coord));
            activeCoworkings.put("Grosseinay-Saint-Jean", coworking8); // Aggiungi alla mappa degli attivi
        }
       

        List<CoworkingBuilding> activeCoworkingList = new ArrayList<>(activeCoworkings.values());

        
        List<Valle> allValli = new ArrayList<>();

        int[] residentsArray = {
            params.getInteger("coworking1Resident"),
            params.getInteger("coworking2Resident"),
            params.getInteger("coworking3Resident"),
            params.getInteger("coworking4Resident"),
            params.getInteger("coworking5Resident"),
            params.getInteger("coworking6Resident"),
            params.getInteger("coworking7Resident"),
            params.getInteger("coworking8Resident")
        };

        for (int i = 0; i < cities.size(); i++) {
            Valle valle = new Valle(cities.get(i), i + 1, residentsArray[i], 0);
            allValli.add(valle);
            context.add(valle);
        }
        
      
        for (int i = 0; i < 960; i++) {
            CoworkingBuilding assignedCoworking = null;
            Service allService = null;

            // Associa una valle in modo ciclico
            Valle valleAssegnata = allValli.get(i % allValli.size());
            // Associa una città iniziale in modo ciclico
            String cityName = cityNames.get(i % cityNames.size());

            // Ottieni il percorso della valle
            List<Coordinate> path = valleyPaths.get(cityName);

            // Filtra i coworking attivi per la città del coworker
            List<CoworkingBuilding> citySpecificCoworkings = activeCoworkingList.stream()
                .filter(coworking -> coworking.getName().equals(cityName))
                .collect(Collectors.toList());

            // Assegna un coworking compatibile se disponibile
            if (!citySpecificCoworkings.isEmpty()) {
                assignedCoworking = citySpecificCoworkings.get(i % citySpecificCoworkings.size());
            }

            // Filtra i servizi per la valle attuale del coworker
            List<Service> citySpecificServices = allServices.stream()
                .filter(service -> service.getCity().equals(cityName))
                .collect(Collectors.toList());

            // Associa un servizio compatibile alla valle del coworker, se disponibile
            if (!citySpecificServices.isEmpty()) {
                allService = citySpecificServices.get(i % citySpecificServices.size());
            }

            // Crea il coworker passando l'intera valle
            Coworker coworker = new Coworker(i, assignedCoworking, allService, valleAssegnata); // Passa l'intera valle
            context.add(coworker);

            // Aggiungi il coworker alla lista per controlli futuri
            coworkerList.add(coworker);

            // Imposta il percorso del coworker
            coworker.setPath(path);

            // Posiziona il coworker al primo punto del percorso
            Coordinate startCoord = path.get(0);
            Point startLocation = new GeometryFactory().createPoint(startCoord);
            geography.move(coworker, startLocation);
        }


        RunEnvironment.getInstance().endAt(timeSimulation); // Simulazione più lunga

		return context;

    }
    
	
}


