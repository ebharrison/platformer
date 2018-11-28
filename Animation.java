import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Animation extends ScreenObject{

    private ImageIcon spriteSheet;
    private int width, height;
    private GameObject gameObject = null;   // null when animation does not belong to a GameObject
    
    private int cycles;     // number of times animation should loop, 0 is infinite.
    private int currentCycle = 0;
    
    private int[] numFrames;    // number of frames in each row (motion clip)
    private int currentClip;    // clip (row) of the spritesheet
    private int currentFrame;   // frame (col) of the spritesheet
    
    private int animationSmoother = 0;
    
    public Animation(GameObject go, String src, int[] numFrames) {
        this(go.getPanel(), go.getX(), go.getY(), go.getWidth(), go.getHeight(), src, numFrames, 0);
        gameObject = go;
    }
    
    public Animation(GameObject go, String src, int[] numFrames, int cycles) {
        this(go.getPanel(), go.getX(), go.getY(), go.getWidth(), go.getHeight(), src, numFrames, cycles);
        gameObject = go;
    }
    
    public Animation(JPanel jp, int x, int y, int width, int height, String src, int[] numFrames, int cycles) {
        super(jp, x, y);
        
        try{
            spriteSheet = new ImageIcon(src);
        }
        catch(Exception e) {
            System.out.println(e);
        }

        this.width = width; 
        this.height = height;
        this.numFrames = numFrames;
        this.cycles = cycles;
        currentClip = 0;
        currentFrame = 0;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setMotionState(int st) {
        if(st != currentClip) {
            currentClip = st;
            currentFrame = 0;
        }
    }
    
    public void draw(Graphics g) {
        drawFrame((Graphics2D) g);
    }
    
    private void drawFrame(Graphics2D g2d){
        if(cycles == 0 || currentCycle < cycles) {
            if(currentFrame == numFrames[currentClip]) {
                currentFrame = 0;
                currentCycle++;
            }
            int frameX = currentFrame * width;
            int frameY = currentClip * height;
                        
            if(gameObject != null) {
                setX(gameObject.getX());
                setY(gameObject.getY());
            }
            
            g2d.drawImage(spriteSheet.getImage(), 
                    getX(), getY(),
                    getX() + width, getY() + height, 
                    frameX, frameY,
                    frameX + width, frameY + height,
                    null);
            
            animationSmoother++;
            if(animationSmoother % 5 == 0) currentFrame++;
        }
    }
}
