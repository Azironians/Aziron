package management.service.engine.services;

import heroes.abstractHero.hero.Hero;
import management.service.components.handleComponet.EngineComponent;
import management.playerManagement.Player;

public interface RegularEngineService {

    EngineComponent installSingletonEngineComponent(Hero hero);

    EngineComponent getSingletonEngineComponent();
}
