import java.util.ArrayList;
public class TokenController {
    private ArrayList<Token> tokens;
    private ArrayList<Token> tokensToRemove;
    
    public TokenController() {
        tokens = new ArrayList<>();
        tokensToRemove = new ArrayList<>();
    }
    
    public void addToken(Token t) {
        tokens.add(t);
    }
    
    public ArrayList<Token> getTokens() {
        return this.tokens;
    }
    
    public void addFutureRemoval(Token t) {
        tokensToRemove.add(t);
    }
    
    public void removeRemovedTokens() {
        tokens.removeAll(tokensToRemove);
    }
}
