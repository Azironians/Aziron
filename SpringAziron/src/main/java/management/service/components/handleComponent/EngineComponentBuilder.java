package management.service.components.handleComponent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import heroes.abstractHero.hero.Hero;
import management.pipeline.APipeline;
import management.playerManagement.PlayerManager;

import java.lang.reflect.Constructor;

@Singleton
public final class EngineComponentBuilder {

    @Inject
    private APipeline pipeline;

    @Inject
    private PlayerManager playerManager;

    public final EngineComponent build(final String name, final Hero hero
            , final Class<? extends EngineComponent> clazz) {
        try {
            final Constructor<? extends EngineComponent> constructor = clazz.getDeclaredConstructor(String.class
                    , Hero.class, APipeline.class, PlayerManager.class);
            return constructor.newInstance(name, hero, this.pipeline, this.playerManager);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public final EngineComponent clone(final EngineComponent engineComponent) {
        return engineComponent.clone();
    }
}