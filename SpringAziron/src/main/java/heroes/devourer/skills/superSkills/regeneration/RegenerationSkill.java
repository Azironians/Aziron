package heroes.devourer.skills.superSkills.regeneration;

import management.service.components.handleComponent.EngineComponent;
import heroes.abstractHero.tallents.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.battleManagement.BattleManager;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

import java.util.List;
import java.util.logging.Logger;

import static heroes.devourer.skills.superSkills.regeneration.RegenerationPropertySkill.*;

public final class RegenerationSkill extends AbstractSkill {

    private static final Logger log = Logger.getLogger(RegenerationSkill.class.getName());

    public RegenerationSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        getEffect(currentPlayer, coefficients.get(0));
        actionManager.getEventEngine().addHandler(getHandlerInstance(currentPlayer));
        log.info("skill added");
    }

    private void getEffect(final Player currentPlayer, final double coefficient){
        final double HEALING = getParent().getTreatment() * coefficient;
        final heroes.abstractHero.hero.Hero currentHero = currentPlayer.getCurrentHero();
        if (currentHero.getHealing(HEALING)){
            final ActionEvent actionEvent = new ActionEvent(null
                    , currentPlayer, "Regeneration: " + HEALING);
            actionEvents.add(actionEvent);
        }
    }

    private EngineComponent getHandlerInstance(final Player currentPlayer){
        return new EngineComponent() { // FIXME: 14.02.2018 make skillEventEngine

            private Player player;

            private double coefficient;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = currentPlayer;
                this.coefficient = coefficients.get(0);
                log.info("Setup was successful");
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.START_TURN && actionEvent.getHero() == player){
                    getEffect(player, coefficient);
                    isWorking = false;
                    log.info("Second healing");
                }
            }

            @Override
            public final String getName() {
                return NAME;
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
            public final void setWorking(boolean isWorking) {
                this.isWorking = false;
            }
        };
    }

    @Override
    public final void showAnimation() {

    }
}
