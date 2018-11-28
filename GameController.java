import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;

public class GameController extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    
    private GamePanel gamePanel;
    private boolean aPressed, wPressed, dPressed, spacePressed, jPressed, kPressed; // Used for KeyListener when pressing various keys, default state is false
    
    private Timer gameloopTimer;
    private static final int GAMELOOP_FREQUENCY = 16;   // ~63 fps
    
    private int l2SpawnTimer = 0;
    
    private PlayerCharacter pc;
    
    private CombatantController cbController;
    private TokenController tController;
    
    public GameController(int width, int height) {
        addKeyListener(this);

        setTitle("Earth-Chan Saves the World!");
        setSize(width, height);
        setVisible(true);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        gamePanel = new GamePanel();
        getContentPane().add(gamePanel);
        
        pc = gamePanel.getPC();
        cbController = gamePanel.getCombatantController();
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ActionListener gameloopListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        gameloop();
                    }
                };
                gameloopTimer = new Timer(GAMELOOP_FREQUENCY, gameloopListener);
                gameloopTimer.start();
            }
        });
    }
    
    private void gameloop() {
        setVisible(true);
        switch(gamePanel.getGameState()) {
            case TITLE:
                gamePanel.repaint();
                break;
            case TUTORIAL:
                basicLoop();
                break;
            case LEVEL_1:
                basicLoop();
                break;
            case LEVEL_2:
                basicLoop();
                if(l2SpawnTimer % 800 == 0) gamePanel.getCombatantController().addCombatant(new ESlime(gamePanel, 30000, -2000, gamePanel.getCombatantController()));
                l2SpawnTimer++;
                break;
            case LEVEL_3:
                basicLoop();
                break;
            case END:
                gamePanel.repaint();
                break;
            default:
                break;
        }
        
    }
    
    public void basicLoop() {
        pc = gamePanel.getPC();
        cbController = gamePanel.getCombatantController();
        tController = gamePanel.getTokenController();
        
        if(wPressed || spacePressed) pc.jump();
        if(aPressed && !dPressed) pc.speedUpLeft();
        if(dPressed && !aPressed) pc.speedUpRight();
        if((!aPressed && !dPressed) || (aPressed && dPressed)) pc.slowX();
        
        if(jPressed || kPressed) pc.attackAnimations();
        
        for(Combatant cb: cbController.getCombatants()) {
            if(cb instanceof Enemy) {
                ((Enemy) cb).basicAI();
            }
        }
        
        cbController.removeRemovedCombatants();
        
        for(Token t: tController.getTokens()){
            if(t.hitTrigger(pc)){
                tController.addFutureRemoval(t);
                gamePanel.setScore(gamePanel.getScore() + 1);
            }
        }
        
        tController.removeRemovedTokens();
        
        gamePanel.physicsUpdate();
        gamePanel.repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) aPressed = true; // Move left
        else if(e.getKeyCode() == KeyEvent.VK_W) wPressed = true; // Jump
        else if(e.getKeyCode() == KeyEvent.VK_D) dPressed = true; // Move right
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) spacePressed = true; // Jump
        else if(e.getKeyCode() == KeyEvent.VK_J) jPressed = true; // Start attacking
        else if(e.getKeyCode() == KeyEvent.VK_K) kPressed = true; // Start attacking
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) aPressed = false; // Stop moving left
        else if(e.getKeyCode() == KeyEvent.VK_W) wPressed = false; // Stop jumping
        else if(e.getKeyCode() == KeyEvent.VK_D) dPressed = false; // Stop moving right
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) spacePressed = false; // Stop jumping
        else if(e.getKeyCode() == KeyEvent.VK_J) jPressed = false; // Stop attacking
        else if(e.getKeyCode() == KeyEvent.VK_K) kPressed = false; // Stop attacking
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}

