package management.playerManagement;

import annotations.sourceAnnotations.Transcendental;
import heroes.abstractHero.abilities.bonus.Bonus;
import gui.service.locations.ALocation;
import management.profileManagement.Profile;
import org.jetbrains.annotations.Contract;
import scala.Tuple3;

import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final Profile profile;

    private heroes.abstractHero.hero.Hero currentHero;

    private List<heroes.abstractHero.hero.Hero> allHeroes;

    private boolean hasAliveHeroes;
    //Total:
    private boolean isCurrent;
    private double dealDamage = 0;
    private double restoredHitPoints = 0;
    private int reachedLevel = 1;
    private int usedSkills = 0;
    private Bonus favouriteBonus;
    private int remainingTime;
    private Boolean winner = null;

    private ALocation location;

    public Player(final Profile profile, final heroes.abstractHero.hero.Hero currentHero) {
        this.profile = profile;
        this.currentHero = currentHero;
        this.hasAliveHeroes = true;
        this.allHeroes = new ArrayList<>() {{
            add(currentHero);
        }};
    }


    public Tuple3<Boolean, Player, heroes.abstractHero.hero.Hero> checkAliveHeroes() {
        for (final heroes.abstractHero.hero.Hero hero : this.allHeroes) {
            if (hero.isAlive()) {
                //Временно будет доставать первого попавшегося:
                //TODO: Панель выбора следующего героя после гибели предыдущего.
                this.currentHero = hero;
                return new Tuple3<>(true, this, hero);
            }
        }
        this.hasAliveHeroes = false;
        return new Tuple3<>(false, null, null);
    }


    @Contract(pure = true)
    public Profile getProfile() {
        return profile;
    }

    @Contract(pure = true)
    public heroes.abstractHero.hero.Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(heroes.abstractHero.hero.Hero currentHero) {
        this.currentHero = currentHero;
    }

    @Contract(pure = true)
    public double getDealDamage() {
        return dealDamage;
    }

    void setDealDamage(int dealDamage) {
        this.dealDamage = dealDamage;
    }

    @Contract(pure = true)
    public double getRestoredHitPoints() {
        return restoredHitPoints;
    }

    void setRestoredHitPoints(int restoredHitPoints) {
        this.restoredHitPoints = restoredHitPoints;
    }

    @Contract(pure = true)
    public int getReachedLevel() {
        return reachedLevel;
    }

    void setReachedLevel(byte reachedLevel) {
        this.reachedLevel = reachedLevel;
    }

    @Contract(pure = true)
    public int getUsedSkills() {
        return usedSkills;
    }

    void setUsedSkills(byte usedSkills) {
        this.usedSkills = usedSkills;
    }

    @Contract(pure = true)
    public Bonus getFavouriteBonus() {
        return favouriteBonus;
    }

    public void setFavouriteBonus(Bonus favouriteBonus) {
        this.favouriteBonus = favouriteBonus;
    }

    @Contract(pure = true)
    public int getRemainingTime() {
        return remainingTime;
    }

    void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Contract(pure = true)
    public boolean isWinner() {
        return winner;
    }

    void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    @Transcendental
    public final List<heroes.abstractHero.hero.Hero> getAllHeroes() {
        return this.allHeroes;
    }

    public final boolean hasAliveHeroes() {
        return hasAliveHeroes;
    }

    public final void setHasAliveHeroes(boolean hasAliveHeroes) {
        this.hasAliveHeroes = hasAliveHeroes;
    }
}