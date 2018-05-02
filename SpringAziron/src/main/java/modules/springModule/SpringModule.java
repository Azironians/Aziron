package modules.springModule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class SpringModule extends AbstractModule {

    @Override
    protected final void configure() {
        final String contextPath = "spring/context/AzironMainContext.xml";
        bind(ClassPathXmlApplicationContext.class).annotatedWith(Names.named("SpringCore"))
                .toInstance(new ClassPathXmlApplicationContext(contextPath));
    }
}
