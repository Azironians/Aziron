package management.actionManagement.actionProccessors;

import bonus.bonuses.Bonus;
import management.actionManagement.ActionManager;
import management.processors.Processor;

//Not final!
public class BonusProcessor implements Processor {

    private final ActionManager actionManager;

    private Bonus bonus;

    public BonusProcessor(final ActionManager actionManager){
        this.actionManager = actionManager;
    }

    @Override
    public final void process() {
        this.bonus.getActionEvents().clear();
        this.bonus.use();
        this.actionManager.refreshScreen();
        this.actionManager.getEventEngine().handle();
        this.actionManager.refreshScreen();
    }

    public void setBonus(final Bonus bonus){
        this.bonus = bonus;
    }
}