package bonus.devourerBonuses.bonuses.experience.snakeCollapse

import heroes.abstractHero.skills.Skill
import management.actionManagement.ActionManager
import management.actionManagement.actionProccessors.SkillProcessor
import management.battleManagement.BattleManager
import management.playerManagement.{ATeam, PlayerManager}

class SnakeCollapseProcessor(actionManager: ActionManager, battleManager: BattleManager, playerManager: PlayerManager)
  extends SkillProcessor(actionManager, battleManager, playerManager){

  override def setTeamAndSkill(currentTeam: ATeam, skill: Skill): Unit = {
    this.currentTeam = currentTeam
    this.skill = new SnakeCollapseSkill()
  }
}