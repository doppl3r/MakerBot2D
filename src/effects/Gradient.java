package effects;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Gradient{
	private int width, height;
	private double transparency;
	private String direction;
	private BufferedImage image;
	private Color color;
	
	public Gradient(){}
	public void render(Graphics2D g) {
        g.setColor(color);
        
        if (direction.equals("right")){
        	g.translate(0,0);
        	g.rotate(0 * Math.PI / 180.0);
        }
        else if (direction.equals("left")){
        	g.translate(width, height);
        	g.rotate(180 * Math.PI / 180.0);
        }
		else if (direction.equals("up")){
			g.translate(0, height);
			g.rotate(270 * Math.PI / 180.0);
			readjust();
		}
		else if (direction.equals("down")){
        	g.translate(width,0);
        	g.rotate(90 * Math.PI / 180.0);
        	readjust();
		}
        for (double i = 1; i <= width; i++) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float)((i/width)*transparency)));
            g.fillRect((int)i, 0, 1, height);
        }
    }
	public BufferedImage getGradient(){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); 
		Graphics2D g = bi.createGraphics();
		render(g);
		g.dispose();
		return bi;		
	}
	public void setGradient(int width, int height, double transparency, String direction, Color color){
		this.width=width;
		this.height=height;
		this.transparency=transparency;
		this.setDirection(direction);
		this.color=color;
		setImage(getGradient());
	}
	public void readjust(){
		int oldWidth = width;
    	width = height;
    	height = oldWidth;
	}
	public BufferedImage getImage() {return image;}
	public void setImage(BufferedImage image) {this.image = image;}
	public String getDirection() {return direction;}
	public void setDirection(String direction) {this.direction = direction;}
}