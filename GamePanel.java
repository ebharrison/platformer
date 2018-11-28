import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class GamePanel extends JPanel{
    private static final long serialVersionUID = 1L;
    
    private GameState state = GameState.TITLE;
    
    // These ArrayLists do not include the Player Character or any Enemies
    private ArrayList<GameObject> gameObjectsTTRL;
    private ArrayList<GameObject> gameObjectsL1;
    private ArrayList<GameObject> gameObjectsL2;
    private ArrayList<GameObject> gameObjectsL3;
    
    private CombatantController cbControllerTTRL;
    private CombatantController cbControllerL1;
    private CombatantController cbControllerL2;
    private CombatantController cbControllerL3;
    
    private TokenController tokenControllerTTRL;
    private TokenController tokenControllerL1;
    private TokenController tokenControllerL2;
    private TokenController tokenControllerL3;

    private PlayerCharacter pcTitle;
    private PlayerCharacter pcTTRL;
    private PlayerCharacter pcL1;
    private PlayerCharacter pcL2;
    private PlayerCharacter pcL3;
    
    // These are used for generic purposes, using the set methods within this class to make them into the defined variables
    private ArrayList<GameObject> gameObjectsGeneric;
    private CombatantController cbControllerGeneric;
    private TokenController tokenControllerGeneric;
    private PlayerCharacter pcGeneric;
    private Background background = new Background(); 
    
    private Camera camera;
    private JButton btnStart;
    
    private int score = 0;
    
    
    public GamePanel() {
        setLayout(null);
        
        // ///// Tutorial Setup /////
        gameObjectsTTRL = new ArrayList<GameObject>();
        cbControllerTTRL = new CombatantController();
        tokenControllerTTRL = new TokenController();
        
        // Tutorial Player
        pcTTRL = new PlayerCharacter(this, 0, -200, cbControllerTTRL, false);
        cbControllerTTRL.addCombatant(pcTTRL);
        
        // Tutorial Platforms
        gameObjectsTTRL.add(new Platform(this, -50, 100, 11500, 50)); // Ground
        gameObjectsTTRL.add(new Platform(this, -100, -1000, 50, 1150)); // Left wall
        gameObjectsTTRL.add(new Platform(this, 5900, 50, 300, 50, PlatformType.BOUNCY)); // Bouncy Platform intro
        gameObjectsTTRL.add(new Platform(this, 7300, -100, 40, 200, PlatformType.VINE)); // Vine Platform intro
        gameObjectsTTRL.add(new Platform(this, 7700, -150, 40, 250, PlatformType.VINE));
        gameObjectsTTRL.add(new Platform(this, 8600, 50, 40, 50)); // Enemy intro
        gameObjectsTTRL.add(new Platform(this, 9800, 50, 40, 50));
        gameObjectsTTRL.add(new Platform(this, 10900, -100, 100, 200, PlatformType.PORTAL)); // Portal to next level
        
        // Platforms making an arrow pointing to the right
        gameObjectsTTRL.add(new Platform(this, 250, 250, 300, 25));
        gameObjectsTTRL.add(new Platform(this, 400, 200, 50, 25));
        gameObjectsTTRL.add(new Platform(this, 400, 300, 50, 25));
        gameObjectsTTRL.add(new Platform(this, 450, 225, 50, 75));
        
        // Tutorial Enemies
        cbControllerTTRL.addCombatant(new ESlime(this, 9100, -1000, cbControllerTTRL)); // Enemy intro
        
        // Tutorial Tokens
        tokenControllerTTRL.addToken(new Token(this, 3000, 0)); // Token intro
        tokenControllerTTRL.addToken(new Token(this, 4500, -150)); // Jumping intro
        tokenControllerTTRL.addToken(new Token(this, 6040, -150)); // Bouncy Platform intro
        tokenControllerTTRL.addToken(new Token(this, 7320, -150)); // Vine Platform intro
        tokenControllerTTRL.addToken(new Token(this, 7720, -200));
        tokenControllerTTRL.addToken(new Token(this, 10000, 0)); // Reward for Enemy intro
        
        // ///// Level 1 Setup /////
        gameObjectsL1 = new ArrayList<GameObject>();
        cbControllerL1 = new CombatantController();
        tokenControllerL1 = new TokenController();
        
        // Level 1 Player
        pcL1 = new PlayerCharacter(this, 0, 0, cbControllerL1, false);
        cbControllerL1.addCombatant(pcL1);
        
        // Level 1 Platforms
        gameObjectsL1.add(new Platform(this, -50, 2000, 6050, 50)); // Ground
        gameObjectsL1.add(new Platform(this, -100, -1000, 50, 3050)); // Left wall
        gameObjectsL1.add(new Platform(this, 6000, -1000, 50, 3050)); // Right wall
        gameObjectsL1.add(new Platform(this, -50, 200, 500, 50));
        gameObjectsL1.add(new Platform(this, 200, 350, 500, 50));
        gameObjectsL1.add(new Platform(this, 1080, 230, 1000, 50)); // Platform that has portal on top, visible from 'starting area' but unreachable
        gameObjectsL1.add(new Platform(this, 1110, 30, 100, 200, PlatformType.PORTAL)); // Portal to next level
        gameObjectsL1.add(new Platform(this, 550, 500, 500, 50));
        gameObjectsL1.add(new Platform(this, 1100, 670, 300, 50));
        gameObjectsL1.add(new Platform(this, 1650, 670, 700, 50));
        gameObjectsL1.add(new Platform(this, 2600, 670, 300, 50, PlatformType.BOUNCY));
        gameObjectsL1.add(new Platform(this, 2850, 800, 300, 50, PlatformType.BOUNCY));
        gameObjectsL1.add(new Platform(this, 3170, 640, 300, 50, PlatformType.BOUNCY));
        gameObjectsL1.add(new Platform(this, 3700, 640, 300, 50, PlatformType.BOUNCY));
        gameObjectsL1.add(new Platform(this, 4050, 570, 300, 50, PlatformType.BOUNCY));
        gameObjectsL1.add(new Platform(this, 4500, 500, 300, 50, PlatformType.BOUNCY));
        gameObjectsL1.add(new Platform(this, 5150, -280, 40, 1000, PlatformType.VINE));
        gameObjectsL1.add(new Platform(this, 4820, 650, 40, 1350, PlatformType.VINE));
        gameObjectsL1.add(new Platform(this, 4050, -100, 850, 50));
        gameObjectsL1.add(new Platform(this, 4300, -500, 300, 50)); // Upper Platform of mini fight area
        gameObjectsL1.add(new Platform(this, 5350, -450, 300, 50));
        gameObjectsL1.add(new Platform(this, 4880, -600, 300, 40));
        gameObjectsL1.add(new Platform(this, 2800, 0, 1000, 50));
        gameObjectsL1.add(new Platform(this, 2300, 100, 300, 50));
        gameObjectsL1.add(new Platform(this, 2600, 100, 100, 50));
        
        // Level 1 Enemies
        cbControllerL1.addCombatant(new ESlime(this, 1700, 300, cbControllerL1));
        cbControllerL1.addCombatant(new ESlime(this, 4300, -350, cbControllerL1));
        cbControllerL1.addCombatant(new ESlime(this, 3050, -350, cbControllerL1));
        cbControllerL1.addCombatant(new ESlime(this, 3600, -350, cbControllerL1));
        
        // Level 1 Tokens
        tokenControllerL1.addToken(new Token(this, 200, 0)); // Opening token
        tokenControllerL1.addToken(new Token(this, 3000, 700)); // Bouncy Platform token
        tokenControllerL1.addToken(new Token(this, 5115, -100)); // Vine Climbing token
        tokenControllerL1.addToken(new Token(this, 4450, -700)); // Upper mini fight area token
        
        // ///// Level 2 Setup /////
        gameObjectsL2 = new ArrayList<GameObject>();
        cbControllerL2 = new CombatantController();
        tokenControllerL2 = new TokenController();
        
        // Level 2 Player
        pcL2 = new PlayerCharacter(this, 500, 0, cbControllerL2, false);
        cbControllerL2.addCombatant(pcL2);
        
        // Level 2 Platforms
        gameObjectsL2.add(new Platform(this, -50, 200, 13050, 50)); // Base Ground
        gameObjectsL2.add(new Platform(this, -50, -1000, 50, 3050)); // Left wall
        gameObjectsL2.add(new Platform(this, 33000, -1000, 50, 3050)); // Right wall
        gameObjectsL2.add(new Platform(this, 31000, -2000, 500, 50)); // Higher Ground
        gameObjectsL2.add(new Platform(this, 31400, -2500, 100, 500, PlatformType.PORTAL)); // Portal to the next level
        
        // Sloping Platforms
        for(int i = 0; i < 100; i++) {
            gameObjectsL2.add(new Platform(this, 1000 + (300 * i), 100 - (20 * i), 300, 10));
        }
        
        // Level 2 Enemies
        cbControllerL2.addCombatant(new ESlime(this, 4500, -500, cbControllerL2));
        cbControllerL2.addCombatant(new ESlime(this, 9000, -1000, cbControllerL2));
        cbControllerL2.addCombatant(new ESlime(this, 13500, -1500, cbControllerL2));
        cbControllerL2.addCombatant(new ESlime(this, 18000, -2000, cbControllerL2));
        cbControllerL2.addCombatant(new ESlime(this, 22500, -2500, cbControllerL2));
        cbControllerL2.addCombatant(new ESlime(this, 27000, -3000, cbControllerL2));
        cbControllerL2.addCombatant(new ESlime(this, 30000, -3000, cbControllerL2));
        
        // Level 2 Tokens
        tokenControllerL2.addToken(new Token(this, 2500, -170));
        tokenControllerL2.addToken(new Token(this, 5000, -350));
        tokenControllerL2.addToken(new Token(this, 7500, -540));
        tokenControllerL2.addToken(new Token(this, 10000, -700));
        
        // ///// Level 3 Setup /////
        gameObjectsL3 = new ArrayList<GameObject>();
        cbControllerL3 = new CombatantController();
        tokenControllerL3 = new TokenController();
        
        // Level 3 Player
        pcL3 = new PlayerCharacter(this, 500, 0, cbControllerL3, false);
        cbControllerL3.addCombatant(pcL3);
        
        // Level 3 Platforms
        gameObjectsL3.add(new Platform(this, -50, 1000, 1050, 50)); // Ground
        gameObjectsL3.add(new Platform(this, -50, -1000, 50, 2050)); // Left wall
        gameObjectsL3.add(new Platform(this, 1000, -1000, 50, 2050)); // Right wall
        gameObjectsL3.add(new Platform(this, 0, 400, 400, 20));
        gameObjectsL3.add(new Platform(this, 0, 100, 200, 20));
        gameObjectsL3.add(new Platform(this, 600, 400, 400, 20));
        gameObjectsL3.add(new Platform(this, 800, 100, 200, 20));
        gameObjectsL3.add(new Platform(this, 600, 400, 400, 20));
        gameObjectsL3.add(new Platform(this, 400, 300, 200, 20));
        gameObjectsL3.add(new Platform(this, 200, 200, 100, 20));
        gameObjectsL3.add(new Platform(this, 700, 200, 100, 20));
        
        // Level 3 Enemies
        cbControllerL3.addCombatant(new ESlime(this, 0, -50, cbControllerL3));
        cbControllerL3.addCombatant(new ESlime(this, 50, 200, cbControllerL3));
        cbControllerL3.addCombatant(new ESlime(this, 800, -50, cbControllerL3));
        cbControllerL3.addCombatant(new ESlime(this, 850, 200, cbControllerL3));
        
        // Level 3 Tokens
        tokenControllerL3.addToken(new Token(this, 200, 0));
        
        // ///// Other Setup /////
        pcTitle = new PlayerCharacter(this, 900, 400, null, true);
        
        camera = new Camera(this, 0, 0);
        camera.setVisible(false);
        
        btnStart = new JButton("Start");
        btnStart.setBounds(800, 520, 200, 40);
        btnStart.setFocusable(false);
        this.add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = GameState.TUTORIAL;
                btnStart.setVisible(false);
            }
        });
        
        // Makes the button blend into the background, different from classic JButton style
        btnStart.setBackground(Color.RED);
        btnStart.setOpaque(true);
        // Makes button flat in style
        btnStart.setFocusPainted(false);
        btnStart.setContentAreaFilled(false);
    }
    
    public ArrayList<GameObject> getGameObjects() {
        switch(state) {
            case TITLE:
                return null;
            case TUTORIAL:
                return this.gameObjectsTTRL;
            case LEVEL_1:
                return this.gameObjectsL1;
            case LEVEL_2:
                return this.gameObjectsL2;
            case LEVEL_3:
                return this.gameObjectsL3;
            case END:
                return null;
            default:
                return null;
        }
    }
    
    public PlayerCharacter getPC() {
        switch(state) {
            case TITLE:
                return this.pcTitle;
            case TUTORIAL:
                return this.pcTTRL;
            case LEVEL_1:
                return this.pcL1;
            case LEVEL_2:
                return this.pcL2;
            case LEVEL_3:
                return this.pcL3;
            case END:
                return this.pcTitle;
            default:
                return this.pcTitle;
        }
    }
    
    public CombatantController getCombatantController() {
        switch(state) {
            case TITLE:
                return null;
            case TUTORIAL:
                return this.cbControllerTTRL;
            case LEVEL_1:
                return this.cbControllerL1;
            case LEVEL_2:
                return this.cbControllerL2;
            case LEVEL_3:
                return this.cbControllerL3;
            case END:
                return null;
            default:
                return null;
        }
    }
    
    public TokenController getTokenController() {
        switch(state) {
            case TITLE:
                return null;
            case TUTORIAL:
                return this.tokenControllerTTRL;
            case LEVEL_1:
                return this.tokenControllerL1;
            case LEVEL_2:
                return this.tokenControllerL2;
            case LEVEL_3:
                return this.tokenControllerL3;
            case END:
                return null;
            default:
                return null;
        }
    }
    
    public void setGameState(GameState state) {
        this.state = state;
    }
    
    public GameState getGameState() {
        return this.state;
    }
    
    public void nextLevel() {
        // Increases score by 10 every time the player advances to the next level
        this.setScore(this.getScore() + 10);
        // Gets the next GameState
        switch(state) {
            case TITLE:
                break;
            case TUTORIAL:
                this.state = GameState.LEVEL_1;
                break;
            case LEVEL_1:
                this.state = GameState.LEVEL_2;
                break;
            case LEVEL_2:
                this.state = GameState.LEVEL_3;
                break;
            case LEVEL_3:
                this.state = GameState.END;
                break;
            case END:
                break;
            default:
                break;
        }
    }
    
    public void physicsUpdate() {
        switch(state) {
            case TITLE:
                break;
            case TUTORIAL:
                physicsGeneric();
                break;
            case LEVEL_1:
                physicsGeneric();                
                break;
            case LEVEL_2:
                physicsGeneric();
                break;
            case LEVEL_3:
                physicsGeneric();
            case END:
                break;
            default:
                break;
        }
    }
    
    public void physicsGeneric() {
        // Uses the getter methods in order to make the drawing apply genericly
        gameObjectsGeneric = this.getGameObjects();
        cbControllerGeneric = this.getCombatantController();
        tokenControllerGeneric = this.getTokenController();
        pcGeneric = this.getPC();
        
        pcGeneric.moveH(gameObjectsGeneric);   // Horizontal Movement
        pcGeneric.vUpdate(gameObjectsGeneric); // Vertical Movement
        
        for(Combatant cb: cbControllerGeneric.getCombatants()) {
            if(!cb.equals(pcGeneric)) {
                cb.moveH(gameObjectsGeneric);
                cb.vUpdate(gameObjectsGeneric);
            }
        }
        
        camera.follow(pcGeneric);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(state) {
            case TITLE:
                drawTitle(g);
                break;
            case TUTORIAL:
                drawTutorial(g);
                drawHUD(g);
                break;
            case LEVEL_1:
                drawLevel_1(g);
                drawHUD(g);
                break;
            case LEVEL_2:
                drawLevel_2(g);
                drawHUD(g);
                break;
            case LEVEL_3:
                drawLevel_3(g);
                drawHUD(g);
                break;
            case END:
                drawEnd(g);
                break;
            default:
                break;
        }
    }
    
    public void drawHUD(Graphics g) {
        
        // Draw a background for the HUD
        g.setColor(Color.ORANGE);
        g.fillRect(camera.getX() + this.getWidth() - 200, camera.getY(), 200, 110);
        
        // Draw the words on the HUD
        g.setFont(new Font("Dialog", Font.BOLD, 40));
        g.setColor(Color.BLACK);
        
        g.drawString("Health: " + this.getPC().getHealth(), camera.getX() + this.getWidth() - 190, camera.getY() + 40);
        g.drawString("Score: " + this.getScore(), camera.getX() + this.getWidth() - 190, camera.getY() + 90);
    }
    
    public void drawTitle(Graphics g) {
        // Draw a background
        ImageIcon imgBG = new ImageIcon(background.getImageSource());
        imgBG.paintIcon(this, g, 0, background.getY1());
        imgBG = new ImageIcon(background.getImageSource());
        imgBG.paintIcon(this, g, 0, background.getY2()); 
        
        // Title
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.RED);
        g.drawString("Earth-Chan Saves the World", (this.getWidth() / 3), (this.getHeight() / 3));
    }
    
    public void drawEnd(Graphics g) {
        // Background
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        // Title
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.setColor(Color.RED);
        g.drawString("R . I . P .", 450, 150);
    
        // Player Character
        pcTitle.draw(g);
    }
    
    public void drawTutorial(Graphics g) {
        drawGeneric(g);
        
        // Tutorial text
        g.setFont(new Font("Dialog", Font.BOLD, 40));
        g.setColor(Color.BLACK);
        
        g.drawString("Welcome to - Earth-Chan Saves the World -", 50, -50);
        g.drawString("Or atleast its tutorial.", 350, -10);
        g.drawString("As you already know,", 1200, -50);
        g.drawString("you can use A and D to move Left and Right.", 1350, -10);
        g.drawString("This is a coin! Walk through it to pick it up for + 1 score!", 2500, -50);
        g.drawString("Pressing W or the Space Bar will make you jump.", 4000, -50);
        g.drawString("This is a Bouncy Platform - Walking over it will make you jump.", 5500, -50);
        g.drawString("These are Vines - Walking into them ----- raises you up.", 7000, -50);
        g.drawString("Watch out! This is an Oil Slime! If it attacks you it will hurt!", 8600, -50);
        g.drawString("Fight back by pressing J or K!", 8800, -10);
        g.drawString("This portal will take you to", 10400, -50);
        g.drawString("the first level!", 10500, -10);
    }
    
    public void drawLevel_1(Graphics g) {
        drawGeneric(g);
    }
    
    public void drawLevel_2(Graphics g) {
        drawGeneric(g);
    }
    
    public void drawLevel_3(Graphics g) {
        drawGeneric(g);
    }
    
    public void drawGeneric(Graphics g) {        
        // Draw a background
        ImageIcon imgBG = new ImageIcon(background.getImageSource());
        imgBG.paintIcon(this, g, 0, background.getY1());
        imgBG = new ImageIcon(background.getImageSource());
        imgBG.paintIcon(this, g, 0, background.getY2()); 
        
        // Uses the getter methods in order to make the drawing apply genericly
        gameObjectsGeneric = this.getGameObjects();
        cbControllerGeneric = this.getCombatantController();
        tokenControllerGeneric = this.getTokenController();
        pcGeneric = this.getPC();
        
        g.translate(camera.getX() * -1, camera.getY() * -1);
        g.setColor(Color.WHITE);
        g.fillRect(-10000, -10000, this.getWidth() + 50000, this.getHeight() + 50000);
        
        for(GameObject go: gameObjectsGeneric) {
            go.draw(g);
        }
        
        for(Combatant cb: cbControllerGeneric.getCombatants()) {
            cb.draw(g);
        }
        
        for(Token t: tokenControllerGeneric.getTokens()) {
            t.draw(g);
        }
        
        pcGeneric.draw(g);
        
        camera.draw(g);
    }
    
    public int getScore(){
        return this.score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
}
