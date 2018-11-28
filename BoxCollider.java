import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
public class BoxCollider extends Collider
{
    private int width, height;
    private static Color color = Color.LIGHT_GRAY;
    public BoxCollider(GameObject go, int x, int y, int width, int height) {
        super(go, x, y);
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public void draw(Graphics g) {
        if(isVisible()) {
            g.setColor(color);
            g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
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
            // Uses an XOR statement - Will only work if either but not both are triggers
            if(!(this.isTrigger() == c.isTrigger()) && this.hit(c)) return true;
        }
        return false;
    }
    
    public boolean hit(Collider c) {
        if(c instanceof BoxCollider) {
            return this.hit((BoxCollider) c);
        } else if(c instanceof CircleCollider) {
            return this.hit((CircleCollider) c);
        }
        return false;
    }
    
    public boolean hit(BoxCollider bc) {
        boolean xCollided = true;
        boolean yCollided = true;
        
        //a is "this" box collider
        int aLx = this.getX();
        int aRx = aLx + this.getWidth();
        int aTy = this.getY();
        int aBy = aTy + this.getHeight();
        
        //b is the other collider
        int bLx = bc.getX();
        int bRx = bLx + bc.getWidth();
        int bTy = bc.getY();
        int bBy = bTy + bc.getHeight();

        if((aRx < bLx) || (aLx > bRx)) xCollided = false;
        
        if((aBy < bTy) || (aTy > bBy)) yCollided = false;
        
        return (xCollided && yCollided);
    }
    
    public boolean hit(CircleCollider cc) {
        int ccx = cc.getX();
        int ccy = cc.getY();
        int ccr = cc.getRadius();
        int ccLx = ccx - ccr;
        int ccRx = ccx + ccr;
        int ccTy = ccy + ccr;
        int ccBy = ccy - ccr;
        
        int bcLx = this.getX();
        int bcRx = bcLx + this.getWidth();
        int bcTy = this.getY();
        int bcBy = bcTy + this.getHeight();

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
}
