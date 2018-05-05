package management.service.engine.services;

import management.service.components.handleComponet.HandleComponent;
import management.playerManagement.Player;

public interface RegularHandleService {

    HandleComponent getRegularHandlerInstance(Player player);

}
