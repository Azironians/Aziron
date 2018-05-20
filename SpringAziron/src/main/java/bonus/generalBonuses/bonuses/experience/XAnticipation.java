package bonus.generalBonuses.bonuses.experience;

import heroes.abstractHero.abilities.bonus.Bonus;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class XAnticipation extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(XAnticipation.class.getName());

    private static final int EXPERIENCE_BOOST = 3;

    public XAnticipation(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
        log.info("ANTICIPATION IS ACTIVATED");
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private Player opponent;

            private Player alternativeOpponent;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponent = playerManager.getOpponentTeam().getCurrentPlayer();
                this.alternativeOpponent = playerManager.getOpponentTeam().getAlternativePlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Player victimPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
                final Player attackPlayer = actionEvent.getHero();

                if (actionEvent.getActionType() == ActionType.BEFORE_ATTACK && (attackPlayer == opponent
                        || attackPlayer == alternativeOpponent) && player == victimPlayer) {
                    final heroes.abstractHero.hero.Hero victimHero = player.getCurrentHero();
                    victimHero.addExperience(EXPERIENCE_BOOST);
                    log.info("+3 XP");
                }
            }

            @Override
            public final String getName() {
                return "Anticipation";
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
