package bonus.devourerBonuses.bonuses.attack.growTentacle;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.playerManagement.Player;

import java.util.logging.Logger;

final class CutTentacle extends Bonus{

    private static final Logger log = Logger.getLogger(CutTentacle.class.getName());

    private static final int ATTACK_FIX = 4;

    private Player usedGrowTentaclePlayer;

    public CutTentacle(String name, int id, ImageView sprite, final Player usedGrowTentaclePlayer) {
        super(name, id, sprite);
        this.usedGrowTentaclePlayer = usedGrowTentaclePlayer;
    }

    @Override
    public void use() {
        final Hero hero = usedGrowTentaclePlayer.getCurrentHero();
        hero.setAttack(hero.getAttack() - ATTACK_FIX);
        log.info("-4 BEFORE_ATTACK");
    }
}
