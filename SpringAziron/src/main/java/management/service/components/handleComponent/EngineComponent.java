package management.service.components.handleComponent;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.playerManagement.PlayerManager;

public interface EngineComponent {

    void handle(final ActionEvent actionEvent);

    String getName();

    Hero getCurrentHero();

    boolean isWorking();

    void setWorking(final boolean able) throws IllegalSwitchOffEngineComponentException;

    void setup(final PlayerManager playerManager, final Hero hero);

    boolean isNeedPass();
}