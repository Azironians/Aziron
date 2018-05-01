package spring;

import org.springframework.beans.factory.annotation.Autowired;

public final class SpringManager {

    private final SpringEngine springEngine;

    private String string;

    @Autowired
    public SpringManager(final SpringEngine springEngine) {
        this.springEngine = springEngine;
    }

    public final String getString() {
        return this.string;
    }

    public final void setString(final String string) {
        this.string = string;
    }

    public final SpringEngine getSpringEngine() {
        return springEngine;
    }
}