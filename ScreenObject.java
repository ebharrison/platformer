import javax.swing.JPanel;
import java.awt.Graphics;

public abstract class ScreenObject
{
    private int x;
    private int y;
    private boolean visible = true;
    private JPanel panel;
    
    public ScreenObject(JPanel jp, int x, int y) {
        this.panel = jp;
        this.x = x;
        this.y = y;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }
    
    public JPanel getPanel() {
        return this.panel;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void draw(Graphics g);
    
}
