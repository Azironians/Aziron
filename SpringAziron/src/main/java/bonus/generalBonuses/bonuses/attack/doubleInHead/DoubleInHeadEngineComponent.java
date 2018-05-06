package bonus.generalBonuses.bonuses.attack.doubleInHead;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.battleManagement.BattleManager;
import management.playerManagement.PlayerManager;
import management.processors.Processor;
import management.processors.exceptions.UnsupportedProcessorException;
import management.service.components.handleComponet.EngineComponent;

import java.util.logging.Logger;

public final class DoubleInHeadEngineComponent implements EngineComponent {

    static final Logger LOG = Logger.getLogger(DoubleInHeadEngineComponent.class.getName());

    static final double ATTACK_COEFFICIENT = 2.0;

    private Hero hero;

    private ActionManager actionManager;

    private BattleManager battleManager;

    private PlayerManager playerManager;

    private boolean isWorking = true;

    private Processor previousProcessor;

    @Override
    public final void setup(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager, final Hero hero) {
        this.actionManager = actionManager;
        this.battleManager = battleManager;
        this.playerManager = playerManager;
        this.hero = hero;
    }


    final void use() {
        try {
            this.installCustomAttackProcessor();
        } catch (final UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void handle(final ActionEvent actionEvent) {
        final ActionType actionType = actionEvent.getActionType();
        if (actionType == ActionType.END_TURN) {
            try {
                this.installDefaultAttack();
                this.isWorking = false;
            } catch (final UnsupportedProcessorException e) {
                e.printStackTrace();
            }
        }
    }

    private void installCustomAttackProcessor() throws UnsupportedProcessorException {
        final Processor attackProcessor = new DoubleInHeadProcessor(this.actionManager, this.battleManager
                , this.playerManager);
        this.previousProcessor = this.actionManager.getAttackProcessor();
        this.actionManager.setAttackProcessor(attackProcessor);
        LOG.info("INSTALLED CUSTOM BEFORE_ATTACK PROCESSOR");
    }

    private void installDefaultAttack() throws UnsupportedProcessorException {
        this.actionManager.setAttackProcessor(previousProcessor);
        LOG.info("INSTALLED DEFAULT BEFORE_ATTACK PROCESSOR");
    }

    @Override
    public final String getName() {
        return "DoubleInHead";
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