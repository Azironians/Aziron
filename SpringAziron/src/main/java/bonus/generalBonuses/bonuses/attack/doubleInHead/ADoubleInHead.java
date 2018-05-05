package bonus.generalBonuses.bonuses.attack.doubleInHead;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.processors.exceptions.UnsupportedProcessorException;
import management.service.components.handleComponet.HandleComponent;
import management.service.engine.services.DynamicHandleService;
import management.playerManagement.Player;
import management.processors.Processor;

import java.util.logging.Logger;

public final class ADoubleInHead extends Bonus implements DynamicHandleService {

    static final Logger LOG = Logger.getLogger(ADoubleInHead.class.getName());

    static final double ATTACK_COEFFICIENT = 2;

    private Processor previousProcessor;

    public ADoubleInHead(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        this.installCustomAttackProcessor();
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    private void installCustomAttackProcessor() {
        try {
            final Processor attackProcessor = new DoubleInHeadProcessor(actionManager, battleManager, playerManager);
            this.previousProcessor = actionManager.getAttackProcessor();
            this.actionManager.setAttackProcessor(attackProcessor);
            LOG.info("INSTALLED CUSTOM BEFORE_ATTACK PROCESSOR");
        } catch (final UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    private void installDefaultAttack() {
        try {
            actionManager.setAttackProcessor(previousProcessor);
            LOG.info("INSTALLED DEFAULT BEFORE_ATTACK PROCESSOR");
        } catch (UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private boolean isWorking = true;

            private Player player;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    installDefaultAttack();
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "DoubleInHead";
            }

            @Override
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}