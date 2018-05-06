package bonus.bonuses.service.annotations.implementations;

import bonus.bonuses.service.annotations.Engine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimelineEngine {

    Engine engine();
}
