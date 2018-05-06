package bonus.generalBonuses.bonuses.attack.atack;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.battleManagement.BattleManager;
import management.playerManagement.PlayerManager;
import management.service.components.handleComponet.EngineComponent;

import java.util.logging.Logger;

public final class AttackEngineComponent implements EngineComponent {

    private static final Logger log = Logger.getLogger(AttackEngineComponent.class.getName());

    private static final int ATTACK_BOOST = 10;

    private Hero hero;

    private boolean isWorking = true;

    @Override
    public final void setup(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager, final Hero hero) {
        this.hero = hero;
    }

    final void use() {
        this.hero.setAttack(this.hero.getAttack() + ATTACK_BOOST);
        log.info("+10 BEFORE_ATTACK");
    }

    @Override
    public final void handle(final ActionEvent actionEvent) {
        final ActionType actionType = actionEvent.getActionType();
        if (actionType == ActionType.END_TURN) {
            this.hero.setAttack(this.hero.getAttack() - ATTACK_BOOST);
            this.isWorking = false;
            log.info("-10 BEFORE_ATTACK");
        }
    }

    @Override
    public final String getName() {
        return "Attack!";
    }

    @Override
    public final Hero getCurrentHero() {
        return this.hero;
    }

    @Override
    public final boolean isWorking() {
        return this.isWorking;
    }

    @Override
    public final void setWorking(final boolean able) {
        this.isWorking = able;
    }
}