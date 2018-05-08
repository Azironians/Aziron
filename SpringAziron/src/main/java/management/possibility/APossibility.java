package management.possibility;

import heroes.abstractHero.hero.Hero;
import management.pipeline.APipeline;

public abstract class APossibility {

    protected Hero hero;

    protected APipeline pipeline;

    public final APossibility setHero(final Hero hero){
        this.hero = hero;
        return this;
    }

    public final APossibility setPipeline(final APipeline pipeline){
        this.pipeline = pipeline;
        return this;
    }
}