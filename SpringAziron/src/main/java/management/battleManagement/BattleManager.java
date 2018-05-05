package management.battleManagement;

import annotations.sourceAnnotations.Transcendental;
import bonus.bonuses.Bonus;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import gui.service.graphicEngine.GraphicEngine;
import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEventFactory;
import management.battleManagement.processors.BonusLoadingProcessor;
import management.processors.exceptions.UnsupportedProcessorException;
import management.service.components.providerComponent.ProviderComponent;
import management.service.engine.EventEngine;
import management.playerManagement.ATeam;
import management.playerManagement.GameMode;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;
import management.processors.Processor;

import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public final class BattleManager {

    private final static Logger log = Logger.getLogger(BattleManager.class.getName());

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ActionManager actionManager;

    @Inject
    private GraphicEngine graphicEngine;

    @Inject
    private EventEngine eventEngine;

    @Inject
    @Named("start time")
    private static int startTime;

    @Inject
    @Named("turn")
    private int turn;

    //End turn condition:
    @Inject
    @Named("end turn condition")
    private boolean endTurn;

    @Inject
    @Named("skip turn condition")
    private boolean skipTurn;

    private BonusLoadingProcessor bonusLoadingProcessor;

    public final void install(){
        this.bonusLoadingProcessor = new BonusLoadingProcessor(this.graphicEngine);
    }

    //Next turn:
    public final void nextTurn() {
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final boolean isDestroyed = currentPlayer.getCurrentHero().getHitPoints() <= 0;
        if (isDestroyed) {
            currentPlayer.setAlive(false);
            if (isEndGame()) {
                eventEngine.handle(ActionEventFactory.getEndGame(currentPlayer));
                log.info("GAME_OVER");
                endGame();
            } else {
                eventEngine.handle(ActionEventFactory.getPlayerOut(currentPlayer));
                makeEagerPlayerSwapRequest();
                log.info("PLAYER_OUT");
            }
        } else {
            changeTurn();
            if (skipTurn) {
                graphicEngine.hideBonuses();
                final Player newCurrentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
                eventEngine.handle(ActionEventFactory.getSkipTurn(newCurrentPlayer));
                skipTurn = false;
                nextTurn();
            }
        }
    }

    //Defines turn:
    private void changeTurn() {
        final ATeam left = playerManager.getLeftATeam();
        final ATeam right = playerManager.getRightATeam();
        //CHANGE: //////////////////////////////////////////////////////////
        turn = (turn + 1) % 2;
        if (turn == left.getTurn()) {
            playerManager.setCurrentATeam(left);
            playerManager.setOpponentATeam(right);
        }
        if (turn == right.getTurn()) {
            playerManager.setCurrentATeam(right);
            playerManager.setOpponentATeam(left);
        }
        ////////////////////////////////////////////////////////////////////
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final Player alternativePlayer = currentTeam.getAlternativePlayer();

        currentPlayer.getCurrentHero().reloadSkills();
        if (playerManager.getGameMode() == GameMode._2x2){
            alternativePlayer.getCurrentHero().reloadSkills();
            alternativePlayer.getCurrentHero().getSwapSkill().reload();
        }
        //handling:
        eventEngine.handle(ActionEventFactory.getStartTurn(currentPlayer));
        if (playerManager.getGameMode() == GameMode._2x2){
            eventEngine.handle(ActionEventFactory.getStartTurn(alternativePlayer));
        }
        this.bonusLoadingProcessor.setHero(currentPlayer.getCurrentHero());
        this.bonusLoadingProcessor.process();
    }

    private boolean isEndGame() {
        final Player alternativePlayer = playerManager.getCurrentTeam().getAlternativePlayer();
        return alternativePlayer == null || !alternativePlayer.isAlive();
    }

    public final void endGame() {

    }

    private void makeEagerPlayerSwapRequest() {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        actionManager.setEagerPlayerSwapRequest(currentTeam);
    }

    public static int getStartTime() {
        return startTime;
    }

    public final boolean isEndTurn() {
        return endTurn;
    }

    public final void setEndTurn(final boolean endTurn) {
        this.endTurn = endTurn;
    }

    public final void setSkipTurn(final boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public final BonusLoadingProcessor getProcessor() {
        return this.bonusLoadingProcessor;
    }

    public final void setProcessor(final Processor processor) throws UnsupportedProcessorException {
        if (processor instanceof BonusLoadingProcessor){
            this.bonusLoadingProcessor = (BonusLoadingProcessor) processor;
        } else {
            throw new UnsupportedProcessorException("Invalid bonus loadlin processor");
        }
    }

    public final void setDefaultProcessor() {
        this.bonusLoadingProcessor = new BonusLoadingProcessor(this.graphicEngine);
    }
}