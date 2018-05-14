package management.service.engine.services;

import heroes.abstractHero.hero.Hero;
import management.service.components.handleComponent.EngineComponent;

public interface RegularEngineService {

    EngineComponent installSingletonEngineComponent(Hero hero);

    EngineComponent getSingletonEngineComponent();
}
