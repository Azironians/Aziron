package bonus.devourerBonuses.bonuses.experience;

import heroes.abstractHero.abilities.bonus.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Player;

public final class XVoidChannel extends Bonus implements DynamicEngineService {

    private static final int EXPERIENCE_BOOST = 1;

    public XVoidChannel(final String name, final int id, final ImageView sprite) {
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
                if (actionEvent.getActionType() == ActionType.AFTER_DEAL_DAMAGE && actionEvent.getHero() == player){
                    final heroes.abstractHero.hero.Hero hero = player.getCurrentHero();
                    hero.addExperience(EXPERIENCE_BOOST);
                    actionManager.getEventEngine().setRepeatHandling(true);
                }
            }

            @Override
            public final String getName() {
                return "VoidChannel";
            }

            @Override
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean isWorking) {
                this.isWorking = isWorking;
            }
        };
    }
}