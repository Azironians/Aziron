package bonus.generalBonuses.bonuses.special;

import bonus.bonuses.Bonus;
import management.service.components.handleComponet.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class SMagicTotem extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(SMagicTotem.class.getName());

    private static final double COEFFICIENT_BOOST = 0.1;

    public SMagicTotem(final String name, final int id, final ImageView sprite) {
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
        log.info("SKILL POWER INCREASED BY 10%");
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private Player player;

            private boolean isWorking = true;

            private boolean skillPassed = false;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (skillPassed){
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
                    log.info("SKILL POWER DECREASED BY 10%");
                }
                if (actionEvent.getActionType() == ActionType.BEFORE_USED_SKILL && actionEvent.getHero() == player) {
                    log.info("MAGIC TOTEM");
                    skillPassed = true;
                }
            }

            @Override
            public final String getName() {
                return "MagicTotem";
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