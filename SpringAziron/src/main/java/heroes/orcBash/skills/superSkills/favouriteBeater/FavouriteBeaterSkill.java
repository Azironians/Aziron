package heroes.orcBash.skills.superSkills.favouriteBeater;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import management.actionManagement.actions.ActionEventFactory;
import management.battleManagement.BattleManager;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

import java.util.List;
import java.util.logging.Logger;

import static heroes.orcBash.skills.superSkills.favouriteBeater.FavouriteBeaterPropertySkill.*;

public final class FavouriteBeaterSkill extends AbstractSkill {

    private static final Logger log = Logger.getLogger(FavouriteBeaterSkill.class.getName());

    public FavouriteBeaterSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final double damage = getParent().getAttack() * coefficients.get(0);
        final double fixHealthSupply = getParent().getAttack() * coefficients.get(1);
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final Hero opponentHero = opponentPlayer.getCurrentHero();
        if (opponentHero.getDamage(damage)) {
            actionEvents.add(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero, damage));
        }
        opponentHero.setHealthSupply(opponentHero.getHealthSupply() - fixHealthSupply);
    }

    @Override
    public final void showAnimation() {

    }
}
