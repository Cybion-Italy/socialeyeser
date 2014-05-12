package it.cybion.socialeyeser.alerting.responses;

public class Alert {
    
    private double score = 0.0;
    
    public Alert() {
    
    }
    
    public Alert(double score) {
    
        super();
        this.score = score;
    }
    
    public double getScore() {
    
        return score;
    }
    
    public void setScore(double score) {
    
        this.score = score;
    }
}
