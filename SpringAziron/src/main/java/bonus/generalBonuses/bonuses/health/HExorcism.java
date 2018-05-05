package bonus.generalBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import management.service.components.handleComponet.HandleComponent;
import management.service.engine.services.DynamicHandleService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class HExorcism extends Bonus implements DynamicHandleService {

    private static final Logger log = Logger.getLogger(HExorcism.class.getName());

    private static final double HEALING_BOOST = 2;

    public HExorcism(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
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

                if (actionType == ActionType.END_TURN && player == actionEvent.getPlayer()) {
                    final Hero currentHero = player.getCurrentHero();

                    if (currentHero.getHealing(HEALING_BOOST)) {
                        log.info("+2 HP");
                        actionManager.getEventEngine().setRepeatHandling(true);
                    }
                }
            }

            @Override
            public final String getName() {
                return "Exorcism";
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
