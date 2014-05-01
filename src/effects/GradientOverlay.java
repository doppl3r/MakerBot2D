package effects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;

public class GradientOverlay extends JPanel {
	private static final long serialVersionUID = 1L;
	private int width, height, size;
	private double transparency;
	private Color color;
	private Gradient gradient;
	private Image shader;
	private LinkedList<Gradient> list;
	
	public GradientOverlay(){
		width = height = 512;
		size = 150;
		transparency = 0.2;
		color = Color.black;
		buildShader();
		renderShader();
	}
	public GradientOverlay(int width, int height, int size, double transparency, Color color){
		this.width=width;
		this.height=height;
		this.size=size;
		this.transparency=transparency;
		this.color=color;
		buildShader();
		renderShader();
	}
	public void buildShader(){
		list = new LinkedList<Gradient>();
		list.add(new Gradient());
		list.get(list.size()-1).setGradient(width,size,transparency,"up",color);
		list.add(new Gradient());
		list.get(list.size()-1).setGradient(width,size,transparency,"down",color);
		list.add(new Gradient());
		list.get(list.size()-1).setGradient(size,height,transparency,"left",color);
		list.add(new Gradient());
		list.get(list.size()-1).setGradient(size,height,transparency,"right",color);
	}
	public void renderShader(){ //renders the background map
		setShader(new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB)); 
		Graphics g = getShader().getGraphics();
		g.drawImage(list.get(0).getImage(), 0, 0, this);
		g.drawImage(list.get(1).getImage(), 0, height-size, this);
		g.drawImage(list.get(2).getImage(), 0, 0, this);
		g.drawImage(list.get(3).getImage(), width-size, 0, this);
		g.dispose();
	}
	public Gradient getGradient() {return gradient;}
	public void setGradient(Gradient gradient) {this.gradient = gradient;}
	public Image getShader() {return shader;}
	public void setShader(Image shader) {this.shader = shader;}
}
