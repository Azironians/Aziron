package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.ATeam;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class HSelfKeepingInstinct extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(HSelfKeepingInstinct.class.getName());

    private static final double HEALING = 10;

    public HSelfKeepingInstinct(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final EngineComponent engineComponent = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(engineComponent);
        log.info(name + " is activated");
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private ATeam opponentTeam;

            private boolean isWorking;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponentTeam = playerManager.getOpponentTeam();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                final Player opponent = actionEvent.getHero();
                if (opponent == opponentTeam.getCurrentPlayer()
                        && actionType == ActionType.BEFORE_USED_SKILL){
                    if (player.getCurrentHero().getHealing(HEALING)){
                        log.info("+10 HP");
                        actionManager.getEventEngine().setRepeatHandling(true);
                    }
                }
            }

            @Override
            public final String getName() {
                return "SelfKeepingInstinct";
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
