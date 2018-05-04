package spring;

import bonus.bonuses.Bonus;
import bonus.devourerBonuses.bonuses.special.SAvatarsCore;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;

public final class SpringManager {

    private final Spring springEngine;

    private String string;

    @Autowired
    public SpringManager(final Spring springEngine) {
        this.springEngine = springEngine;
    }

    public final String getString() {
        return this.string;
    }

    public final void setString(final String string) {
        this.string = string;
    }

    public final Spring getSpringEngine() {
        final Bonus bonus = new SAvatarsCore("", -1, new ImageView());
        System.out.println(bonus.toString());
        return springEngine;
    }
}