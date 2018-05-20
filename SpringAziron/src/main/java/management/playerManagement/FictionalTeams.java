package management.playerManagement;

import annotations.bindingAnnotations.BonusService;
import annotations.sourceAnnotations.Transcendental;
import heroes.abstractHero.abilities.bonus.Bonus;
import bonus.bonuses.factory.BonusFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import heroes.abstractHero.builder.HeroBuilder;
import heroes.devourer.annotation.DevourerHeroService;
import heroes.lv.annotation.LVHeroService;
import heroes.orcBash.annotation.OrcBashHeroService;
import management.profileManagement.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Test factory imitates players in match.
 * Temporary test clazz.
 */

@Singleton
@Transcendental
final class FictionalTeams {

    @Inject
    @DevourerHeroService
    private HeroBuilder devourerBuilder;

    @Inject
    @LVHeroService
    private HeroBuilder lvBuilder;

    @Inject
    @OrcBashHeroService
    private HeroBuilder orcBashBuilder;

    @Inject
    @BonusService
    private Provider<BonusFactory> provider;

    private List<Bonus> getGeneralDeck(){
        return  new ArrayList<>(){{
            addAll(provider.get().getMapOfBonus().values());
        }};
    }

    @NotNull
    Team createLeft() {

        final Profile joysProfile = new Profile("Joe", 0, null, null, null
                , null, null, 0, null);
        final Profile goresProfile = new Profile("Gore", 0, null, null, null
                , null, null, 0, null);

        final heroes.abstractHero.hero.Hero joysDevourer = orcBashBuilder.buildHero();
        joysDevourer.putBonusCollection(getGeneralDeck());
        final heroes.abstractHero.hero.Hero goresOrcBash = orcBashBuilder.buildHero();
        goresOrcBash.putBonusCollection(getGeneralDeck());

        final Player joysPlayer = new Player(joysProfile, joysDevourer);
        final Player goresPlayer = new Player(goresProfile, goresOrcBash);

        return new Team(goresPlayer, joysPlayer);
    }

    @NotNull
    Team createRight() {
        final Profile mikesProfile = new Profile("Mike", 0, null, null, null
                , null
                , null, null, null);
        final Profile kevinProfile = new Profile("Kevin", 0, null, null, null
                , null
                , null, null, null);

        final heroes.abstractHero.hero.Hero mikesLordVampire = lvBuilder.buildHero();
        mikesLordVampire.putBonusCollection(getGeneralDeck());
        final heroes.abstractHero.hero.Hero kevinOrcBash = lvBuilder.buildHero();
        kevinOrcBash.putBonusCollection(getGeneralDeck());

        final Player mikesPlayer = new Player(mikesProfile, mikesLordVampire);
        final Player kevinPlayer = new Player(kevinProfile, kevinOrcBash);

        return new Team(mikesPlayer, kevinPlayer);
    }
}