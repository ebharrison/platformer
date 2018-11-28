import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class GameObject extends ScreenObject{
    private int width, height;
    ArrayList<Collider> colliders;
    
    public GameObject(JPanel jp, int x, int y, int w, int h) {
        super(jp, x, y);
        colliders = new ArrayList<>();
        width = w;
        height = h;
    }

    public ArrayList<Collider> getColliders() {
        return colliders;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    } 
    
    public boolean hit(GameObject go) {
        for(Collider c: colliders) {
            if(!c.isTrigger() && c.hit(go)) return true;
        }
        return false;
    }
    
    public boolean hitTrigger(GameObject go) {
        for(Collider c: colliders) {
            if(c.isTrigger() && c.hitTrigger(go)) return true;
        }
        return false;
    }
}
