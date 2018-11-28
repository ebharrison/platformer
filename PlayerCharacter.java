import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.ArrayList;
public class PlayerCharacter extends Combatant{
    private static final int MAXHSPEED = 8;
    private static final int JUMP_FORCE = 18;
    
    private Animation animation;

    public PlayerCharacter(JPanel jp, int x, int y, CombatantController cbController, boolean singleFrame) {
        super(jp, x, y, 153, 150, 3, 1, cbController);
        // Note: Assumes player Combatant values to be 3 health and 1 damage
        
        if(singleFrame) this.animation = new Animation(this, "images/pc_singleframe.png", new int[] {1}, 0);
        else this.animation = new Animation(this, "images/pc.png", new int[]{9, 9, 8, 8, 8, 8, 3, 3, 7, 7, 6, 6, 7, 7}, 0);
        animation.setMotionState(0); // Start at 0 in case only one motion state exists (as with titlePC)
        
        CircleCollider cc = new CircleCollider(this, getWidth()/2, getHeight()/2, getWidth()/2);
        cc.setVisible(false);
        this.getColliders().add(cc);
        
        BoxCollider bc = new BoxCollider(this, 0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
        bc.setVisible(false);
        this.getColliders().add(bc);
    }
    
    @Override
    public void attackAnimations() {
        // Animations and timing with animations
        if(this.getDirection() == 1) animation.setMotionState(PlayerMotionState.KICK_RIGHT);
        else animation.setMotionState(PlayerMotionState.KICK_LEFT);        
        attackDetection(this.getCombatantController().getCombatants());
        this.setAnimationOverride(true);
        this.setAnimationOverride(false);
    }
    
    @Override
    public void die() {
        // Play death Animation
        ((GamePanel) this.getPanel()).setGameState(GameState.END);
        if(this.getDirection() == 1) animation.setMotionState(PlayerMotionState.DIE_LEFT);
        else animation.setMotionState(PlayerMotionState.DIE_RIGHT);
    }
    
    @Override
    public void jump() {
        if(this.inAir()) return;
        this.setVSpeed(PlayerCharacter.JUMP_FORCE);
        this.setInAir(true);
        if(!this.getAnimationOverride()) {
            if(this.getDirection() == 1) animation.setMotionState(PlayerMotionState.JUMP_RIGHT);
            else animation.setMotionState(PlayerMotionState.JUMP_LEFT);
        }
    }
    
    @Override
    public void speedUpRight() {
        if(this.getHSpeed() < PlayerCharacter.MAXHSPEED) this.setHSpeed(this.getHSpeed() + 1);
        if(!this.inAir() && !this.getAnimationOverride()) animation.setMotionState(PlayerMotionState.RUN_FORWARDS);
        this.setDirection(1); // Facing to the Right
    }
    
    @Override
    public void speedUpLeft() {
        if(this.getHSpeed() > (-1 * PlayerCharacter.MAXHSPEED)) this.setHSpeed(this.getHSpeed() - 1);
        if(!this.inAir() && !this.getAnimationOverride()) animation.setMotionState(PlayerMotionState.RUN_BACKWARDS);
        this.setDirection(-1); // Facing to the Left
    }
    
    @Override
    public void slowX() {
        if(this.getHSpeed() > 0) this.setHSpeed(this.getHSpeed() - 1);
        else if(this.getHSpeed() < 0) this.setHSpeed(this.getHSpeed() + 1);
        else {
            if(this.getDirection() == 1) animation.setMotionState(PlayerMotionState.IDLE_FORWARD);
            else animation.setMotionState(PlayerMotionState.IDLE_BACKWARD);
        } 
        //else if(!this.getAnimationOverride())animation.setMotionState(PlayerMotionState.IDLE_FORWARD);
    }
    
    @Override
    public void draw(Graphics g) {
        if(isVisible()) {
            animation.draw(g);
            
            for(Collider c: getColliders()) {
                c.draw(g);
            }
        }
    }
}
