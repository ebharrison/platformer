import java.util.ArrayList;
public class CombatantController {
    private ArrayList<Combatant> combatants;
    private ArrayList<Combatant> combatantsToRemove;
    public CombatantController() {
        combatants = new ArrayList<>();
        combatantsToRemove = new ArrayList<>();
    }
    
    public void addCombatant(Combatant cb) {
        combatants.add(cb);
    }
    
    public ArrayList<Combatant> getCombatants() {
        return this.combatants;
    }
    
    public void addFutureRemoval(Combatant cb) {
        combatantsToRemove.add(cb);
    }
    
    public void removeRemovedCombatants() {
        combatants.removeAll(combatantsToRemove);
    }
}
