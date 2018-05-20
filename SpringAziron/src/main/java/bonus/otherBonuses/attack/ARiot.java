package bonus.otherBonuses.attack;

import heroes.abstractHero.abilities.bonus.Bonus;
import bonus.bonuses.service.annotations.EngineField;
import bonus.bonuses.service.annotations.implementations.DamageEngine;
import bonus.bonuses.service.parameterType.ParameterType;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.EventEngine;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;

public final class ARiot extends Bonus {

    private static final ParameterType checkedParameterType = ParameterType.VALUE;

    private static final double DAMAGE_BOOST = 3.0;

    public ARiot(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final EventEngine eventEngine = this.actionManager.getEventEngine();
        final Set<EngineComponent> engineComponents = eventEngine.getHandlers();
        for (final EngineComponent engineComponent : engineComponents) {
            if (hero == engineComponent.getCurrentHero()){
                final Class engineComponentClass = engineComponent.getClass();
                if (engineComponentClass.isAnnotationPresent(DamageEngine.class)){
                    final DamageEngine damageEngineAnnotation = (DamageEngine) engineComponentClass
                            .getAnnotation(DamageEngine.class);
                    final EngineField engineFieldAnnotation = damageEngineAnnotation.engineField();
                    if (checkedParameterType == engineFieldAnnotation.parameterType()){
                        final String fieldName = engineFieldAnnotation.fieldName();
                        final Field field = ReflectionUtils.findField(engineComponentClass, fieldName);
                        ReflectionUtils.makeAccessible(field);
                        final double damage = (double) ReflectionUtils.getField(field, engineComponent);
                        ReflectionUtils.setField(field, engineComponent, damage + DAMAGE_BOOST);
                    }
                }
            }
        }
    }
}