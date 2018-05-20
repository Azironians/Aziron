package bonus.devourerBonuses.bonuses.health;

import heroes.abstractHero.abilities.bonus.Bonus;
import heroes.devourer.skills.superSkills.regeneration.utilities.RegenerationMessageParser;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class HEvolution extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(HEvolution.class.getName());

    private static final double BOOST_COEFFICIENT = 1.03;

    public HEvolution(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final EngineComponent engineComponent = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(engineComponent);
        log.info("Evolution is activated!");
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private boolean isWorking;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (player == actionEvent.getHero()) {
                    final heroes.abstractHero.hero.Hero hero = player.getCurrentHero();
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
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean isWorking) {
                this.isWorking = isWorking;
            }
        };
    }
}