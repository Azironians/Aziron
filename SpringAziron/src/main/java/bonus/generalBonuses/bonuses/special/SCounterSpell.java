package bonus.generalBonuses.bonuses.special;

import bonus.bonuses.Bonus;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.playerManagement.Player;

import java.util.List;
import java.util.logging.Logger;

public final class SCounterSpell extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(SCounterSpell.class.getName());

    public SCounterSpell(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Player opponentPlayer = playerManager.getOpponentTeam().getCurrentPlayer();
        final heroes.abstractHero.hero.Hero opponentHero = opponentPlayer.getCurrentHero();
        final List<Skill> opponentSkills = opponentHero.getCollectionOfSkills();

        for (final Skill skill : opponentSkills) {
            skill.setSkillAccess(false);
        }
        log.info("COUNTER SPELL IS ACTIVATED");
        final EngineComponent handler = getPrototypeEngineComponent();
        actionManager.getEventEngine().addHandler(handler);
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private boolean isWorking = true;

            private Player player;

            private Player opponent;

            private Player alternativeOpponent;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponent = playerManager.getOpponentTeam().getCurrentPlayer();
                this.alternativeOpponent = playerManager.getOpponentTeam().getAlternativePlayer();
            }

            @Override
            public final void handle(ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.END_TURN
                        && (actionEvent.getHero() == opponent
                        || (actionEvent.getHero() == alternativeOpponent
                        && alternativeOpponent != null))) {
                    final List<Skill> opponentSkills = opponent.getCurrentHero().getCollectionOfSkills();
                    for (final Skill skill : opponentSkills) {
                        skill.setSkillAccess(true);
                    }
                    isWorking = false;
                    log.info("COUNTER SPELL IS DEACTIVATED");
                }
            }

            @Override
            public final String getName() {
                return "CounterSpell";
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