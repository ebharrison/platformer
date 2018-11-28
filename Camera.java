import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Camera extends ScreenObject{
    
    public Camera(JPanel jp, int x, int y) {
        super(jp, x, y);
    }
    
    public void follow(PlayerCharacter pc) {
        // setPosition( pc.getX() - pc.getPanel().getWidth()/2 + pc.getWidth()/2, pc.getY() - pc.getPanel().getHeight()/2 + pc.getWidth()/2 );
        follow(pc, 0, 0);
    }
    
    public void follow(PlayerCharacter pc, int offsetX, int offsetY) {
        setPosition( pc.getX() - pc.getPanel().getWidth()/2 + pc.getWidth()/2 - offsetX, pc.getY() - pc.getPanel().getHeight()/2 + pc.getWidth()/2 - offsetY );
    }
    
    public void draw(Graphics g) {
        if(isVisible()) {
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            g.drawRect(getX(), getY(), getPanel().getWidth(), getPanel().getHeight());
            ((Graphics2D) g).setStroke(new BasicStroke(2));
g.drawRect(getX() + 6, getY() + 6, getPanel().getWidth() - 12, getPanel().getHeight() - 12);
    }
    }
}
