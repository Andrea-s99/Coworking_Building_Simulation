package coworking.agents;

public class Collaboration {

	private Coworker coworker1;
    private Coworker coworker2;
    private String type; // Tipologia di collaborazione

    public Collaboration(Coworker coworker1, Coworker coworker2, String type) {
        this.coworker1 = coworker1;
        this.coworker2 = coworker2;
        this.type = type;
    }

    public Coworker getCoworker1() {
        return coworker1;
    }

    public Coworker getCoworker2() {
        return coworker2;
    }

    public String getType() {
        return type;
    }

    
    @Override
    public String toString() {
        return String.valueOf(coworker2.getId());
    }

}
