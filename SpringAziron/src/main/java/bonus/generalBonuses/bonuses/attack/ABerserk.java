package bonus.generalBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEventFactory;
import management.playerManagement.Player;


import java.util.Arrays;
import java.util.logging.Logger;

public final class ABerserk extends Bonus {

    private static final Logger log = Logger.getLogger(ABerserk.class.getName());

    public ABerserk(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final Hero opponentHero = opponentPlayer.getCurrentHero();
        opponentHero.getDamage(currentHero.getAttack());
        log.info("DEAL " + currentHero.getAttack() + " DAMAGE TO OPPONENT PLAYER");
        currentHero.getDamage(opponentHero.getAttack());
        log.info("DEAL " + opponentHero.getAttack() + " DAMAGE TO CURRENT PLAYER");
        this.actionEvents.addAll(Arrays.asList
                (ActionEventFactory.getAfterDealDamage(currentHero, opponentHero, currentHero.getAttack())
                        , ActionEventFactory.getAfterDealDamage(opponentHero, currentHero, opponentHero.getAttack())));
    }
}