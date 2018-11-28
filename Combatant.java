import javax.swing.JPanel;
import java.util.ArrayList;

import java.util.Iterator;
public abstract class Combatant extends Rigidbody
{
    private CombatantController cbController;
    
    private int damageDealt;
    private int health;
    
    private boolean animationOverride = false;
    
    public Combatant(JPanel jp, int x, int y, int w, int h, int health, int damageDealt, CombatantController cbController) {
        super(jp, x, y, w, h);
        this.damageDealt = damageDealt;
        this.health = health;
        this.cbController = cbController;
    }
    
    public CombatantController getCombatantController() {
        return this.cbController;
    }
    
    public boolean getAnimationOverride() {
        return this.animationOverride;
    }
    
    public void setAnimationOverride(boolean animationOverride) {
        this.animationOverride = animationOverride;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public int getDamageDealt() {
        return this.damageDealt;
    }
    
    public void takeDamage(int damageDealt, int direction) {
        // The actual taking of the damage
        this.health -= damageDealt;
        
        // Simulate knockback
        this.setHSpeed(20 * direction);
        this.setVSpeed(15);
        
        // Check for death
        if(health < 1) {
            this.cbController.addFutureRemoval(this); // Does not directly remove 'this' Combatant to avoid a potential ConcurrentModificationException being thrown
            this.die();
        }
    }
    
    public abstract void attackAnimations(); // Used for specific Animations and timings
    
    public abstract void die(); // Used for death Animation
    
    public void attackDetection(ArrayList<Combatant> combatants) { // Note: as this is currently implemented it allows for 'friendly fire'
        CircleCollider attackCollider;
        // Makes the collider far away from the Combatant
        if(this.getDirection() == 1) {
            attackCollider = new CircleCollider(this, this.getWidth() * (5 / 4) , this.getHeight()/2, ((this.getWidth()/2) + (this.getHeight()/2))/3);
        }else{
            attackCollider = new CircleCollider(this, this.getWidth() * (-1 / 4), this.getHeight()/2, ((this.getWidth()/2) + (this.getHeight()/2))/3);
        }
        attackCollider.setTrigger(true);
        
        for(Combatant cb: combatants) {
            if((!cb.equals(this)) && attackCollider.hitTrigger(cb)) {
                cb.takeDamage(this.getDamageDealt(), this.getDirection());
                return; // Can only hit one Combatant per attack
            }
        }
    }
}
