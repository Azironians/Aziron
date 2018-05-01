package bonus.generalBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import management.playerManagement.Player;

import java.util.List;
import java.util.logging.Logger;

public final class XNerf extends Bonus {

    private static final Logger log = Logger.getLogger(XNerf.class.getName());

    private static final double BONUS_COEFFICIENT = 2.0;

    public XNerf(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player player = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = player.getCurrentHero();

        final double EXPERIENCE_BOOST = currentHero.getAttack() * BONUS_COEFFICIENT;

        final List<Skill> skills = currentHero.getCollectionOfSkills();
        for (final Skill skill : skills) {
            final boolean levelReached = currentHero.getLevel() >= skill.getRequiredLevel();
            if (skill.isReady() && levelReached) {
                if (currentHero.addExperience(EXPERIENCE_BOOST)) {
                    log.info("+" + EXPERIENCE_BOOST + " XP");
                    actionManager.getEventEngine().setRepeatHandling(true);
                }
                skill.reset();
            }
        }
        log.info("SKILL RESET WAS SUCCESSFUL");
    }
}
