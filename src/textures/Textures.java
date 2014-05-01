package textures;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Textures {
    public Image icon;
    public Image makerbotlogo;
    public Image camera;
    public Image intro;

    public Textures(){
		addResources();
	}
	public void addResources(){
        icon = new ImageIcon(this.getClass().getResource("/gui/icon.png")).getImage();
        makerbotlogo = new ImageIcon(this.getClass().getResource("/gui/makerbotlogo.png")).getImage();
        camera = new ImageIcon(this.getClass().getResource("/gui/camera.png")).getImage();
        intro = new ImageIcon(this.getClass().getResource("/gui/intro.png")).getImage();
    }
}
