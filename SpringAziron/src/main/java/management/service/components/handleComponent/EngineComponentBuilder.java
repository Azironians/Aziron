package management.service.components.handleComponent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import heroes.abstractHero.hero.Hero;
import management.pipeline.APipeline;
import management.playerManagement.PlayerManager;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

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
        final Class<? extends EngineComponent> clazz = engineComponent.getClass();
        try {
            final EngineComponent newEngineComponent = clazz.getDeclaredConstructor(clazz).newInstance(engineComponent);
            final Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                final String fieldName = field.getName();
                if (!fieldName.equals("playerManager") && !fieldName.equals("pipeline") && !fieldName.equals("hero")
                        && !fieldName.equals("name")) {
                    ReflectionUtils.makeAccessible(field);
                    final Object value = ReflectionUtils.getField(field, engineComponent);
                    ReflectionUtils.setField(field, newEngineComponent, value);
                }
            }
            return newEngineComponent;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}