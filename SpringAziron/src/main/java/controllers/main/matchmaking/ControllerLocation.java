package controllers.main.matchmaking;

import com.google.inject.Inject;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.possibility.APossibility;
import javafx.scene.layout.Pane;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.actionManagement.actions.ActionType;
import management.pipeline.APipeline;
import management.playerManagement.ATeam;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

public abstract class ControllerLocation {

    @Inject
    protected PlayerManager playerManager;

    @Inject
    private APipeline pipeline;

    final void makeHeroRequest(final ATeam team) {
        final ATeam currentTeam = this.playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();
        if (team == currentTeam) {
            if (currentHero.isTreatmentAccess()) {
                this.pipeline.push(ActionEventFactory.getBeforeTreatment(currentHero));
            }
        } else {
            if (currentHero.isAttackAccess()) {
                this.pipeline.push(ActionEventFactory.getBeforeAttack(currentHero));
            }
        }
    }

    final void trySwapHeroRequest(final ATeam team) {
        final ATeam currentTeam = this.playerManager.getCurrentTeam();
        final Hero hero = team.getCurrentPlayer().getCurrentHero();
        if (team == currentTeam && hero.isSwapAccess()){
            this.getHeroCollectionPane().setVisible(true);
        }
    }

    public final void makePossibilityRequest(final ActionEvent actionEvent) {
        final Hero hero = actionEvent.getHero();
        final APossibility possibility = (APossibility) actionEvent.getData();
        final Hero currentHero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        if (currentHero == hero && possibility.isPossibilityAccess()){
            this.pipeline.push(actionEvent);
        }
    }

    abstract Pane getHeroCollectionPane();
}