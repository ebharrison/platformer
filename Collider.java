import javax.swing.JPanel;
public abstract class Collider extends ScreenObject
{
    private GameObject gameObject;
    
    private boolean isTrigger;
    
    public Collider(GameObject go, int x, int y) {
        super(go.getPanel(), x, y);
        this.gameObject = go;
        this.isTrigger = false;
    }
    
    public void setTrigger(boolean trigger) {
        this.isTrigger = trigger;
    }
    
    public boolean isTrigger() {
        return this.isTrigger;
    }
    
    @Override
    public int getX() {
        return this.gameObject.getX() + this.getLocalX();
    }
    
    @Override
    public int getY() {
        return this.gameObject.getY() + this.getLocalY();
    }
    
    public int getLocalX() {
        return super.getX();
    }
    
    public int getLocalY() {
        return super.getY();
    }
    
    public GameObject getGameObject() {
        return this.gameObject;
    }
    
    public abstract boolean hit(GameObject go);
    
    public abstract boolean hitTrigger(GameObject go);
    
    public abstract boolean hit(Collider c);
    
    public abstract boolean hit(BoxCollider bc);
    
    public abstract boolean hit(CircleCollider cc);
}
