package physics;

public class SprintAccelerator {
	double x, y, minX, maxX, speed, acceleration, boost;
	public SprintAccelerator(double maxX, double speed, double acceleration){
		this.maxX = maxX;
		this.speed = speed;
		this.acceleration = acceleration;
	}
	public void accelerate(){
		if (x < maxX+boost){ x+=speed; }
		else x = maxX+boost;
		y = Math.pow(((acceleration)*x)+speed, 2) - Math.pow((acceleration)*x, 2);
	}
	public void decelerate(){
		if (x > minX) x-=(speed/2); //decelerate slower}
		else{ x = 0; boost = 0; }
		y = Math.pow(((acceleration)*x)+speed, 2) - Math.pow((acceleration)*x, 2);
	}
	public void stop(){
		x = y = 0;
	}
    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getSprintAmount(){
        return Math.pow(((acceleration)*(x+speed))+speed, 2) - Math.pow((acceleration)*x, 2)-
               Math.pow(((acceleration)*x)+speed, 2) - Math.pow((acceleration)*x, 2);
    }
}
