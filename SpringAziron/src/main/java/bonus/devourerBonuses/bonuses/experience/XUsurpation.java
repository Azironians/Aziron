package bonus.devourerBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponent.EngineComponent;
import management.service.components.handleComponent.IllegalSwitchOffEngineComponentException;
import management.service.engine.services.RegularEngineService;
import management.playerManagement.Player;

public final class XUsurpation extends Bonus implements RegularEngineService {

    private static final double EXPERIENCE_CONSUMING = 5;

    private double experienceConsumingCoefficient = 0;

    public XUsurpation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final double experienceConsuming = EXPERIENCE_CONSUMING * experienceConsumingCoefficient;
        final heroes.abstractHero.hero.Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        if (opponentHero.removeExperience(experienceConsuming)){
            actionManager.getEventEngine().setRepeatHandling(true);
        }
    }

    @Override
    public final EngineComponent installSingletonEngineComponent(final Player hero) {
        return new EngineComponent() {

            private Player player;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.BEFORE_USED_SKILL && actionEvent.getHero() == player){
                    experienceConsumingCoefficient++;
                }
            }

            @Override
            public final String getName() {
                return "Usurpation";
            }

            @Override
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean isWorking) throws IllegalSwitchOffEngineComponentException {
                throw new IllegalSwitchOffEngineComponentException("Usurpation handler"
                        + "component always must work in EventEngine");
            }
        };
    }
}
