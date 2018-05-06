package bonus.generalBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;

import java.util.logging.Logger;

public final class AAxeUp extends Bonus {

    private static final Logger log = Logger.getLogger(AAxeUp.class.getName());

    private static final int ATTACK_BOOST = 2;

    public AAxeUp(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        log.info("+2 BEFORE_ATTACK");
    }
}