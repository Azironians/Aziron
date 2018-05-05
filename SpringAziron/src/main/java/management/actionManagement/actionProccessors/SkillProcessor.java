package management.actionManagement.actionProccessors;

import heroes.abstractHero.skills.Skill;
import management.actionManagement.ActionManager;
import management.battleManagement.BattleManager;
import management.playerManagement.ATeam;
import management.playerManagement.PlayerManager;
import management.processors.Processor;

//Not final!
public class SkillProcessor implements Processor {

    private final ActionManager actionManager;

    private final BattleManager battleManager;

    private final PlayerManager playerManager;

    protected ATeam currentTeam;

    protected Skill skill;

    public SkillProcessor(final ActionManager actionManager, final BattleManager battleManager
            , final  PlayerManager playerManager){
        this.actionManager = actionManager;
        this.battleManager = battleManager;
        this.playerManager = playerManager;
    }

    //NOT FINAL:
    @Override
    public void process() {
        this.skill.getActionEvents().clear();
        this.skill.use(battleManager, playerManager); //FIXME: wrap all skills in processor
        this.skill.reset();
        this.skill.getActionEvents().forEach(actionManager.getEventEngine()::handle);
        this.actionManager.refreshScreen();
        if (battleManager.isEndTurn()) {
            actionManager.endTurn(currentTeam);
        }
    }

    //NOT FINAL:
    public void setTeamAndSkill(final ATeam currentTeam, final Skill skill){
        this.currentTeam = currentTeam;
        this.skill = skill;
    }
}
