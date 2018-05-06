package management.service.components.handleComponet;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.battleManagement.BattleManager;
import management.playerManagement.PlayerManager;

public interface EngineComponent {

    void handle(final ActionEvent actionEvent);

    String getName();

    Hero getCurrentHero();

    boolean isWorking();

    void setWorking(final boolean able) throws IllegalSwitchOffEngineComponentException;

    void setup(final ActionManager actionManager, final BattleManager battleManager, final PlayerManager playerManager
            , final Hero hero);
}