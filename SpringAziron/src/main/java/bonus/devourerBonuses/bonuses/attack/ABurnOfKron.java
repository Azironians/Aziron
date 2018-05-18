package bonus.devourerBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class ABurnOfKron extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(ABurnOfKron.class.getName());

    private static final double DAMAGE_COEFFICIENT = 0.05;

    private static final int TURNS = 5;

    public ABurnOfKron(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private int count = TURNS;

            private Player player;

            private Player opponentPlayer;

            private double damage;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.damage = player.getCurrentHero().getAttack() * DAMAGE_COEFFICIENT;
                this.opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.START_TURN && actionEvent.getHero()
                        .equals(opponentPlayer)){
                    if (opponentPlayer.getCurrentHero().getDamage(damage)) {
                        actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(player
                                , opponentPlayer.getCurrentHero(), damage));
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
                return "BurnOfKron";
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
            public final void setWorking(final boolean isWorking) {
                if (isWorking){
                    count++;
                } else {
                    count = 0;
                }
            }
        };
    }
}