import javax.swing.JPanel;
import java.util.ArrayList;
public abstract class Enemy extends Combatant {
    private Animation animation;
    
    private int attackTimer;
    private int attackCooldown;
    
    public Enemy(JPanel jp, int x, int y, int w, int h, int health, int damageDealt, int attackCooldown, CombatantController cbController) {
        super(jp, x, y, w, h, health, damageDealt, cbController);
        
        this.attackTimer = 0;
        this.attackCooldown = attackCooldown;
        
        // Adds two basic colliders to each enemy to be their hitbox
        CircleCollider cc = new CircleCollider(this, getWidth()/2, getHeight()/2, getWidth()/3); // Note: getWidth()/3 because ESlime is an absolute unit, resulting in odd hitboxes
        cc.setVisible(false);
        this.getColliders().add(cc);
        
        BoxCollider bc = new BoxCollider(this, 0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
        bc.setVisible(false);
        this.getColliders().add(bc);
        
        // Adds a large collider to the enemy, used to trigger when the Enemy is near the player
        CircleCollider attackTriggerCollider = new CircleCollider(this, getWidth()/2, getHeight()/2, (getWidth()/3 + getHeight()/3) * 2);
        attackTriggerCollider.setVisible(false);
        attackTriggerCollider.setTrigger(true);
        this.getColliders().add(attackTriggerCollider);
    }
    
    
    
    public void basicAI() {
        if(this.getDirection() == 1) speedUpRight();
        else speedUpLeft();

        // Attacks if the enemy is near the player, according to cooldowns and animations
        for(Collider c: getColliders()) {
            if(c.isTrigger() && c.hitTrigger(((GamePanel) this.getPanel()).getPC())) {
                if(attackTimer % attackCooldown == 0) {
                    attackAnimations();
                }
                if(attackTimer % attackCooldown == (attackCooldown / 2)) attackDetection(this.getCombatantController().getCombatants());
                if(attackTimer % attackCooldown == attackCooldown) this.setAnimationOverride(false);
                attackTimer++;
            }
        }
    }
    
    @Override
    public void moveH(ArrayList<GameObject> gameObjects) {
        int movement = 0; //magnitude of movement progress
        int unit = 1; //direction of movement. - down, + up
        if(this.getHSpeed() < 0) unit = -1;
        
        BoxCollider bc;
        
        while(movement < Math.abs(this.getHSpeed()) ) {
            movement++;
            this.setX(this.getX() + unit);
            
            for(GameObject go: gameObjects) {
                if(go instanceof Platform && this.hit(go)) {
                    this.setX(this.getX() - unit);
                    // Makes the Enemy face the other Direction if they hit a wall
                    this.zeroHSpeed();
                    this.setDirection(this.getDirection() * -1);
                    return; // Exits the method instead of the current loop
                }
            }
            
            // Makes a temporary BoxCollider to check if the Enemy will fall off a platform resulting in a large fall
            bc = new BoxCollider(this, this.getWidth() * unit, this.getHeight(), this.getWidth(), this.getHeight()/4);
            
            boolean willFallOff = true;
            
            for(GameObject go: gameObjects) {
                if(go instanceof Platform && bc.hit(go)) { 
                    willFallOff = false;
                }
            }
            if(willFallOff) {
                this.setX(this.getX() - unit);
                // Makes the Enemy face the other Direction if they will fall off the platform they are on
                this.zeroHSpeed();
                this.setDirection(this.getDirection() * -1);
                return; // Exits the method instead of the current loop
            }
        }
    }
    
    public void createAnimation(GameObject go, String src, int[] numFrames, int cycles) {
        this.animation = new Animation(go, src, numFrames, cycles);
    }
    
    public Animation getAnimation() {
        return this.animation;
    }
    
    public void setAnimation(int enemyMotionState) {
        this.animation.setMotionState(enemyMotionState);
    }
}
