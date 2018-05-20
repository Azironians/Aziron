package heroes.lv.builder;

import com.google.inject.Inject;
import heroes.abstractHero.builder.HeroBuilder;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.abilities.talents.factory.TalentFactory;
import heroes.lv.annotation.LVHeroService;
import heroes.lv.hero.LV;

public final class LVBuilder implements HeroBuilder {

    @Inject
    @LVHeroService
    private TalentFactory skillFactory;

    @Inject
    @LVHeroService
    private HeroResourceSupplier resourceSupplier;

    @Override
    public final TalentFactory getSkillFactory() {
        return skillFactory;
    }

    @Override
    public final HeroResourceSupplier getHeroResourceSupplier() {
        return resourceSupplier;
    }

    @Override
    public final Class getHeroClass() {
        return LV.class;
    }
}