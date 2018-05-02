package spring;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Singleton
public final class SpringCore {

    @Inject
    @Named("SpringCore")
    private ClassPathXmlApplicationContext context;

    public final ClassPathXmlApplicationContext getContext(){
        return this.context;
    }
}
