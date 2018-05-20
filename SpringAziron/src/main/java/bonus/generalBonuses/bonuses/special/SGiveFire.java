package bonus.generalBonuses.bonuses.special;

import heroes.abstractHero.abilities.bonus.Bonus;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class SGiveFire extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(SGiveFire.class.getName());

    private static final double COEFFICIENT_BOOST = 0.2;

    public SGiveFire(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final heroes.abstractHero.hero.Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final List<Skill> skills = hero.getCollectionOfSkills();
        for (final Skill skill : skills) {
            final List<Double> coefficients = skill.getCoefficients();
            final List<Double> newCoefficients = new ArrayList<>();
            for (final double coefficient : coefficients) {
                newCoefficients.add(coefficient * (1 + COEFFICIENT_BOOST));
            }
            skill.setCoefficients(newCoefficients);
        }
        log.info("SKILL POWER INCREASED BY 20%");
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.END_TURN) {
                    final heroes.abstractHero.hero.Hero hero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
                    final List<Skill> skills = hero.getCollectionOfSkills();
                    for (final Skill skill : skills) {
                        final List<Double> coefficients = skill.getCoefficients();
                        final List<Double> newCoefficients = new ArrayList<>();
                        for (final double coefficient : coefficients) {
                            newCoefficients.add(coefficient / (1 + COEFFICIENT_BOOST));
                        }
                        skill.setCoefficients(newCoefficients);
                    }
                    isWorking = false;
                    log.info("SKILL POWER DECREASED BY 20%");
                }
            }

            @Override
            public final String getName() {
                return "GiveFire";
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
            public final void setWorking(final boolean isWorking) {
                this.isWorking = isWorking;
            }
        };
    }
}
