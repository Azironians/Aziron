package bonus.generalBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import management.service.components.handleComponent.EngineComponent;
import management.service.components.handleComponent.IllegalSwitchOffEngineComponentException;
import management.service.engine.services.RegularEngineService;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.service.engine.EventEngine;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class XFeedBack extends Bonus implements RegularEngineService {

    private static final Logger log = Logger.getLogger(XFeedBack.class.getName());

    private static final double SKILL_HEALING_COEFFICIENT = 0.5;

    private static final double SKILL_EXPERIENCE_COEFFICIENT = 0.4;

    public XFeedBack(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private double lastDamage;

    @Override
    public final void use() {
        final heroes.abstractHero.hero.Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final heroes.abstractHero.hero.Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();

        final double HEALING_BOOST = lastDamage * SKILL_HEALING_COEFFICIENT;
        final double OPPONENT_EXPERIENCE_BOOST = lastDamage * SKILL_EXPERIENCE_COEFFICIENT;
        log.info("LAST_DAMAGE: " + lastDamage);
        log.info("HEALING_BOOST: " + HEALING_BOOST);
        log.info("OPPONENT_XP_BOOST: " + OPPONENT_EXPERIENCE_BOOST);

        final EventEngine engine = actionManager.getEventEngine();
        if (currentHero.getHealing(HEALING_BOOST) | opponentHero.addExperience(OPPONENT_EXPERIENCE_BOOST)) {
            log.info("FEEDBACK WAS SUCCESSFUL");
            engine.handle();
        }
    }

    @Override
    public final EngineComponent installSingletonEngineComponent(final Player hero) {
        return new EngineComponent() {

            private Player player;

            private double hitPoints;

            @Override
            public final void setup() {
                this.player = hero;
                this.hitPoints = player.getCurrentHero().getHitPoints();
            }

            @Override
            public synchronized final void handle(final ActionEvent actionEvent) {
//                log.info("FEEDBACK HANDLE");
                final double newHitPoints = player.getCurrentHero().getHitPoints();
                final double comparison = hitPoints - newHitPoints;
//                if (player == playerManager.getCurrentTeam().getCurrentHero()){
//                    log.info("PLAYER: " + player.getProfile().getName());
//                    log.info("LAST_DAMAGE: " + lastDamage);
//                    log.info("OLD HP: " + hitPoints);
//                    log.info("NEW HP: " + newHitPoints);
//                }
                if (comparison > 0) {
                    lastDamage = comparison;
                    log.info("FEEDBACK: WAS DAMAGE: " + lastDamage + "FOR PLAYER: " + player.getProfile().getName());
                }
                this.hitPoints = newHitPoints;
            }

            @Override
            public final String getName() {
                return "FeedBack";
            }

            @Override
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean isWorking)
                    throws IllegalSwitchOffEngineComponentException {
                throw new IllegalSwitchOffEngineComponentException("FeedBack handler " +
                        "component always must work in EventEngine");
            }
        };
    }
}