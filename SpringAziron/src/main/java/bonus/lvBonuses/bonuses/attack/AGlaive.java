package bonus.lvBonuses.bonuses.attack;

import bonus.bonuses.Bonus;
import management.bonusManagement.BonusManager;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponet.HandleComponent;
import management.service.components.handleComponet.IllegalSwitchOffHandleComponentException;
import management.service.components.providerComponent.ProviderComponent;
import management.service.engine.EventEngine;
import management.service.engine.services.RegularHandleService;
import management.playerManagement.Player;

import java.util.List;

public final class AGlaive extends Bonus implements RegularHandleService {

    private static final double DAMAGE_COEFFICIENT = 0.10;

    private Pair<Integer, ProviderComponent<Integer>> indexVsPreviousProviderComponent;

    private boolean hasCustomProviderComponent;

    public AGlaive(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.hasCustomProviderComponent = false;
    }

    @Override
    public final void use() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();
        final Hero opponentHero = playerManager.getOpponentTeam().getCurrentPlayer().getCurrentHero();
        final EventEngine eventEngine = actionManager.getEventEngine();
        final double damage = currentHero.getAttack() * DAMAGE_COEFFICIENT;
        eventEngine.handle(ActionEventFactory.getBeforeDealDamage(currentPlayer, opponentHero, damage));
        if (opponentHero.getDamage(damage)){
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero, damage));
        }
        if (!hasCustomProviderComponent){
            pushThisBonusAgain(currentHero);
        }
    }

    private void pushThisBonusAgain(final Hero hero){
        final BonusManager bonusManager = hero.getBonusManager();
        final int index = bonusManager.getAvailableProviderComponent();
        final List<ProviderComponent<Integer>> providerComponentList = bonusManager.getProviderComponentList();
        final ProviderComponent<Integer> previousProviderComponent = providerComponentList.get(index);
        this.indexVsPreviousProviderComponent = new Pair<>(index, previousProviderComponent);
        bonusManager.setCustomProviderComponent(index, getCustomProviderComponent(previousProviderComponent
                .getPriority()));
        this.hasCustomProviderComponent = true;
    }

    private ProviderComponent<Integer> getCustomProviderComponent(final int priority){
        return new ProviderComponent<>() {

            private int currentPriority = priority;

            @Override
            public final Integer getValue() {
                return getId();
            }

            @Override
            public final int getPriority() {
                return currentPriority;
            }

            @Override
            public final void setPriority(final int priority) {
                this.currentPriority = priority;
            }
        };
    }

    @Override
    public final HandleComponent getRegularHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = player;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Hero hero = currentPlayer.getCurrentHero();
                if (actionEvent.getActionType() == ActionType.AFTER_USED_BONUS && actionEvent.getPlayer()
                        .getCurrentHero() == hero){
                    final Object data = actionEvent.getData();
                    if (data instanceof String){
                        final String message = (String) data;
                        if (!message.equals("Glaive")){
                            final BonusManager bonusManager = hero.getBonusManager();
                            final int index = indexVsPreviousProviderComponent.getKey();
                            final ProviderComponent<Integer> previousProviderComponent
                                    = indexVsPreviousProviderComponent.getValue();
                            bonusManager.returnPreviousProviderComponent(index, hero.getBonusCollection().size()
                                    , previousProviderComponent);
                            hasCustomProviderComponent = false;
                        }
                    }
                }
            }

            @Override
            public final String getName() {
                return "AGlaive";
            }

            @Override
            public final Player getCurrentPlayer() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException {
                throw new IllegalSwitchOffHandleComponentException();
            }
        };
    }
}