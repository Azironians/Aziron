package modules.securityModules.gate;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import security.assistants.PropertyAssistant;
import security.gate.AGate;

public final class AGateModule extends AbstractModule{

    @Override
    protected void configure() {
        Names.bindProperties(binder(), PropertyAssistant
                .makeProperties("./src/main/java/security/gate/properties/messages.properties"));
        requestStaticInjection(AGate.RegisterService.class);
    }
}
