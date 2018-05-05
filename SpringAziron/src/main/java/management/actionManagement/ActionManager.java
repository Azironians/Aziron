package management.actionManagement;

import bonus.bonuses.Bonus;
import com.google.inject.Inject;
import gui.service.graphicEngine.GraphicEngine;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import management.actionManagement.actionProccessors.*;
import management.actionManagement.actions.ActionEventFactory;
import management.processors.exceptions.UnsupportedProcessorException;
import management.service.engine.EventEngine;
import management.battleManagement.BattleManager;
import management.playerManagement.ATeam;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;
import management.processors.Processor;

import java.util.logging.Logger;

public final class ActionManager {

    private static final Logger log = Logger.getLogger(ActionManager.class.getName());

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private EventEngine eventEngine;

    @Inject
    private GraphicEngine graphicEngine;

    //Action processors:
    private AttackProcessor attackProcessor;

    private TreatmentProcessor treatmentProcessor;

    private SkillProcessor skillProcessor;

    private SwapProcessor swapProcessor;

    private BonusProcessor bonusProcessor;

    public final void install() {
        this.attackProcessor = new AttackProcessor(this, battleManager);
        this.treatmentProcessor = new TreatmentProcessor(this, battleManager);
        this.skillProcessor = new SkillProcessor(this, battleManager, playerManager);
        this.swapProcessor = new SwapProcessor(this, battleManager, playerManager, skillProcessor);
        this.bonusProcessor = new BonusProcessor(this);
    }

    public final void setHeroRequest(final ATeam clickedTeam) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final Hero currentHero = currentPlayer.getCurrentHero();
        if (clickedTeam.equals(currentTeam)) {
            if (currentHero.isTreatmentAccess()) {
                eventEngine.handle(ActionEventFactory.getTreatment(currentPlayer));
                this.treatmentProcessor.setTeam(clickedTeam);
                this.treatmentProcessor.process();
            }
        } else {
            if (currentHero.isAttackAccess()) {
                this.eventEngine.handle(ActionEventFactory.getAttack(currentPlayer));
                this.attackProcessor.setTeams(currentTeam, clickedTeam);
                this.attackProcessor.process();
            }
        }
    }

    public final void setSkillRequest(final Hero hero, final Skill skill) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        final boolean heroAuthentication = hero.equals(currentPlayer.getCurrentHero());
        if (heroAuthentication) {
            final boolean access = skill.isSkillAccess();
            if (access) {
                eventEngine.handle(ActionEventFactory.getBeforeUsedSkill(currentPlayer
                        , skill.getName()));
                this.skillProcessor.setTeamAndSkill(currentTeam, skill);
                this.skillProcessor.process();
            }
        }
    }

    public final void setPlayerSwapRequest(final ATeam team) {
        final ATeam currentTeam = playerManager.getCurrentTeam();
        if (team.equals(currentTeam)) {
            swapProcessor.setTeam(currentTeam);
            swapProcessor.process();
        }
    }

    public final void setBonusRequest(final Bonus bonus) {
        graphicEngine.hideBonuses();
        final ATeam currentTeam = playerManager.getCurrentTeam();
        final Player currentPlayer = currentTeam.getCurrentPlayer();
        eventEngine.handle(ActionEventFactory.getAfterUsedBonus(currentPlayer, bonus));
        bonusProcess(bonus);
    }

    private void bonusProcess(final Bonus bonus) {
        this.bonusProcessor.setBonus(bonus);
        this.bonusProcessor.process();
    }

    public final void setEagerPlayerSwapRequest(final ATeam team) {
        team.eagerSwapPlayers();
        refreshScreen();
    }

    public final void refreshScreen() {
        this.graphicEngine.showLocation();
    }

    public final void endTurn(final ATeam team) {
        this.eventEngine.handle(ActionEventFactory.getEndTurn(team.getCurrentPlayer()));
        this.eventEngine.handle(ActionEventFactory.getEndTurn(team.getAlternativePlayer()));
        refreshScreen();
        this.battleManager.nextTurn();
    }

    public final EventEngine getEventEngine() {
        return eventEngine;
    }

    public final Processor getAttackProcessor() {
        return attackProcessor;
    }

    public final void setAttackProcessor(Processor processor) throws UnsupportedProcessorException {
        if (processor instanceof AttackProcessor) {
            this.attackProcessor = (AttackProcessor) processor;
        } else {
            throw new UnsupportedProcessorException("Invalid attack processor");
        }
    }

    public final Processor getTreatmentProcessor() {
        return treatmentProcessor;
    }

    public final void setTreatmentProcessor(final Processor processor) throws UnsupportedProcessorException {
        if (processor instanceof TreatmentProcessor) {
            this.treatmentProcessor = (TreatmentProcessor) processor;
        } else {
            throw new UnsupportedProcessorException("Invalid healing processor");
        }
    }

    public final Processor getSkillProcessor() {
        return skillProcessor;
    }

    public final void setSkillProcessor(final Processor processor) throws UnsupportedProcessorException {
        if (processor instanceof SkillProcessor) {
            this.skillProcessor = (SkillProcessor) processor;
        } else {
            throw new UnsupportedProcessorException("Invalid skill processor");
        }
    }

    public final Processor getSwapProcessor() {
        return swapProcessor;
    }

    public final void setSwapProcessor(final Processor processor) throws UnsupportedProcessorException {
        if (processor instanceof SwapProcessor) {
            this.swapProcessor = (SwapProcessor) processor;
        } else {
            throw new UnsupportedProcessorException("Invalid swap processor");
        }
    }

    public final Processor getBonusProcessor() {
        return bonusProcessor;
    }

    public final void setBonusProcessor(final Processor processor) throws UnsupportedProcessorException {
        if (processor instanceof BonusProcessor) {
            this.bonusProcessor = (BonusProcessor) processor;
        } else {
            throw new UnsupportedProcessorException("Invalid bonus processor");
        }
    }
}