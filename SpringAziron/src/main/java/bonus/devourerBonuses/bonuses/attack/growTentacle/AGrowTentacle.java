package bonus.devourerBonuses.bonuses.attack.growTentacle;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponent.EngineComponent;
import management.service.components.providerComponent.ProviderComponent;
import management.service.engine.services.DynamicEngineService;
import management.bonusManagement.BonusManager;
import management.playerManagement.ATeam;
import management.playerManagement.Player;

import java.util.logging.Logger;

public final class AGrowTentacle extends Bonus implements DynamicEngineService {

    private static final Logger log = Logger.getLogger(AGrowTentacle.class.getName());

    private static final int ATTACK_BOOST = 4;

    private static int CUT_TENTACLE_ID;

    public AGrowTentacle(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final heroes.abstractHero.hero.Hero currentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        currentHero.setAttack(currentHero.getAttack() + ATTACK_BOOST);
        log.info("+4 BEFORE_ATTACK");
        actionManager.getEventEngine().addHandler(getPrototypeEngineComponent());
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

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
                final Player player = actionEvent.getHero();
                final heroes.abstractHero.hero.Hero hero = actionEvent.getHero().getCurrentHero();
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
                        && (actionEvent.getHero() == opponentTeam.getCurrentPlayer()
                        || actionEvent.getHero() == opponentTeam.getAlternativePlayer())) {
                    hero.getBonusManager().returnPreviousProviderComponent(index, hero.getBonusCollection().size()
                            , this.previousProviderComponent);
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "GrowTentacle";
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