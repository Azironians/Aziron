package management.actionManagement.actions;

import bonus.bonuses.Bonus;
import com.google.inject.Singleton;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.possibility.APossibility;
import heroes.abstractHero.skills.ASkill;
import heroes.abstractHero.skills.ASwapSkill;
import javafx.util.Pair;
import management.playerManagement.ATeam;
import management.playerManagement.Player;
import org.jetbrains.annotations.Nullable;

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
        for (final Hero hero : player.getAllHeroes()){
            events.add(new ActionEvent(actionType, hero));
        }
        return events;
    }

    public static ActionEvent getSkipTurn(final Hero hero) {
        return new ActionEvent(ActionType.SKIP_TURN, hero);
    }

    public static ActionEvent getHeroSwap(final Hero hero) {
        return new ActionEvent(ActionType.SWAP_HEROES, hero);
    }

    public static ActionEvent getHeroOut(final Hero hero) {
        return new ActionEvent(ActionType.PLAYER_OUT, hero);
    }

    public static ActionEvent getEndGame(final Hero hero) {
        return new ActionEvent(ActionType.END_GAME, hero);
    }

    public static ActionEvent getFrameDurationEvent(final Hero hero) {
        return new ActionEvent(ActionType.GET_FRAME, hero);
    }

    public static ActionEvent getBeforeTreatment(final Hero hero) {
        return new ActionEvent(ActionType.BEFORE_TREATMENT, hero);
    }

    public static ActionEvent getAfterTreatment(final Hero hero){
        return new ActionEvent(ActionType.AFTER_TREATMENT, hero);
    }

    public static ActionEvent getBeforeAttack(final Hero hero) {
        return new ActionEvent(ActionType.BEFORE_ATTACK, hero);
    }

    public static ActionEvent getAfterAttack(final Hero hero){
        return new ActionEvent(ActionType.AFTER_ATTACK, hero);
    }

    /**
     * @param attacker who made damage;
     * @param victim has special format about who was damaged: "damage playerID heroID";
     * @param damage is understand;
     * @return event.
     */

    public static ActionEvent getBeforeDealDamage(final Hero attacker, final Hero victim, final double damage) {
        return new ActionEvent(ActionType.BEFORE_DEAL_DAMAGE, attacker, new Pair<>(victim, damage));
    }

    public static ActionEvent getAfterDealDamage(final Hero attacker, final Hero victim, final double damage) {
        return new ActionEvent(ActionType.AFTER_DEAL_DAMAGE, attacker, new Pair<>(victim, damage));
    }

    public static ActionEvent getBeforeHealing(final Hero hero, final double healing){
        return new ActionEvent(ActionType.BEFORE_HEALING, hero);
    }

    public static ActionEvent getAfterHealing(final Hero hero, final double healing){
        return new ActionEvent(ActionType.AFTER_HEALING, hero);
    }

    public static ActionEvent getBeforeUsedSkill(final Hero hero, final ASkill skill) {
        return new ActionEvent(ActionType.BEFORE_USED_SKILL, hero, skill);
    }

    public static ActionEvent getAfterUsedSkill(final Hero hero, final ASkill skill){
        return new ActionEvent(ActionType.BEFORE_USED_SKILL, hero, skill);
    }

    public static ActionEvent getAfterUsedBonus(final Hero hero, final Bonus bonus) {
        return new ActionEvent(ActionType.AFTER_USED_BONUS, hero, bonus);
    }

    public static ActionEvent getBeforeUsedPossibility(final Hero hero, final APossibility possibility) {
        return new ActionEvent(ActionType.BEFORE_USED_POSSIBILITY, hero, possibility);
    }

    public static ActionEvent getAfterUsedPossbility(final Hero hero, final APossibility possibility){
        return new ActionEvent(ActionType.AFTER_USED_POSSIBILITY, hero, possibility);
    }

    public static ActionEvent getBeforeUsedSwapSkill(final Hero hero, ASwapSkill swapSkill) {
        return new ActionEvent(ActionType.BEFORE_USED_SWAP_SKILL, hero, swapSkill);
    }

    public static ActionEvent getAfterUsedSwapSkill(final Hero hero, ASwapSkill swapSkill){
        return new ActionEvent(ActionType.AFTER_USED_SWAP_SKILL, hero, swapSkill);
    }

    public static ActionEvent getNullableEvent() {
        return new ActionEvent(null, null, null);
    }

    public static ActionEvent getBeforeGettingExperience(final Hero hero, final double experience) {
        return new ActionEvent(ActionType.BEFORE_GETTING_EXPERIENCE, hero, experience);
    }

    public static ActionEvent getAfterGettingExperience(final Hero hero, final double experience) {
        return new ActionEvent(ActionType.AFTER_GETTING_EXPERIENCE, hero, experience);
    }
}