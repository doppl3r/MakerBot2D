package physics;


public class JumpAccelerator {
	private double x, y, length, height, speed, slope, boostX, boostY;
	public JumpAccelerator(double length, double height, double speed){
		this.length = length;
		this.height = height;
		this.speed = speed;
		slope = (height/(Math.pow(((double)length)/2,2)));
	}
	public void accelerate(){
		//if (x < length + fallDistance) 
		x+=(speed);
		y = (-slope*(Math.pow((x)-length/2,2))+(height));
	}
	public void reset(){
		x = 0;
		y = 0;
		if (boostX > 0){
			length-=boostX;
			boostX = 0;
		}
		if (boostY > 0){
			height-=boostY;
			boostY = 0;
		}
		slope = (height/(Math.pow(((double)length)/2,2)));
	}
	public void adjustSlope(double boostX, double boostY){
		this.boostX=boostX;
		this.boostY=boostY;
		length+=boostX;
		height+=boostY;
		slope = ((height)/(Math.pow(((double)(length))/2,2)));
	}
	public void set(double digit){
		x = digit;
	}
    public void land(){ x = length/2; } //start at top of fall;
    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getJumpAmount(){
        return (-slope*(Math.pow((x+speed)-length/2,2))+(height))-
               (-slope*(Math.pow((x)  -length/2,2))+(height));
    }
    public boolean isGrounded(){ return x == length/2; }
    public boolean isAssending(){ return x < length/2; }
}

