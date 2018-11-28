import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Token extends GameObject
{
    private final Color color;
    
    private int radius;
    
    public Token(JPanel jp, int x, int y) {
        super(jp, x, y, 25, 25);

        this.radius = 25;
        
        this.color = Color.YELLOW;
        
        CircleCollider cc = new CircleCollider(this, 0, 0, this.radius);
        cc.setVisible(false);
        cc.setTrigger(true);
        this.getColliders().add(cc);
    }
    
    public int getRadius(){
        return this.radius;
    }
    
    public void draw(Graphics g){
        if(this.isVisible()) {
            g.setColor(color);
            g.fillOval(this.getX() - this.getRadius(), this.getY() - this.getRadius(), this.getRadius() * 2, this.getRadius() * 2);
            
            for(Collider c: this.getColliders()){
                if(c.isVisible()) c.draw(g);
            }
        }
        
    }
    
}
