package bonus.generalBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import management.service.components.handleComponet.HandleComponent;
import management.service.engine.services.DynamicHandleService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.logging.Logger;


public final class AAttack extends Bonus implements DynamicHandleService {

    private static final Logger log = Logger.getLogger(AAttack.class.getName());

    public AAttack(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private static final int ATTACK_BOOST = 10;

    @Override
    public final void use() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();

        currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        log.info("+10 BEFORE_ATTACK");
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final ActionType actionType = actionEvent.getActionType();
                if (actionType == ActionType.END_TURN) {
                    final Hero currentHero = player.getCurrentHero();
                    currentHero.setAttack(currentHero.getAttack() - ATTACK_BOOST);
                    isWorking = false;
                    log.info("-10 BEFORE_ATTACK");
                }
            }

            @Override
            public final String getName() {
                return "Attack!";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                log.info("BEFORE_ATTACK BONUS: " + isWorking);
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}
