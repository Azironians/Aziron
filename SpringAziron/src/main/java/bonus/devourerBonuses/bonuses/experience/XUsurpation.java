package bonus.devourerBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.actionManagement.service.components.handleComponet.HandleComponent;
import management.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import management.actionManagement.service.engine.services.RegularHandleService;
import management.playerManagement.Player;

public final class XUsurpation extends Bonus implements RegularHandleService {

    private static final double EXPERIENCE_CONSUMING = 5;

    private double experienceConsumingCoefficient = 0;

    public XUsurpation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final double experienceConsuming = EXPERIENCE_CONSUMING * experienceConsumingCoefficient;
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        if (opponentHero.removeExperience(experienceConsuming)){
            actionManager.getEventEngine().setRepeatHandling(true);
        }
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player player;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.BEFORE_USED_SKILL && actionEvent.getPlayer() == player){
                    experienceConsumingCoefficient++;
                }
            }

            @Override
            public final String getName() {
                return "Usurpation";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException("Usurpation handler"
                        + "component always must work in EventEngine");
            }
        };
    }
}
