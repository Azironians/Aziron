package bonus.generalBonuses.bonuses.attack.doubleInHead;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actionProccessors.AttackProcessor;
import management.actionManagement.actions.ActionEventFactory;
import management.battleManagement.BattleManager;
import management.playerManagement.ATeam;
import management.playerManagement.PlayerManager;
import management.service.engine.EventEngine;

import static bonus.generalBonuses.bonuses.attack.doubleInHead.DoubleInHeadEngineComponent.ATTACK_COEFFICIENT;
import static bonus.generalBonuses.bonuses.attack.doubleInHead.DoubleInHeadEngineComponent.LOG;

public final class DoubleInHeadProcessor extends AttackProcessor {

    private final PlayerManager playerManager;

    DoubleInHeadProcessor(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager) {
        super(actionManager, battleManager);
        this.playerManager = playerManager;
    }

    private ATeam attackTeam;

    private ATeam victimTeam;

    @Override
    public final void process(){
        final Hero attackHero = this.attackTeam.getCurrentPlayer().getCurrentHero();
        final EventEngine eventEngine = actionManager.getEventEngine();
        final double attackValue = attackHero.getAttack() * ATTACK_COEFFICIENT;
        LOG.info("BEFORE_ATTACK IS DUPLICATED");
        final heroes.abstractHero.hero.Hero opponentHero = victimTeam.getCurrentPlayer().getCurrentHero();
        eventEngine.handle(ActionEventFactory.getBeforeDealDamage(attackHero, opponentHero, attackValue));
        if (opponentHero.getDamage(attackValue)) {
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(attackHero, opponentHero, attackValue));
        }
        this.actionManager.refreshScreen();
        if (this.battleManager.isEndTurn()) {
            this.actionManager.endTurn(attackTeam);
        }
    }

    @Override
    public final void setTeams(final ATeam attackTeam, final ATeam victimTeam){
        this.attackTeam = playerManager.getCurrentTeam();
        this.victimTeam = playerManager.getOpponentTeam();
    }
}