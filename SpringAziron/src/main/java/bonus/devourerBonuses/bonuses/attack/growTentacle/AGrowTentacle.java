package bonus.devourerBonuses.bonuses.attack.growTentacle;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.actionManagement.service.components.handleComponet.HandleComponent;
import management.actionManagement.service.components.providerComponent.ProviderComponent;
import management.actionManagement.service.engine.services.DynamicHandleService;
import heroes.abstractHero.bonusManagement.BonusManager;
import management.playerManagement.ATeam;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class AGrowTentacle extends Bonus implements DynamicHandleService {

    private static final Logger log = Logger.getLogger(AGrowTentacle.class.getName());

    private static final int ATTACK_BOOST = 4;

    private static int CUT_TENTACLE_ID;

    public AGrowTentacle(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        log.info("+4 BEFORE_ATTACK");
        actionManager.getEventEngine().addHandler(getHandlerInstance());
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private ATeam opponentTeam;

            private int index;

            private boolean isWorking;

            private ProviderComponent<Integer> previousProviderComponent;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.opponentTeam = playerManager.getOpponentTeam();
                this.isWorking = true;
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Player player = actionEvent.getPlayer();
                final Hero hero = actionEvent.getPlayer().getCurrentHero();
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (player == opponentTeam.getCurrentPlayer()
                        || player == opponentTeam.getAlternativePlayer())) {
                    final BonusManager bonusManager = hero.getBonusManager();
                    this.index = bonusManager.getAvailableProviderComponent();
                    this.previousProviderComponent = bonusManager.getProviderComponentList().get(index);
                    final ProviderComponent<Integer> customProviderComponent
                            = getCustomProviderComponent(previousProviderComponent.getPriority());
                    bonusManager.setCustomProviderComponent(index, customProviderComponent);
                }
                if ((actionEvent.getActionType() == ActionType.END_TURN
                        || actionEvent.getActionType() == ActionType.SKIP_TURN
                        || actionEvent.getActionType() == ActionType.AFTER_USED_BONUS)
                        && (actionEvent.getPlayer() == opponentTeam.getCurrentPlayer()
                        || actionEvent.getPlayer() == opponentTeam.getAlternativePlayer())) {
                    hero.getBonusManager().returnPreviousProviderComponent(index, previousProviderComponent);
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "GrowTentacle";
            }

            @Override
            public final Player getCurrentPlayer() {
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

            private ProviderComponent<Integer> getCustomProviderComponent(final int inputPriority) {
                return new ProviderComponent<>() {

                    private int priority = inputPriority;

                    @Override
                    public final Integer getValue() {
                        return CUT_TENTACLE_ID;
                    }

                    @Override
                    public int getPriority() {
                        return priority;
                    }

                    @Override
                    public void setPriority(final int priority) {
                        this.priority = priority;
                    }
                };
            }
        };
    }
}