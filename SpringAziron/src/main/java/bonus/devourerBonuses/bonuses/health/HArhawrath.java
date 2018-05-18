package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;
import management.playerManagement.ATeam;
import management.playerManagement.Player;

public final class HArhawrath extends Bonus implements DynamicEngineService {

    public HArhawrath(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        actionManager.getEventEngine().addHandler(getPrototypeEngineComponent());
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new EngineComponent() {

            private ATeam team;

            private Player player;

            private boolean isWorking;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.team = playerManager.getOpponentTeam();
            }

            @Override
            public final void handle(ActionEvent actionEvent) {
                if (actionEvent.getActionType() == ActionType.BEFORE_ATTACK
                        && (actionEvent.getHero() == getCurrentHero()
                        || actionEvent.getHero() == team.getAlternativePlayer())
                        && player == playerManager.getOpponentTeam().getCurrentPlayer()){
                    for (final Skill skill : player.getCurrentHero().getCollectionOfSkills()){
                        if (skill.getName().equals("FlameSnakes")){
                            skill.setTemp(skill.getReload());
                        }
                    }
                }
                if (actionEvent.getActionType() == ActionType.END_TURN
                        && (actionEvent.getHero() == getCurrentHero()
                        || actionEvent.getHero() == team.getAlternativePlayer())){
                    this.isWorking = false;
                }
            }

            @Override
            public final String getName() {
                return "Arhawrath";
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