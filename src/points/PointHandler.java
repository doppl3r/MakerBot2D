package points;
import java.awt.*;
//import java.awt.Point;
import java.util.LinkedList;

public class PointHandler {

    private LinkedList<MyPoint> points;
    private double speed;
    private int width;
    private int radius;
    private int maxHeight;

    public PointHandler(){
        points = new LinkedList<MyPoint>();
        width = 3;
        radius = 5;
        maxHeight = 340;
        speed = 2;
    }
    public void draw(Graphics2D g){
        GradientPaint gp = new GradientPaint(
            0, 0, new Color(150,0,0),
            0, 360, new Color(255,0,0));
        g.setPaint(gp);
        //g.setColor(new Color(255,0,0));
        g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, null, 0f));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < points.size(); i++){
            //draw lines
            if (points.size() > 0){
                if (i < points.size()-1){
                    g.drawLine((int)points.get(i).getX(),(int)points.get(i).getY(),
                        (int)points.get(i+1).getX(),(int)points.get(i+1).getY());
                }
            }
        }
    }
    public void update(double mod){
        for (int i = 0; i < points.size(); i++){
            if (!points.get(i).isFrozen()){
                points.get(i).setY(points.get(i).getY()+mod*speed);
                //freeze point if it reaches the bottom
                if (points.get(i).getY() > maxHeight){
                    points.get(i).freeze();
                    points.get(i).setY(maxHeight);
                }
                //check other points
                for (int j = 0; j < points.size(); j++){
                    if (j != i){ //don't check current point
                        //if (isCollision(points.get(i),radius,points.get(j),radius) && points.get(j).isFrozen()){
                        //    points.get(i).freeze();
                        //}
                        if (j < points.size()-1){
                            if (isCollision(
                                    points.get(j).getX(),points.get(j).getY(),
                                    points.get(j+1).getX(), points.get(j+1).getY(),
                                    points.get(i).getX(), points.get(i).getY(), radius) &&
                                    points.get(j).isFrozen() && points.get(j+1).isFrozen()){
                                points.get(i).freeze();
                            }
                        }
                    }
                }
            }
        }
    }
    public void add(int x, int y){
        if (points.size() > 0){
            //points.add(new MyPoint(x,y));
            for (int i = 0; i < points.size(); i++){
                if (!isCollision(x,y,width,points.get(i).getX(),points.get(i).getY(),width)){
                    points.add(new MyPoint(x+((int)(Math.random()*7)-4),y));
                    break;
                }
            }
        }
        else points.add(new MyPoint(x,y));
    }
    public void remove(int x, int y){
        //check other points
        for (int j = 0; j < points.size(); j++){
            if (j < points.size()-1){
                if (isCollision(
                        points.get(j).getX(),points.get(j).getY(),
                        points.get(j+1).getX(), points.get(j+1).getY(),
                        x, y, radius)){
                    points.remove(j);
                    break;
                }
            }
        }
    }
    public void removeAll(){
        int size = points.size();
        for (int i = 0; i < size; i++){
            points.remove(0);
        }
    }
    public boolean isCollision(MyPoint p1, int r1, MyPoint p2, int r2){
        double a = r1 + r2;
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return a * a > (dx * dx + dy * dy);
    }
    public boolean isCollision(double x1, double y1, int r1, double x2, double y2, int r2){
        double a = r1 + r2;
        double dx = x1 - x2;
        double dy = y1 - y2;
        return a * a > (dx * dx + dy * dy);
    }
    public boolean isCollision(double x1, double y1, double x2, double y2, double x3, double y3, double r)
    {
        boolean collision = false;
        double vx = x2 - x1;
        double vy = y2 - y1;
        double xdiff = x1 - x3;
        double ydiff = y1 - y3;
        double a = Math.pow(vx, 2) + Math.pow(vy, 2);
        double b = 2 * ((vx * xdiff) + (vy * ydiff));
        double c = Math.pow(xdiff, 2) + Math.pow(ydiff, 2) - Math.pow(r, 2);
        double quad = Math.pow(b, 2) - (4 * a * c);
        if (quad >= 0)
        {
            // An infinite collision is happening, but let's not stop here
            double quadsqrt = Math.sqrt(quad);
            for (int i = -1; i <= 1; i += 2)
            {
                // Returns the two coordinates of the intersection points
                double t = (i * -b + quadsqrt) / (2 * a);
                Point intersection = new Point();
                intersection.setLocation(x1 + (i * vx * t), y1 + (i * vy * t));

                // If one of them is in the boundaries of the segment, it collides
                if (intersection.getX() >= Math.min(x1, x2) &&
                        intersection.getX() <= Math.max(x1, x2) &&
                        intersection.getY() >= Math.min(y1, y2) &&
                        intersection.getY() <= Math.max(y1, y2)){
                    collision = true;
                }
            }
        }
        return collision;
    }
}
