package dlxsimulationgui;

public class Positions {
    private final double x;
    private final double y;
    
    Positions(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public double getX() { return this.x; } 
    public double getY(){ return this.y; }
}