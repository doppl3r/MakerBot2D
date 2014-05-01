import points.PointHandler;
import buttons.Button;

import java.awt.*;
import java.io.IOException;

public class Game {

    private PointHandler points;
    private Button camera;
    private Button link;
    private boolean draw;
    private double ticker;
    private int x;
    private int y;

    public Game(){
        points = new PointHandler();
        camera = new Button(Window.tt.camera, 16,72,1,2,false);
        link = new Button(Window.tt.makerbotlogo, 16,16,1,2,false);
    }
    public void draw(Graphics2D g){
        link.draw(g);
        camera.draw(g);
        points.draw(g);
    }
    public void update(double mod){
        points.update(mod);
        if (draw || ticker > 0){
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
    public void down(int x, int y){
        if (camera.down(x,y)){}
        else if (link.down(x,y)){}
        else draw = true;
    }
    public void up(int x, int y){
        if (camera.up(x,y)) Window.panel.screenshot();
        else if (link.up(x,y)) {
            try { java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://doppl3r.com/"));
            } catch (IOException e) { e.printStackTrace(); }
        }
        draw = false;
    }
    public void move(int x, int y){
        camera.move(x,y);
        link.move(x,y);
        this.x=x;
        this.y=y;
    }
    public void hover(int x, int y){
        camera.hover(x,y);
        link.hover(x,y);
        this.x=x;
        this.y=y;
    }
}
