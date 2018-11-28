import java.awt.Graphics;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.ArrayList;
public class ESlime extends Enemy {
    
    public ESlime(JPanel jp, int x, int y, CombatantController cbController) {
        super(jp, x, y, 200, 120, 1, 1, 11 * 5, cbController); // width = 200, height = 120, health = 1, damageDealt = 1, attackCooldown = 11 frames * 2
        
        this.createAnimation(this, "images/eSlime.png", new int[] {8, 11, 7, 8, 11, 7}, 0);
        this.getAnimation().setMotionState(0); // Start at 0 in case only one motion state exists
    }
    
    @Override
    public void speedUpRight() {
        if(this.getHSpeed() < Rigidbody.MAXHSPEED) this.setHSpeed(this.getHSpeed() + 1);
        if(!this.getAnimationOverride()) this.setAnimation(ESlimeMotionState.RUN_FORWARDS);
        this.setDirection(1); // Facing to the Right
    }
    
    @Override
    public void speedUpLeft() {
        if(this.getHSpeed() > (-1 * Rigidbody.MAXHSPEED)) this.setHSpeed(this.getHSpeed() - 1);
        if(!this.getAnimationOverride()) this.setAnimation(ESlimeMotionState.RUN_BACKWARDS);
        this.setDirection(-1); // Facing to the Left
    }
    
    @Override
    public void attackAnimations() {
        if(this.getDirection() == 1) this.setAnimation(ESlimeMotionState.ATTACK_FORWARDS);
        else this.setAnimation(ESlimeMotionState.ATTACK_BACKWARDS);
        this.setAnimationOverride(true);
    }
    
    @Override
    public void die() {
        // Plays death Animation
        if(this.getDirection() == 1) this.setAnimation(ESlimeMotionState.DIE_FORWARDS);
        else this.setAnimation(ESlimeMotionState.DIE_BACKWARDS);
        
        // Adds 1 to the score upon killing an ESlime
        ((GamePanel) this.getPanel()).setScore(((GamePanel) this.getPanel()).getScore() + 1);
    }
    
    @Override
    public void draw(Graphics g) {
        if(isVisible()) {
            this.getAnimation().draw(g);
            
            for(Collider c: getColliders()) {
                c.draw(g);
            }
        }
    }
}
