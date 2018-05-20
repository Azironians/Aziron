package management.playerManagement;

import annotations.sourceAnnotations.Transcendental;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class Team {
    private static final Logger log = Logger.getLogger(Team.class.getName());

    private Player currentPlayer;

    private Player alternativePlayer;

    @Transcendental
    private List<Player> allPlayers;

    private int turn;

    private int time;

    private final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> time--));

    Team(){}

    Team(final Player currentPlayer, final Player alternativePlayer) {
        currentPlayer.setCurrent(true);
        alternativePlayer.setCurrent(false);
        this.currentPlayer = currentPlayer;
        this.alternativePlayer = alternativePlayer;
        this.allPlayers = new ArrayList<>(){{
            add(currentPlayer);
            if (alternativePlayer.hasAliveHeroes()){
                add(alternativePlayer);
            }
        }};
    }

    private boolean swapAccess = true;

    public final boolean swapPlayers(){
        if (swapAccess && currentPlayer.getCurrentHero().getHitPoints() > 0){
            eagerSwapPlayers();
            return true;
        }
        return false;
    }

    public final void eagerSwapPlayers(){
            final Player swapper = this.currentPlayer;
            this.currentPlayer = this.alternativePlayer;
            this.alternativePlayer = swapper;
            this.currentPlayer.setCurrent(true);
            this.alternativePlayer.setCurrent(false);
            log.info("Swap was successful");
    }

    public final void launchTimer(){
        timeline.play();
    }

    public final void pauseTimer(){
        timeline.pause();
    }

    @Contract(pure = true)
    public final Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Contract(pure = true)
    public final Player getAlternativePlayer() {
        return alternativePlayer;
    }

    @Contract(pure = true)
    public final int getTurn() {
        return turn;
    }

    public final void setTurn(final int turn) {
        this.turn = turn;
    }

    public int getTime() {
        return time;
    }

    public void changeTimeBy(final int time){
        this.time += time;
    }

    public void setTime(final int time) {
        this.time = time;
        this.timeline.setCycleCount(time);
    }

    public final Timeline getTimeline() {
        return timeline;
    }

    public final boolean isSwapAccess() {
        return swapAccess;
    }

    public final void setSwapAccess(boolean swapAccess) {
        this.swapAccess = swapAccess;
    }

    @Override
    public final String toString() {
        if (alternativePlayer != null){
            return "ATeam{" +
                    "currentPlayer=" + currentPlayer.getProfile().getName() +
                    ", alternativePlayer=" + alternativePlayer.getProfile().getName() +
                    ", turn=" + turn +
                    '}';
        } else {
            return "ATeam{" +
                    "currentPlayer=" + currentPlayer.getProfile().getName() +
                    ", turn=" + turn +
                    '}';
        }

    }

    public void setCurrentPlayer(Player currentPlayer) {
        currentPlayer.setCurrent(true);
        this.currentPlayer = currentPlayer;
    }

    public void setAlternativePlayer(Player alternativePlayer) {
        alternativePlayer.setCurrent(false);
        this.alternativePlayer = alternativePlayer;
    }

    @Transcendental
    public final List<Player> getAllPlayers() {
        return this.allPlayers;
    }
}