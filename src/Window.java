import java.awt.*;
import javax.swing.*;


import textures.Textures;

/**
 */
public class Window {
    static JFrame jf;
    static Textures tt;
    static Panel panel;
    static int panelWidth; //stays the same
    static int panelHeight; //stays the same
    static int frameWidth;
    static int frameHeight;
    static int frameExtraWidth;
    static int frameExtraHeight;
    static String title;
    static String version;
    static boolean fullScreen;
    static boolean stretched;
    static Toolkit tk = Toolkit.getDefaultToolkit();

    public Window(){
        //change ui for jFileChooser + other UI settings
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        catch (InstantiationException e) { e.printStackTrace(); }
        catch (IllegalAccessException e) { e.printStackTrace(); }
        catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
        //construct frame and panel
        tt = new Textures();
        jf = new JFrame();
        panelWidth = 480;
        panelHeight = 360;
        //stretched = true;
        panel = new Panel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        title = "MakerBot 2D - By Jacob DeBenedetto";
        version = "v0.1";
        //build window
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setIconImage(tt.icon);
        jf.setTitle(title+" ["+version+"]");
        jf.add(panel);
        jf.setResizable(true);
        jf.pack();
        jf.setMinimumSize(new Dimension(jf.getWidth(),jf.getHeight()));
        jf.setLocationRelativeTo(null);
        jf.requestFocusInWindow();
        jf.setVisible(true);
        //update extras
        frameExtraWidth = jf.getInsets().left + jf.getInsets().right;
        frameExtraHeight = jf.getInsets().top + jf.getInsets().bottom;
        //start fullscreen
        //toggleFullScreen();
        toggleStretched();
    }
    public static int getPanelWidth() { return panelWidth; }
    public static int getPanelHeight(){ return panelHeight;}
    public static int getFrameWidth(){ return frameWidth-frameExtraWidth; }
    public static int getFrameHeight(){ return frameHeight-frameExtraHeight; }
    public static boolean isStretched(){ return stretched; }
    public static void toggleStretched(){ stretched = !stretched; }
    public static void toggleFullScreen(){
        if (fullScreen){ //set windowed
            jf.dispose();
            jf.setUndecorated(false);
            jf.setVisible(true);
            frameWidth  = panelWidth;
            frameHeight = panelHeight;
            fullScreen = false;
            jf.setPreferredSize(new Dimension(frameWidth, frameHeight));
            jf.pack();
            jf.setLocationRelativeTo(null);
        }
        else{
            jf.dispose();
            jf.setUndecorated(true);
            jf.setVisible(true);
            frameWidth = (int)tk.getScreenSize().getWidth();
            frameHeight = (int)tk.getScreenSize().getHeight();
            fullScreen = true;
            jf.setPreferredSize(new Dimension(frameWidth+frameExtraWidth, frameHeight+frameExtraHeight));
            jf.pack();
            jf.setLocation(new Point(0, 0));
        }
    }
    public static void updateSize(){
        frameWidth = jf.getWidth();
        frameHeight = jf.getHeight();
    }
}
