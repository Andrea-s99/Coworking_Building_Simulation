package coworking.agents;

import java.util.List;

import repast.simphony.engine.schedule.ScheduledMethod;

public class Valle {
    private String nome;
    private int id;
    private float residenti;
    private double gradoSoddisfazione;

    private int collaborations;
    
    // Costruttore
    public Valle(String nome, int id, int residenti, double gradoSoddisfazione) {
        this.nome = nome;
        this.id = id;
        this.residenti = residenti;
        this.gradoSoddisfazione = gradoSoddisfazione;
    }

    // Getter e Setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getResidenti() {
        return this.residenti;
    }

    public void setResidenti(float residenti) {
        this.residenti = residenti;
    }

    public double getGradoSoddisfazione() {
        return gradoSoddisfazione;
    }

    public void setGradoSoddisfazione(double gradoSoddisfazione) {
        this.gradoSoddisfazione = gradoSoddisfazione;
    }
    
    public int getCollaborations() {
    	return collaborations;
    }
    
    public void setCollaborations(int collaborations) {
    	this.collaborations = collaborations;
    }

    @ScheduledMethod(start = 1, interval = 100)
	public void step() {
		
    	setResidenti((int) (getResidenti() - 1)); // Lieve spopolamento
		
    	setCollaborations(getCollaborations() + 1); // Nasce una nuova collaborazione
	
    }    
    
    @ScheduledMethod(start = 1, interval = 300)
	public void step1() {
		
    	setCollaborations(getCollaborations() - 1); //Termina una collaborazione
	
    }   
}
