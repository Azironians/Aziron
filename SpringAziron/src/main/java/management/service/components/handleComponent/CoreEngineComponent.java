package management.service.components.handleComponent;

import heroes.abstractHero.hero.Hero;
import management.pipeline.APipeline;
import management.playerManagement.PlayerManager;

import java.lang.reflect.Constructor;

public abstract class CoreEngineComponent extends EngineComponent{

    private boolean isWorking = true;

    public CoreEngineComponent(final String name, final Hero hero, final APipeline pipeline
            , final PlayerManager playerManager) {
        super(name, hero, pipeline, playerManager);
    }

    @Override
    protected final EngineComponent clone() {
        final Class<? extends CoreEngineComponent> clazz = this.getClass();
        try {
            final Constructor<? extends CoreEngineComponent> constructor = clazz.getDeclaredConstructor(String.class
                    , Hero.class, APipeline.class, PlayerManager.class);
            return constructor.newInstance(this.name, this.hero, this.pipeline, this.playerManager);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public final boolean isWorking() {
        return this.isWorking;
    }

    @Override
    public final void setWorking(final boolean isWorking) {
        this.isWorking = isWorking;
    }
}