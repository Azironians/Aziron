package heroes.lv.skills.superSkills.nightBlades;

import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import management.battleManagement.BattleManager;
import management.playerManagement.PlayerManager;

import java.util.List;
import java.util.logging.Logger;

import static heroes.lv.skills.superSkills.nightBlades.NightBladesPropertySkill.*;

public final class NightBladesSkill extends AbstractSkill {

    private static final Logger log = Logger.getLogger(NightBladesSkill.class.getName());

    public NightBladesSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, getSkillCoefficients()
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final double attackValue = parent.getAttack() * coefficients.get(0);
        parent.setAttack(attackValue);
    }

    @Override
    public final void showAnimation() {

    }
}
