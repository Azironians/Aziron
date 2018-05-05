package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import heroes.devourer.skills.superSkills.consuming.utilities.ConsumingMessageParser;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponet.HandleComponent;
import management.service.engine.services.DynamicHandleService;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class HMutation extends Bonus implements DynamicHandleService {

    private static final Logger log = Logger.getLogger(HMutation.class.getName());

    public HMutation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getHandlerInstance());
        log.info("Mutation is activated");
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
                final Object message = actionEvent.getData();
                if (ConsumingMessageParser.isConsumingMessage((String) message)){
                    final double damage = ConsumingMessageParser
                            .parseMessageGetHealing((String) message);
                    final Hero hero = player.getCurrentHero();
                    hero.setHealthSupply(hero.getHealthSupply() + damage);
                    log.info("+" + damage + "to supply health");
                    actionManager.getEventEngine().handle();
                }
                if (actionType == ActionType.END_TURN) {
                    isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "Mutation";
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
                this.isWorking = false;
            }
        };
    }
}