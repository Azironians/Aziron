package management.heroManagement;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import heroes.abstractHero.abilities.Ability;
import heroes.abstractHero.abilities.AbilityInstallationException;
import heroes.abstractHero.hero.Hero;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;
import management.playerManagement.Team;

import java.util.List;

@Singleton
public final class HeroManager {

    @Inject
    private PlayerManager playerManager;

    public final void wireManagersInAllAbilities() {
        for (final Team team : this.playerManager.getAllTeams()) {
            for (final Player player : team.getAllPlayers()) {
                for (final Hero hero : player.getAllHeroes()) {
                    try {
                        this.wireManagers(hero.getCollectionOfSkills(), hero);
                        this.wireManagers(hero.getBonusCollection(), hero);
                        this.wireManagers(hero.getSwapAbilities(), hero);
                    } catch (final AbilityInstallationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void wireManagers(List<? extends Ability> abilities, final Hero hero) throws AbilityInstallationException {
        for (final Ability ability : abilities) {
            ability.installParentHero(hero);
            ability.installPlayerManager(this.playerManager);
        }
    }
}