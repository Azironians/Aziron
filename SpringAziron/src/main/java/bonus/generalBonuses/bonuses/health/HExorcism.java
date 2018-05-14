package bonus.generalBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import bonus.bonuses.service.annotations.EngineField;
import bonus.bonuses.service.annotations.implementations.HealingEngine;
import bonus.bonuses.service.parameterType.ParameterType;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;

import java.util.logging.Logger;


public final class HExorcism extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(HExorcism.class.getName());

    public HExorcism(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final EngineComponent handler = getPrototypeEngineComponent();
        this.actionManager.getEventEngine().addHandler(handler);
    }

    @HealingEngine(engine = @EngineField(fieldName = "healingBoost", parameterType = ParameterType.VALUE))
    private final class ExorcismHandlerComponent implements EngineComponent {

        private static final double START_HEALING_BOOST = 2;

        private double healingBoost = START_HEALING_BOOST;

        private boolean isWorking = true;

        private Hero hero;

        @Override
        public final void setup() {
            this.hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        }

        @Override
        public final void handle(final ActionEvent actionEvent) {
            final ActionType actionType = actionEvent.getActionType();

            if (actionType == ActionType.END_TURN && this.hero == actionEvent.getHero()) {
                final Hero currentHero = this.hero;

                if (currentHero.getHealing(this.healingBoost)) {
                    log.info("+" + this.healingBoost + "HP");
                    actionManager.getEventEngine().setRepeatHandling(true);
                }
            }
        }

        @Override
        public final String getName() {
            return "Exorcism";
        }

        @Override
        public final Hero getCurrentHero() {
            return this.hero;
        }

        @Override
        public final boolean isWorking() {
            return this.isWorking;
        }

        @Override
        public final void setWorking(final boolean able) {
            this.isWorking = able;
        }
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new ExorcismHandlerComponent();
    }
}