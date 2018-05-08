package controllers.main.matchmaking;

import com.google.inject.Inject;
import heroes.abstractHero.hero.Hero;
import javafx.scene.layout.Pane;
import management.actionManagement.actions.ActionEventFactory;
import management.pipeline.APipeline;
import management.playerManagement.ATeam;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

abstract class ControllerLocation {

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

    final void makeChoiceSwapHeroRequest(final ATeam team) {
        final ATeam currentTeam = this.playerManager.getCurrentTeam();
        final Hero hero = team.getCurrentPlayer().getCurrentHero();
        if (team == currentTeam && hero.isSwapAccess()){
            this.getChoiceSwapHeroPane().setVisible(true);
        }
    }

    final void makeSwapHeroRequest(final ATeam team){

    }

    abstract Pane getChoiceSwapHeroPane();
}