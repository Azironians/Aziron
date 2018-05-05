package bonus.devourerBonuses.bonuses.health.regenerationRoot;

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

public final class HRegenerationRoot extends Bonus implements DynamicHandleService {

    private static final Logger log = Logger.getLogger(HRegenerationRoot.class.getName());

    public HRegenerationRoot(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private Processor previousProcessor;

    @Override
    public final void use() {
        installCustomAttack();
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    private void installCustomAttack() {
        this.previousProcessor = actionManager.getAttackProcessor();
        final Processor attackProcessor = new RegenerationRootProcessor(actionManager, battleManager, playerManager);
        try {
            actionManager.setAttackProcessor(attackProcessor);
            log.info("INSTALLED CUSTOM BEFORE_ATTACK PROCESSOR");
        } catch (final UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    private void installDefaultAttack() {
        try {
            actionManager.setAttackProcessor(previousProcessor );
            log.info("INSTALLED DEFAULT BEFORE_ATTACK PROCESSOR");
        } catch (final UnsupportedProcessorException e) {
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
            public void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    installDefaultAttack();
                    isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "RegenerationRoot";
            }

            @Override
            public final Player getCurrentPlayer() {
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