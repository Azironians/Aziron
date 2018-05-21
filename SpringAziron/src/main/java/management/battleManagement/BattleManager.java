package management.battleManagement;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import gui.service.graphicEngine.GraphicEngine;
import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEventFactory;
import management.battleManagement.processors.BonusLoadingProcessor;
import management.playerManagement.Team;
import management.playerManagement.GameMode;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;
import management.processors.Processor;
import management.processors.exceptions.UnsupportedProcessorException;
import management.service.components.chainComponet.ChainComponent;
import management.service.engine.EventEngine;
import scala.Tuple3;

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

    public final void install() {
        this.bonusLoadingProcessor = new BonusLoadingProcessor(this.graphicEngine);
    }

    //Next turn:
    public final void nextTurn() {
        final Team team = this.playerManager.getCurrentTeam();
        final Player currentPlayer = team.getCurrentPlayer();
        final Player alternativePlayer = team.getAlternativePlayer();
        final heroes.abstractHero.hero.Hero currentHero = currentPlayer.getCurrentHero();
        final boolean isDestroyed = currentHero.getHitPoints() <= 0;
        if (isDestroyed) {
            currentHero.setAlive(false);
            if (isNotTheEndOfGame(currentPlayer, alternativePlayer)._1()) {
                this.eventEngine.handle(ActionEventFactory.getHeroOut(currentHero));
                if (alternativePlayer.hasAliveHeroes()) {
                    this.makeEagerPlayerSwapRequest(team);
                }
                this.actionManager.refreshScreen();
                log.info("HERO_OUT");
            } else {
                this.eventEngine.handle(ActionEventFactory.getEndGame(currentHero));
                this.endGame();
                log.info("GAME_OVER");
            }
        } else {
            this.changeTurn();
            if (this.skipTurn) {
                final Hero newCurrentHero = playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
                this.graphicEngine.hideBonuses();
                this.eventEngine.handle(ActionEventFactory.getSkipTurn(newCurrentHero));
                this.skipTurn = false;
                this.nextTurn();
            }
        }
    }

    //Defines turn:
    private void changeTurn() {
        final Team left = this.playerManager.getLeftATeam();
        final Team right = this.playerManager.getRightATeam();
        //CHANGE: //////////////////////////////////////////////////////////
        this.turn = (this.turn + 1) % 2;
        if (this.turn == left.getTurn()) {
            this.playerManager.setCurrentATeam(left);
            this.playerManager.setOpponentATeam(right);
        }
        if (this.turn == right.getTurn()) {
            this.playerManager.setCurrentATeam(right);
            this.playerManager.setOpponentATeam(left);
        }
        ////////////////////////////////////////////////////////////////////
        final Team currentTeam = this.playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final Player alternativePlayer = currentTeam.getAlternativePlayer();
        this.reloadAllSkillsOfAllHeroes(currentPlayer);
        if (this.playerManager.getGameMode() == GameMode._2x2) {
            this.reloadAllSkillsOfAllHeroes(alternativePlayer);
        }
        //handling:
        this.eventEngine.handle(ActionEventFactory.getStartTurn(currentTeam));
        if (this.playerManager.getGameMode() == GameMode._2x2) {
            this.eventEngine.handle(ActionEventFactory.getStartTurn(currentTeam));
        }
        this.bonusLoadingProcessor.setHero(currentPlayer.getCurrentHero());
        this.bonusLoadingProcessor.process();
    }

    private void reloadAllSkillsOfAllHeroes(final Player player) {
        final List<heroes.abstractHero.hero.Hero> heroes = player.getAllHeroes();
        for (final heroes.abstractHero.hero.Hero hero : heroes) {
            hero.reloadSkills();
            if (hero != player.getCurrentHero()) {
                hero.getSwapSkill().reload();
            }
        }
    }

    private Tuple3<Boolean, Player, heroes.abstractHero.hero.Hero> isNotTheEndOfGame(final Player current, final Player alternative) {
        final Tuple3<Boolean, Player, heroes.abstractHero.hero.Hero> hasCurrentPlayerAliveHeroes = current.checkAliveHeroes();
        final Tuple3<Boolean, Player, heroes.abstractHero.hero.Hero> hasAlternativePlayerAlliveHeroes;
        if (alternative != null) {
            hasAlternativePlayerAlliveHeroes = alternative.checkAliveHeroes();
        } else {
            hasAlternativePlayerAlliveHeroes = new Tuple3<>(false, null, null);
        }
        if (hasAlternativePlayerAlliveHeroes._1()) {
            return hasAlternativePlayerAlliveHeroes;
        }
        if (hasCurrentPlayerAliveHeroes._1()) {
            return hasCurrentPlayerAliveHeroes;
        }
        return new Tuple3<>(false, null, null);
    }


    public final void endGame() {

    }

    private void makeEagerPlayerSwapRequest(final Team team) {
        this.actionManager.setEagerPlayerSwapRequest(team);
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

    public final BonusLoadingProcessor getBonusLoadingProcessor() {
        return this.bonusLoadingProcessor;
    }

    public final void setCustomProcessor(final Processor processor) throws UnsupportedProcessorException {
        if (processor instanceof BonusLoadingProcessor) {
            this.bonusLoadingProcessor = (BonusLoadingProcessor) processor;
        } else {
            throw new UnsupportedProcessorException("Invalid bonus loadlin processor");
        }
    }

    public final void returnPreviousBonusLoadingProcessor(final BonusLoadingProcessor previousBonusLoadingProcessor){
        final Class bonusLoadingProcessorClass = previousBonusLoadingProcessor.getClass();
        final Class[] classes = bonusLoadingProcessorClass.getInterfaces();
        for (final Class clazz : classes){
            if (clazz == ChainComponent.class){
                final ChainComponent<BonusLoadingProcessor> chain = (ChainComponent) previousBonusLoadingProcessor;
                if (!chain.isWorking()){
                    final BonusLoadingProcessor replacedBonusLoadingProcessor = chain.getReplacedComponent();
                    returnPreviousBonusLoadingProcessor(replacedBonusLoadingProcessor);
                    return;
                }
            }
        }
        this.bonusLoadingProcessor = previousBonusLoadingProcessor;
    }

    public final void setDefaultProcessor() {
        this.bonusLoadingProcessor = new BonusLoadingProcessor(this.graphicEngine);
    }
}