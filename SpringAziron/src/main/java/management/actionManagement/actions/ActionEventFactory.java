package management.actionManagement.actions;

import bonus.bonuses.Bonus;
import com.google.inject.Singleton;
import javafx.util.Pair;
import management.playerManagement.ATeam;
import management.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

@Singleton
public final class ActionEventFactory {

    public static List<ActionEvent> getStartTurn(final ATeam team) {
        return getEventsForEachPlayer(team, ActionType.START_TURN);
    }

    public static List<ActionEvent> getEndTurn(final ATeam team) {
        return getEventsForEachPlayer(team, ActionType.END_TURN);
    }

    private static List<ActionEvent> getEventsForEachPlayer(final ATeam team, final ActionType actionType){
        final List<ActionEvent> events = new ArrayList<>();
        for (final Player player : team.getAllPlayers()){
            events.addAll(getEventsForEachHero(player, actionType));
        }
        return events;
    }

    private static List<ActionEvent> getEventsForEachHero(final Player player, final ActionType actionType){
        final List<ActionEvent> events = new ArrayList<>();
        for (final heroes.abstractHero.hero.Hero hero : player.getAllHeroes()){
            events.add(new ActionEvent(actionType, hero));
        }
        return events;
    }

    public static ActionEvent getSkipTurn(final heroes.abstractHero.hero.Hero hero) {
        return new ActionEvent(ActionType.SKIP_TURN, hero);
    }

    public static ActionEvent getHeroSwap(final heroes.abstractHero.hero.Hero hero) {
        return new ActionEvent(ActionType.SWAP_HEROES, hero);
    }

    public static ActionEvent getHeroOut(final heroes.abstractHero.hero.Hero hero) {
        return new ActionEvent(ActionType.PLAYER_OUT, hero);
    }

    public static ActionEvent getEndGame(final heroes.abstractHero.hero.Hero hero) {
        return new ActionEvent(ActionType.END_GAME, hero);
    }

    public static ActionEvent getFrameDurationEvent(final heroes.abstractHero.hero.Hero hero) {
        return new ActionEvent(ActionType.GET_FRAME, hero);
    }

    public static ActionEvent getBeforeTreatment(final heroes.abstractHero.hero.Hero hero) {
        return new ActionEvent(ActionType.BEFORE_TREATMENT, hero);
    }

    public static ActionEvent getAfterTreatment(final heroes.abstractHero.hero.Hero hero){
        return new ActionEvent(ActionType.AFTER_TREATMENT, hero);
    }

    public static ActionEvent getBeforeAttack(final heroes.abstractHero.hero.Hero hero) {
        return new ActionEvent(ActionType.BEFORE_ATTACK, hero);
    }

    public static ActionEvent getAfterAttack(final heroes.abstractHero.hero.Hero hero){
        return new ActionEvent(ActionType.AFTER_ATTACK, hero);
    }

    /**
     * @param attacker who made damage;
     * @param victim has special format about who was damaged: "damage playerID heroID";
     * @param damage is understand;
     * @return event.
     */

    public static ActionEvent getBeforeDealDamage(final heroes.abstractHero.hero.Hero attacker, final heroes.abstractHero.hero.Hero victim, final double damage) {
        return new ActionEvent(ActionType.BEFORE_DEAL_DAMAGE, attacker, new Pair<>(victim, damage));
    }

    public static ActionEvent getAfterDealDamage(final heroes.abstractHero.hero.Hero attacker, final heroes.abstractHero.hero.Hero victim, final double damage) {
        return new ActionEvent(ActionType.AFTER_DEAL_DAMAGE, attacker, new Pair<>(victim, damage));
    }

    public static ActionEvent getBeforeHealing(final heroes.abstractHero.hero.Hero hero, final double healing){
        return new ActionEvent(ActionType.BEFORE_HEALING, hero);
    }

    public static ActionEvent getAfterHealing(final heroes.abstractHero.hero.Hero hero, final double healing){
        return new ActionEvent(ActionType.AFTER_HEALING, hero);
    }

    public static ActionEvent getBeforeUsedSkill(final heroes.abstractHero.hero.Hero hero, final String skillName) {
        return new ActionEvent(ActionType.BEFORE_USED_SKILL, hero, skillName);
    }

    public static ActionEvent getAfterUsedBonus(final heroes.abstractHero.hero.Hero hero, final Bonus bonus) {
        return new ActionEvent(ActionType.AFTER_USED_BONUS, hero, bonus);
    }
}