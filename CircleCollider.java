import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
public class CircleCollider extends Collider
{
    private int radius;
    private static Color color = Color.LIGHT_GRAY;
    public CircleCollider(GameObject go, int x, int y, int radius) {
        super(go, x, y);
        this.radius = radius;
    }
    
    public int getRadius() {
        return this.radius;
    }
    
    @Override
    public void draw(Graphics g) {
        if(this.isVisible()) {
            g.setColor(color);
            g.fillOval(this.getX() - this.getRadius(), this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2);
        }
    }
    
    public boolean hit(GameObject go) {
        for(Collider c: go.getColliders()) {
            // Will only work if both colliders are not triggers
            if((!this.isTrigger() && !c.isTrigger()) && this.hit(c)) return true;
        }
        return false;
    }
    
    public boolean hitTrigger(GameObject go) {
        for(Collider c: go.getColliders()) {
            // Uses an XOR statement - will only work if either but not both are triggers
            if(!(this.isTrigger() == c.isTrigger()) && this.hit(c)) return true;
        }
        return false;
    }
    
    public boolean hit(Collider c) {
        if(c instanceof BoxCollider) {
            return hit((BoxCollider) c);
        } else if(c instanceof CircleCollider) {
            return hit((CircleCollider) c);
        }
        return false;
    }
    
    public boolean hit(BoxCollider bc) {
        int ccx = this.getX();
        int ccy = this.getY();
        int ccr = this.getRadius();
        int ccLx = ccx - ccr;
        int ccRx = ccx + ccr;
        int ccTy = ccy + ccr;
        int ccBy = ccy - ccr;
        
        int bcLx = bc.getX();
        int bcRx = bcLx + bc.getWidth();
        int bcTy = bc.getY();
        int bcBy = bcTy + bc.getHeight();
        
        // Distance used to compare between colliders
        double dist;
        
        // Top Row
        if(ccy < bcTy){
            if(ccRx < bcLx) dist = Math.hypot((double)(ccRx - bcLx), (double)(ccBy - bcTy)); // Top left
            else if(bcRx < ccRx) dist = Math.hypot((double)(ccx - bcRx), (double)(ccy - bcTy)); // Top right
            else dist = bcTy - ccy; // Top middle
        }
        // Bottom Row
        else if(bcBy < ccy){
            if(ccx < bcLx) dist = Math.hypot((double)(bcLx-ccx), (double)(bcBy - ccy)); // Bottom left
            else if(bcRx < ccx) dist = Math.hypot((double)(ccx - bcRx), (double)(ccy - bcBy)); // Bottom right
            else dist = ccy - bcBy; // Bottom middle
        }
        // Middle Row
        else{
            if(ccx < bcLx) dist = bcLx - ccx; // Middle left
            else if(bcRx < ccx) dist = ccx - bcRx; // Middle right
            else return true; // Middle middle
        }

        if(dist < ccr) return true;
        else return false;
    }
    
    public boolean hit(CircleCollider cc) {
        // a is "this" circle collider
        int ax = this.getX();
        int ay = this.getY();
        int ar = this.getRadius();
        
        int bx = cc.getX();
        int by = cc.getY();
        int br = cc.getRadius();
        
        // These are just here for optimization and readability
        int dx = ax - bx;
        int dy = ay - by;
        
        return((ar + br) > Math.sqrt((dx * dx) + (dy * dy)));
    }
}
