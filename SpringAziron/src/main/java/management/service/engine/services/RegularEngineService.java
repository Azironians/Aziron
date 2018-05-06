package management.service.engine.services;

import management.service.components.handleComponet.EngineComponent;
import management.playerManagement.Player;

public interface RegularEngineService {

    EngineComponent installSingletonEngineComponent(Player player);

    EngineComponent getSingletonEngineComponent();
}
