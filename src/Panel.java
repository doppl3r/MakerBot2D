import audio.AudioHandler;
import textures.Resizer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements KeyListener,
        MouseListener, MouseMotionListener, Runnable {
	private static final long serialVersionUID = 1L;
    private int panelState; //displays menus individually
    private double now;
    private double then;
    private double mod;
    private double delta;
    private double pixelsPerSecond;
    private double mLastTime;
    private double opacity;
    private int frameSamplesCollected = 0;
    private int frameSampleTime = 0;
    private int fps = 0;
    private Font font;
    BufferedImage buffered;
    BufferedImage ss;
    public Game game;
    public GUI gui;
	private Timer t;
	
	public Panel(){
        panelState = 0; //start at editor
        pixelsPerSecond = 100; //very important for computer speed vs. graphic speed
        font = new Font ("Arial", Font.BOLD, 18);
        game = new Game();
        gui = new GUI();

        //start music
        //AudioHandler.THEME.clip.loop(-1);

		//set listeners and thread
		addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
		setFocusable(true);
		run();
	}
	public void run(){
		t = new Timer(1000/60, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
                updateMod(true);
                //update components
				update(mod);
				repaint();
                //final update of mod value
                updateMod(false);
			}
		});
		t.start();
	}
	public void paintComponent(Graphics g1){ //alt for paint
        //convert to Graphics2D
        Graphics2D g = (Graphics2D)g1;
        g.setFont(font);
        super.paintComponent(g);
		setBackground(Color.BLACK);

        double ratio = ((double)Window.getPanelWidth()/(double)Window.getPanelHeight());

        int x1 = (int)((Window.getFrameWidth()/2)-((Window.getFrameHeight()*ratio)/2));
        int y1 = 0;
        int x2 = (int)(Window.getFrameHeight()*ratio);
        int y2 = Window.getFrameHeight();

        //buffered = Resizer.BICUBIC.resize(buffered, Window.getPanelWidth(), Window.getPanelHeight());
        if (Window.isStretched()) g.drawImage(buffered, 0,0,Window.getFrameWidth(),Window.getFrameHeight(),this);
        else g.drawImage(buffered,x1,y1,x2,y2,this); //exact ratio

        Window.updateSize(); //Window.jf
        doubleBuffer();
	}
    public void draw(Graphics2D g, boolean screenshot){
        g.setColor(Color.WHITE); //the color of the actual 320x240 game
        g.fillRect(0,0,Window.getPanelWidth(),Window.getPanelHeight());

        //draw components
        switch(panelState){
            case(0): g.drawImage(Window.tt.intro,0,0,null); break;
            case(1): game.draw(g); break;
        }
        gui.draw(g);
        g.setColor(new Color(0,148,255));

        //draw screenshot
        if (!screenshot && ss != null && opacity > 0){
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacity));
            g.drawImage(ss,5,5,null);
        }

        //g.drawString("fps: "+fps,2,10);
        updateFPS(); //updates fps after drawn completely
    }
    public void doubleBuffer(){
        buffered = new BufferedImage(Window.getPanelWidth(),Window.getPanelHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffered.createGraphics();
        draw(g, false);
    }
    public void screenshot(){
        opacity = 1f;
        ss = buffered;
        ss = new BufferedImage(Window.getPanelWidth(),Window.getPanelHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ss.createGraphics();
        draw(g, true);

        try{
            String fileName = "creation.png";
            int fileCount = 0;
            File file = new File(fileName);
            while (file.exists()){
                fileCount++;
                file.renameTo(new File("creation"+fileCount+".png"));
            }
            ImageIO.write(ss, "png", file);
        }
        catch (IOException e){}
    }
	public void update(double mod){
		//update the components
        switch(panelState){
            case(0): break;
            case(1):
                if (opacity - (mod*.01) > 0) opacity -= (mod*.01);
                else opacity = 0;
                game.update(mod);
            break;
        }
        gui.update();
	}
	//key bindings
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        switch(panelState){
            case(0): break;
            case(1): //editor keybindings
                if (key == KeyEvent.VK_ESCAPE){ panelState=0; }
           break;
        }
	}
	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch(panelState){
            case(0): break;
            case(1): //editor keybindings
            break;
        }
    }
	public void keyTyped(KeyEvent arg0) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { //down
        panelState = 1;
        int x = (Window.getPanelWidth() *e.getX())/Window.getFrameWidth();
        int y = (Window.getPanelHeight()*e.getY())/Window.getFrameHeight();
        game.down(x,y,e.getButton()==3);
    }
    public void mouseDragged(MouseEvent e) { //move
        int x = (Window.getPanelWidth() *e.getX())/Window.getFrameWidth();
        int y = (Window.getPanelHeight()*e.getY())/Window.getFrameHeight();
        game.move(x,y,e.getButton()==3);
    }
    public void mouseReleased(MouseEvent e) { //up
        int x = (Window.getPanelWidth() *e.getX())/Window.getFrameWidth();
        int y = (Window.getPanelHeight()*e.getY())/Window.getFrameHeight();
        game.up(x,y,e.getButton()==3);
    }
    public void mouseMoved(MouseEvent e) { //hover
        //update cursor
        int x = (Window.getPanelWidth() *e.getX())/Window.getFrameWidth();
        int y = (Window.getPanelHeight()*e.getY())/Window.getFrameHeight();
        game.hover(x,y,e.getButton()==3);
    }
    //update FPS
    public void updateFPS() {
        long tempNow = System.currentTimeMillis();
        if (mLastTime != 0) {
            int time = (int) (tempNow - mLastTime);
            frameSampleTime += time;
            frameSamplesCollected++;
            if (frameSamplesCollected == 10) {
                if (frameSampleTime != 0)
                    fps = (10000 / frameSampleTime);
                frameSampleTime = 0;
                frameSamplesCollected = 0;
            }
        }
        mLastTime = tempNow;
    }
    public void updateMod(boolean start){
        if (start){
            //first update module value
            now = System.currentTimeMillis();
            delta = (now - then)/1000;
            mod = delta*pixelsPerSecond;
        } else then = now;
    }
    public void setPanelState(int i){ panelState = i; }
}
