package bonus.devourerBonuses.bonuses.attack.fromFireIntoTheFire;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.processors.exceptions.UnsupportedProcessorException;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Player;
import management.processors.Processor;

import java.util.logging.Logger;

public final class AFromFireIntoTheFire extends Bonus implements DynamicEngineService {

    private static final Logger LOG = Logger.getLogger(AFromFireIntoTheFire.class.getName());

    private Processor previousProcessor;

    public AFromFireIntoTheFire(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        this.installCustomTreatmentProcessor();
        actionManager.getEventEngine().addHandler(getPrototypeEngineComponent());
    }

    private void installCustomTreatmentProcessor() {
        this.previousProcessor = actionManager.getTreatmentProcessor();
        final Processor treatmentProcessor = new FromFireIntoTheFireProcessor(actionManager, battleManager
                , playerManager);
        try {
            actionManager.setTreatmentProcessor(treatmentProcessor);
            LOG.info("INSTALLED CUSTOM BEFORE_TREATMENT PROCESSOR");
        } catch (final UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    private void installDefaultTreatment() {
        try {
            actionManager.setTreatmentProcessor(previousProcessor);
            LOG.info("INSTALLED DEFAULT BEFORE_TREATMENT PROCESSOR");
        } catch (final UnsupportedProcessorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

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
                    installDefaultTreatment();
                    isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "FromFireIntoTheFire";
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