package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEventFactory;
import management.playerManagement.ATeam;
import management.playerManagement.Player;

public final class AVampBlast extends Bonus {

    private static final double DAMAGE = 100;

    private static final int TIME_LOSING = -100;

    public AVampBlast(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final ATeam team = playerManager.getCurrentTeam();
        final Player player = team.getCurrentPlayer();
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        team.changeTimeBy(TIME_LOSING);
        actionManager.getEventEngine().handle();
        if (opponentHero.getDamage(DAMAGE)) {
            actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(player, opponentHero, DAMAGE));
        }
    }
}
