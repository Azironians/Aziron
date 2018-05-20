package controllers.main.matchmaking;

import com.google.inject.Inject;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.abilities.Ability;
import javafx.scene.layout.Pane;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.pipeline.APipeline;
import management.playerManagement.Team;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

public abstract class ControllerLocation {

    @Inject
    protected PlayerManager playerManager;

    @Inject
    private APipeline pipeline;

    final void makeHeroRequest(final Team team) {
        final Team currentTeam = this.playerManager.getCurrentTeam();
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

    final void trySwapHeroRequest(final Team team) {
        final Team currentTeam = this.playerManager.getCurrentTeam();
        final Hero hero = team.getCurrentPlayer().getCurrentHero();
        if (team == currentTeam && hero.isSwapAccess()){
            this.getHeroCollectionPane().setVisible(true);
        }
    }

    public final void makeAbilityRequest(final ActionEvent actionEvent) {
        final Hero hero = actionEvent.getHero();
        final Ability ability = (Ability) actionEvent.getData();
        final Hero currentHero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        if (currentHero == hero && ability.isAbilityAccess()){
            this.pipeline.push(actionEvent);
        }
    }

    abstract Pane getHeroCollectionPane();
}