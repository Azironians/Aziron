package management.actionManagement.actionProccessors;

import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEventFactory;
import management.service.engine.EventEngine;
import management.battleManagement.BattleManager;
import management.playerManagement.ATeam;
import management.playerManagement.Player;
import management.processors.Processor;

//Not final!
public class AttackProcessor implements Processor {

    private ATeam attackTeam;

    private ATeam victimTeam;

    protected final ActionManager actionManager;

    protected final BattleManager battleManager;

    public AttackProcessor(final ActionManager actionManager, final BattleManager battleManager){
        this.actionManager = actionManager;
        this.battleManager = battleManager;
    }

    @Override
    public void process() {
        final Player attackPlayer = attackTeam.getCurrentPlayer();
        final heroes.abstractHero.hero.Hero attackHero = attackPlayer.getCurrentHero();
        final double attackValue = attackHero.getAttack();
        final EventEngine eventEngine = actionManager.getEventEngine();
        if (attackHero.addExperience(attackValue)) {
            eventEngine.handle();
        }
        final heroes.abstractHero.hero.Hero victimHero = victimTeam.getCurrentPlayer().getCurrentHero();
        if (victimHero.getDamage(attackValue)) {
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(attackPlayer, victimHero, attackValue));
        }
        actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(attackTeam);
        }
    }

    public void setTeams(final ATeam attackTeam, final ATeam victimTeam){
        this.attackTeam = attackTeam;
        this.victimTeam = victimTeam;
    }
}