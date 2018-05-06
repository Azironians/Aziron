package bonus.lvBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponet.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Player;

public final class HVampirism extends Bonus implements DynamicEngineService {

    private static final double HEALING_BOOST = 5;

    public HVampirism(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getPrototypeEngineComponent());
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
                if (actionEvent.getActionType() == ActionType.BEFORE_ATTACK && actionEvent.getHero() == player) {
                    final Hero hero = player.getCurrentHero();
                    if (hero.getHealing(HEALING_BOOST)) {
                        actionManager.getEventEngine().setRepeatHandling(true);
                    }
                }
            }

            @Override
            public final String getName() {
                return "Vampirism";
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
            public void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}