package heroes.devourer.builder;

import com.google.inject.Inject;
import heroes.abstractHero.builder.HeroBuilder;
import heroes.abstractHero.resourceSupplier.HeroResourceSupplier;
import heroes.abstractHero.abilities.talents.factory.TalentFactory;
import heroes.devourer.annotation.DevourerHeroService;
import heroes.devourer.hero.Devourer;

public final class DevourerBuilder implements HeroBuilder {

    @Inject
    @DevourerHeroService
    private TalentFactory skillFactory;

    @Inject
    @DevourerHeroService
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
        return Devourer.class;
    }
}
