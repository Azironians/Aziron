package heroes.devourer.skills.superSkills.consuming;

import heroes.abstractHero.tallents.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.battleManagement.BattleManager;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

import java.util.List;

import static heroes.devourer.skills.superSkills.consuming.ConsumingPropertySkill.*;

public final class ConsumingSkill extends AbstractSkill {

    public ConsumingSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final heroes.abstractHero.hero.Hero opponentHero = opponentPlayer.getCurrentHero();
        final double damage = opponentHero.getHitPoints() / coefficients.get(0);
        if (opponentHero.getDamage(damage)) {
            actionEvents.add(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero, damage));
            actionEvents.add(new ActionEvent(null, currentPlayer
                    , "Consuming: " + damage));
        }
    }

    @Override
    public final void showAnimation() {

    }
}