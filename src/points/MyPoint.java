package points;

public class MyPoint {
    private double x;
    private double y;
    private boolean freeze;

    public MyPoint(int x, int y){ setXY(x,y); }
    public void freeze(){ freeze=true; }
    public boolean isFrozen(){ return freeze; }
    public void setXY(double x, double y){ this.x=x; this.y=y; }
    public void setX(double x){ this.x=x; }
    public void setY(double y){ this.y=y; }
    public double getX(){ return x; }
    public double getY(){ return y; }
}
