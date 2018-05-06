package bonus.devourerBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEventFactory;
import management.playerManagement.Player;

public final class ASpark extends Bonus{

    private static final double EXPERIENCE_COEFFICIENT = 0.05;

    public ASpark(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final heroes.abstractHero.hero.Hero opponentHero = opponentPlayer.getCurrentHero();
        final double fixExperience = opponentHero.getCurrentExperience() * EXPERIENCE_COEFFICIENT;
        if (opponentHero.removeExperience(fixExperience)) {
            actionManager.getEventEngine().setRepeatHandling(true);
        }
        if (opponentHero.getDamage(fixExperience)){
            final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
            actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero
                    , fixExperience));
        }
    }
}
