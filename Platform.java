import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
public class Platform extends GameObject
{
    private Color color;
    private PlatformType type;
    
    public Platform(JPanel jp, int x, int y, int width, int height) {
        super(jp, x, y, width, height);
        
        BoxCollider bc = new BoxCollider(this, 0, 0, width, height);
        bc.setVisible(false);
        this.getColliders().add(bc);
        
        this.type = PlatformType.DEFAULT;
        this.color = Color.BLACK;
    }
    
    public Platform(JPanel jp, int x, int y, int width, int height, PlatformType type) {
        super(jp, x, y, width, height);
        
        BoxCollider bc = new BoxCollider(this, 0, 0, width, height);
        bc.setVisible(false);
        this.getColliders().add(bc);
        
        this.type = type;
        if(type.equals(PlatformType.VINE)) color = Color.GREEN;
        else if(type.equals(PlatformType.BOUNCY)) color = Color.BLUE;
        else if(type.equals(PlatformType.PORTAL)) color = Color.MAGENTA;
        else color = Color.BLACK;
    }
    
    public void draw(Graphics g) {
        if(this.isVisible()) {
            g.setColor(color);
            g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            
            for(Collider c: getColliders()) {
                c.draw(g);
            }
        }
    }
    
    public PlatformType getType(){
        return this.type;
    }
}
