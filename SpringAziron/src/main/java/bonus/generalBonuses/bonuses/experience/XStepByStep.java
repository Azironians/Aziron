package bonus.generalBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import management.service.components.handleComponet.HandleComponent;
import management.service.engine.services.DynamicHandleService;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class XStepByStep extends Bonus implements DynamicHandleService {

    private static final Logger log = Logger.getLogger(XStepByStep.class.getName());

    private static final double EXPERIENCE_COEFFICIENT = 0.1;

    private static final int TURNS = 3;

    public XStepByStep(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
        log.info("EXPERIENCE IS INCREASED BY 10% IN DURING 3 TURNS");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private int count = TURNS;

            private Player player;

            private double experience;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.experience = player.getCurrentHero().getCurrentExperience();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final double newExperience = player.getCurrentHero().getCurrentExperience();
                final double comparison = newExperience - experience;
                if (comparison > 0) {
                    log.info("EXPERIENCE WAS INCREASED");
                    final double EXPERIENCE_BOOST = comparison * EXPERIENCE_COEFFICIENT;
                    if (player.getCurrentHero().addExperience(EXPERIENCE_BOOST)) {
                        log.info("EXPERIENCE WAS ADDED BY 10%");
                        this.experience = player.getCurrentHero().getCurrentExperience();
                        actionManager.getEventEngine().setRepeatHandling(true);
                    }
                }
                if (actionEvent.getActionType() == ActionType.END_TURN && actionEvent.getHero() == player) {
                    if (isWorking()) {
                        count--;
                        log.info("COUNTDOWN: " + count);
                    }
                }
            }

            @Override
            public final String getName() {
                return "StepByStep";
            }

            @Override
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return count > 0;
            }

            @Override
            public final void setWorking(final boolean able) {
                if (able){
                    count++;
                } else {
                    count = 0;
                }
            }
        };
    }
}