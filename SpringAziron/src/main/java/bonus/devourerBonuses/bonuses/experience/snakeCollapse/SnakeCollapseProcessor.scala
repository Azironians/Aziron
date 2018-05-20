package bonus.devourerBonuses.bonuses.experience.snakeCollapse

import heroes.abstractHero.abilities.talents.skill.Skill
import management.actionManagement.ActionManager
import management.actionManagement.actionProccessors.SkillProcessor
import management.battleManagement.BattleManager
import management.playerManagement.{PlayerManager, Team}

class SnakeCollapseProcessor(actionManager: ActionManager, battleManager: BattleManager, playerManager: PlayerManager)
  extends SkillProcessor(actionManager, battleManager, playerManager){

  override def setTeamAndSkill(currentTeam: Team, skill: Skill): Unit = {
    this.currentTeam = currentTeam
    this.skill = new SnakeCollapseSkill()
  }
}