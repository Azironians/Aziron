package bonus.devourerBonuses.bonuses.health;

import heroes.abstractHero.abilities.bonus.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

import java.util.logging.Logger;

public final class HBurningPotion extends Bonus{

    private static final Logger log = Logger.getLogger(HBurningPotion.class.getName());

    private static final int DAMAGE = 100;

    private static final int HEALING = 120;

    public HBurningPotion(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer()
                .getCurrentHero();
        if (currentHero.getDamage(DAMAGE)){
            actionManager.getEventEngine().setRepeatHandling(true);
            log.info("-100 damage");
        }
        if (currentHero.getHealing(HEALING)){
            actionManager.getEventEngine().setRepeatHandling(true);
            log.info("+120 damage");
        }
    }
}
