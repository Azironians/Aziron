package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import heroes.devourer.skills.superSkills.regeneration.utilities.RegenerationMessageParser;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.service.components.handleComponet.HandleComponent;
import management.service.engine.services.DynamicHandleService;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class HEvolution extends Bonus implements DynamicHandleService {

    private static final Logger log = Logger.getLogger(HEvolution.class.getName());

    private static final double BOOST_COEFFICIENT = 1.03;

    public HEvolution(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handleComponent = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handleComponent);
        log.info("Evolution is activated!");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private boolean isWorking;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (player == actionEvent.getPlayer()) {
                    final Hero hero = player.getCurrentHero();
                    final Object message = actionEvent.getData();
                    if (RegenerationMessageParser.isRegenerationMessage((String) message)) {
                        final double healthSupplyBoost = RegenerationMessageParser
                                .parseMessageGetHealing((String) message) * BOOST_COEFFICIENT;
                        hero.setHealthSupply(hero.getHealthSupply() + healthSupplyBoost);
                        actionManager.getEventEngine().setRepeatHandling(true);
                        log.info("Used regeneration");
                    }
                }
            }

            @Override
            public final String getName() {
                return "Evolution";
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