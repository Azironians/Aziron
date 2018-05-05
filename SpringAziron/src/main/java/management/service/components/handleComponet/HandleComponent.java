package management.service.components.handleComponet;

import management.actionManagement.actions.ActionEvent;
import management.playerManagement.Player;

public interface HandleComponent {

    void setup();

    void handle(final ActionEvent actionEvent);

    String getName();

    Player getCurrentPlayer();

    boolean isWorking();

    void setWorking(final boolean able) throws IllegalSwitchOffHandleComponentException;
}