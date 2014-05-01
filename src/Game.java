import points.PointHandler;
import buttons.Button;

import java.awt.*;
import java.io.IOException;

public class Game {

    private PointHandler points;
    private Button camera;
    private Button link;
    private Button eraseButton;
    private boolean draw;
    private boolean erase;
    private double ticker;
    private int x;
    private int y;

    public Game(){
        points = new PointHandler();
        camera = new Button(Window.tt.camera, 16,72,1,2,false);
        link = new Button(Window.tt.makerbotlogo, 16,16,1,2,false);
        eraseButton = new Button(Window.tt.erase, 16,128,1,2,false);
    }
    public void draw(Graphics2D g){
        link.draw(g);
        camera.draw(g);
        points.draw(g);
        eraseButton.draw(g);
    }
    public void update(double mod){
        points.update(mod);
        if (erase){
            points.remove(x,y);
        }
        else if (draw || ticker > 0){
            if (ticker > 0) ticker-= mod;
            else {
                ticker = 2;
                points.add(x,y);
            }
        }
    }

    //key pressed
    public void keyUpPressed(){ }
    public void keyDownPressed(){  }
    public void keyLeftPressed(){  }
    public void keyRightPressed(){  }
    public void keySpacePressed(){  }

    //key released
    public void keyUpReleased(){  }
    public void keyDownReleased(){  }
    public void keyLeftReleased(){  }
    public void keyRightReleased(){  }
    public void keySpaceReleased(){  }
    public void releaseR(){  }

    //mouse input
    public void down(int x, int y, boolean rightClick){
        if (camera.down(x,y)){}
        else if (link.down(x,y)){}
        else if (eraseButton.down(x,y)){}
        else{
            if (rightClick) erase=true;
            else draw = true;
        }
    }
    public void up(int x, int y, boolean rightClick){
        if (camera.up(x,y)) Window.panel.screenshot();
        else if (link.up(x,y)) {
            try { java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://doppl3r.com/"));
            } catch (IOException e) { e.printStackTrace(); }
        }
        else if (eraseButton.up(x,y)) points.removeAll();
        draw = false;
        erase = false;
    }
    public void move(int x, int y, boolean rightClick){
        camera.move(x,y);
        link.move(x,y);
        eraseButton.move(x,y);
        this.x=x;
        this.y=y;
    }
    public void hover(int x, int y, boolean rightClick){
        camera.hover(x,y);
        link.hover(x,y);
        eraseButton.move(x,y);
        this.x=x;
        this.y=y;
    }
}
