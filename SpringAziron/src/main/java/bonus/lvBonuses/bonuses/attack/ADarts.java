package bonus.lvBonuses.bonuses.attack;

import heroes.abstractHero.abilities.bonus.Bonus;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponent.EngineComponent;
import management.service.components.handleComponent.IllegalSwitchOffEngineComponentException;
import management.service.engine.services.RegularEngineService;
import management.playerManagement.Player;

public final class ADarts extends Bonus implements RegularEngineService {

    private double allDamage;

    private Player thisPlayer;

    public ADarts(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final heroes.abstractHero.hero.Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        if (opponentHero.getDamage(allDamage)) {
            actionManager.getEventEngine().handle(ActionEventFactory.getAfterDealDamage(thisPlayer, opponentHero
                    , allDamage));
        }
    }

    @Override
    public final EngineComponent installSingletonEngineComponent(final Player hero) {
        return new EngineComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = hero;
                thisPlayer = hero;
                allDamage = 0;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.AFTER_DEAL_DAMAGE && actionEvent.getHero() == currentPlayer) {
                    final Pair<heroes.abstractHero.hero.Hero, Double> heroVsDamage = (Pair) actionEvent.getData();
                    final double damage = heroVsDamage.getValue();
                    allDamage += damage;
                }
            }

            @Override
            public final String getName() {
                return "Darts";
            }

            @Override
            public final Player getCurrentHero() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean isWorking) throws IllegalSwitchOffEngineComponentException {
                throw new IllegalSwitchOffEngineComponentException();
            }
        };
    }
}