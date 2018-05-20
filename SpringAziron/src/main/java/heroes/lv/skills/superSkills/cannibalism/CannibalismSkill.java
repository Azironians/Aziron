package heroes.lv.skills.superSkills.cannibalism;

import heroes.abstractHero.tallents.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import management.actionManagement.actions.ActionEventFactory;
import management.battleManagement.BattleManager;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

import java.util.List;

import static heroes.lv.skills.superSkills.cannibalism.CannibalismPropertySkill.*;

public final class CannibalismSkill extends AbstractSkill {

    public CannibalismSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(BattleManager battleManager, PlayerManager playerManager) {
        final double DAMAGE = getParent().getAttack() * coefficients.get(0);
        final double HEALING = getParent().getAttack() * coefficients.get(1);
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final heroes.abstractHero.hero.Hero currentHero = currentPlayer.getCurrentHero();
        final heroes.abstractHero.hero.Hero opponentHero = opponentPlayer.getCurrentHero();
        if (opponentHero.getDamage(DAMAGE)) {
            actionEvents.add(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero, DAMAGE));
        }
        currentHero.getHealing(HEALING);
    }

    @Override
    public final void showAnimation() {

    }
}
