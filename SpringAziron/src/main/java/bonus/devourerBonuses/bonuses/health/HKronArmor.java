package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import bonus.generalBonuses.bonuses.health.HStrengthenTheArmor;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponet.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class HKronArmor extends Bonus implements DynamicEngineService {

    public HKronArmor(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    private static final Logger log = Logger.getLogger(HStrengthenTheArmor.class.getName());

    private static final double HEALING = 120.0;

    private static final double BIG_DAMAGE = 150;

    @Override
    public final void use() {
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
        log.info("KRON ARMOR IS ACTIVATED");
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private Player alternativePlayer;

            private double hitPoints;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getCurrentHero().getHitPoints();
                this.alternativePlayer = playerManager.getCurrentTeam().getAlternativePlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Hero currentHero = player.getCurrentHero();
                final double hitPointsComparison = hitPoints - currentHero.getHitPoints();
                log.info("ARMOR HANDLE");
                final boolean isNecessaryHealing = hitPointsComparison > BIG_DAMAGE;
                if (isNecessaryHealing) {
                    log.info("COMPARISON: " + hitPointsComparison);
                    if (currentHero.getHealing(HEALING)){
                        actionManager.getEventEngine().setRepeatHandling(true);
                    }
                }
                this.hitPoints = currentHero.getHitPoints();
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (actionEvent.getHero() == player
                        || actionEvent.getHero() == alternativePlayer)) {
                    isWorking = false;
                    log.info("ARMOR DOWN");
                }
            }

            @Override
            public final String getName() {
                return "KronArmor";
            }

            @Override
            public final Player getCurrentHero() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setWorking(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}