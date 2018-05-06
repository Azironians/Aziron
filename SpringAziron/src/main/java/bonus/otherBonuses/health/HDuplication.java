package bonus.otherBonuses.health;

import bonus.bonuses.Bonus;
import bonus.bonuses.service.annotations.implementations.HealingEngine;
import heroes.abstractHero.hero.Hero;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import management.service.components.handleComponet.EngineComponent;
import management.service.engine.EventEngine;
import org.springframework.util.ReflectionUtils;

import java.util.HashSet;
import java.util.Set;

public final class HDuplication extends Bonus {

    public HDuplication(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final EventEngine eventEngine = this.actionManager.getEventEngine();
        final Set<EngineComponent> engineComponents = eventEngine.getHandlers();
        final Set<EngineComponent> newEngineComponents = new HashSet<>();
        for (final EngineComponent engineComponent : engineComponents){
            if (hero == engineComponent.getCurrentHero()){
                final Class<EngineComponent> engineComponentClass = (Class) engineComponent.getClass();
                if (engineComponentClass.isAnnotationPresent(HealingEngine.class)){
                    try {
                        final EngineComponent newEngineComponent = engineComponentClass.getDeclaredConstructor()
                                .newInstance();
                        newEngineComponent.setup(this.actionManager, this.battleManager, this.playerManager, hero);
                        newEngineComponents.add(newEngineComponent);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        engineComponents.addAll(newEngineComponents);
    }
}