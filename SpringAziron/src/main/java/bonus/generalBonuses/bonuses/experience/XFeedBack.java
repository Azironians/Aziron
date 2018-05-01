package bonus.generalBonuses.bonuses.experience;

import bonus.bonuses.Bonus;
import management.actionManagement.service.components.handleComponet.HandleComponent;
import management.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import management.actionManagement.service.engine.services.RegularHandleService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.service.engine.EventEngine;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class XFeedBack extends Bonus implements RegularHandleService {

    private static final Logger log = Logger.getLogger(XFeedBack.class.getName());

    private static final double SKILL_HEALING_COEFFICIENT = 0.5;

    private static final double SKILL_EXPERIENCE_COEFFICIENT = 0.4;

    public XFeedBack(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private double lastDamage;

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();

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
    public final HandleComponent getRegularHandlerInstance(final Player inputPlayer) {
        return new HandleComponent() {

            private Player player;

            private double hitPoints;

            @Override
            public final void setup() {
                this.player = inputPlayer;
                this.hitPoints = player.getCurrentHero().getHitPoints();
            }

            @Override
            public synchronized final void handle(final ActionEvent actionEvent) {
//                log.info("FEEDBACK HANDLE");
                final double newHitPoints = player.getCurrentHero().getHitPoints();
                final double comparison = hitPoints - newHitPoints;
//                if (player == playerManager.getCurrentTeam().getCurrentPlayer()){
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
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean able)
                    throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException("FeedBack handler " +
                        "component always must work in EventEngine");
            }
        };
    }
}