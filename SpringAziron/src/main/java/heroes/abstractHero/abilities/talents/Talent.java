package heroes.abstractHero.abilities.talents;

import annotations.sourceAnnotations.NonFinal;
import heroes.abstractHero.abilities.Ability;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;
import java.util.logging.Logger;

public abstract class Talent extends Ability {

    private static final Logger log = Logger.getLogger(Talent.class.getName());

    //Main characteristics:
    private int START_STEP = 1;

    protected int step = 1;

    protected int reload;

    protected int requiredLevel;

    protected List<Double> coefficients;

    protected Talent(final String name, final int reload, final int requiredLevel, final List<Double> coefficients
            , final ImageView mainImage, final ImageView descriptionImage, final List<Media> voices
            , final Media animationSound) {
        super(name, mainImage, descriptionImage, voices, animationSound);
        this.reload = reload;
        this.requiredLevel = requiredLevel;
        this.coefficients = coefficients;
    }

    @NonFinal
    public void reload() {
        if (this.parentHero.getLevel() >= this.requiredLevel) {
            this.step++;
        } else {
            this.step = START_STEP;
        }
    }

    @NonFinal
    public void reset() {
        this.step %= this.reload;
        this.guiHolder.getMainImage().setVisible(false);
    }

    public final int getStep() {
        return this.step;
    }

    public final void setStep(final int step) {
        this.step = step;
    }

    public final int getReload() {
        return reload;
    }

    public final void setReload(final int reload) {
        this.reload = reload;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final void setRequiredLevel(final int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public final List<Double> getCoefficients() {
        return this.coefficients;
    }
}