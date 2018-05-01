package management.actionManagement.service.engine.services;

import management.actionManagement.service.components.handleComponet.HandleComponent;
import management.playerManagement.Player;

public interface RegularHandleService {

    HandleComponent getRegularHandlerInstance(Player player);

}
