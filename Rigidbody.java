import javax.swing.JPanel;
import java.util.ArrayList;
public abstract class Rigidbody extends GameObject
{
    private int vspeed;
    private int hspeed;
    private static final int GRAVITY = -1;
    public static final int MAXVSPEED = -20;
    public static final int MAXHSPEED = 5;
    private static final int JUMP_FORCE = 10;
    
    private int direction = 1; // Value of 1 = facing Right, Value of -1 = facing Left
    
    private boolean inAir = true;
    
    public Rigidbody(JPanel jp, int x, int y, int w, int h) {
        super(jp, x, y, w, h);
        
        this.vspeed = 0;
        this.hspeed = 0;
    }
    
    public boolean inAir() {
        return this.inAir;
    }
    
    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }
    
    public void setVSpeed(int vspeed) {
        if(this.vspeed < Rigidbody.MAXVSPEED) this.vspeed = Rigidbody.MAXVSPEED;
        else this.vspeed = vspeed;
    }
    
    public int getVSpeed() {
        return this.vspeed;
    }
    
    public void vUpdate(ArrayList<GameObject> gameObjects) {
        int movement = 0; //magnitude of movement progress
        int unit = 1; //direction of movement. - down, + up
        //int index=0;
        if(this.getVSpeed() > 0) unit = -1;
        
        if(this.getVSpeed() > Rigidbody.GRAVITY) setInAir(true);
        
        while(movement < Math.abs(this.getVSpeed()) ) {
            movement++;
            this.setY(this.getY() + unit);
            
            for(GameObject go:gameObjects) {
                if(go instanceof Platform && this.hit(go)) {
                    this.setY(this.getY() - unit);
                    if(this.getVSpeed() < 0) setInAir(false);
                    this.setVSpeed(0);

                    if(((Platform) go).getType().equals(PlatformType.BOUNCY)) this.jump();
                    
                    return; //exits the method instead of the current loop
                }
                //System.out.println("");
                
            }
        }
        this.applyGravity();
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    public void setHSpeed(int hspeed) {
        this.hspeed = hspeed;
    }
    
    public int getHSpeed() {
        return this.hspeed;
    }
    
    public void zeroHSpeed() {
        this.hspeed = 0;
    }
    
    public void speedUpRight() {
        if(this.hspeed < Rigidbody.MAXHSPEED) this.hspeed++;
        this.setDirection(1); // Facing to the Right
    }
    
    public void speedUpLeft() {
        if(this.hspeed > (-1 * Rigidbody.MAXHSPEED)) this.hspeed--;
        this.setDirection(-1); // Facing to the Left
    }
    
    public void slowX() {
        if(this.hspeed > 0) this.hspeed--;
        else if(this.hspeed < 0) this.hspeed++;
    }
    
    public void moveH(ArrayList<GameObject> gameObjects) {
        int movement = 0; // Magnitude of movement progress
        int unit = 1; // Direction of movement. - down, + up
        if(this.hspeed < 0) unit = -1;
        
        while(movement < Math.abs(this.hspeed) ) {
            movement++;
            this.setX(this.getX() + unit);
            
            for(GameObject go: gameObjects) {
                if(go instanceof Platform && this.hit(go)) {
                    this.setX(this.getX() - unit);
                    this.zeroHSpeed();
                    
                    // If a Rigidbody runs into a Vine Platform from either side, they will rise up
                    if(((Platform) go).getType().equals(PlatformType.VINE)) this.setVSpeed(7);
                    // If a Rigidbody runs into a Portal Platform from either side, the next level will activate
                    if(((Platform) go).getType().equals(PlatformType.PORTAL)) ((GamePanel) this.getPanel()).nextLevel();
                    
                    return; // Exits the method instead of the current loop
                }
            }
        }
    }
    
    public void applyGravity() {
        this.setVSpeed(this.getVSpeed() + this.GRAVITY);
    }
    
    public void jump() {
        if(this.inAir()) return;
        this.setVSpeed(Rigidbody.JUMP_FORCE);
        this.setInAir(true);
    }
}
