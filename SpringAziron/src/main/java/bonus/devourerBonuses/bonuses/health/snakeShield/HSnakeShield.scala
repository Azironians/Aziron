package bonus.devourerBonuses.bonuses.health.snakeShield

import java.util

import bonus.bonuses.Bonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.skills.ASkill
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionType}
import management.playerManagement.Player
import management.service.components.handleComponet.{EngineComponent, IllegalSwitchOffEngineComponentException}
import management.service.engine.services.RegularEngineService

final class HSnakeShield(name: String, val id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with RegularEngineService {

  private var snakeShieldSkillProxyComponent: SnakeShieldSkillProxyComponent = _

  override def use(): Unit = {
    val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val skills: util.List[ASkill] = hero.getCollectionOfSkills
    for (i <- 0 to skills.size) {
      val skill = skills.get(i)
      if (skill.getName.equals("FlameSnakes")) {
        skill.reset()
        snakeShieldSkillProxyComponent.packSkill(i, skills)
        val snakeShieldSkill = snakeShieldSkillProxyComponent.pullSkill()
        this.wireActionManager(snakeShieldSkill)
      }
    }
  }

  private def wireActionManager(skill: ASkill): Unit = {
    skill.setActionManager(actionManager)
  }

  override def installSingletonEngineComponent(player: Player): EngineComponent = new EngineComponent {

    private var previousArmor: Double = 0

    override final def setup(): Unit = {
      snakeShieldSkillProxyComponent = new SnakeShieldSkillProxyComponent(player)
    }

    override final def handle(actionEvent: ActionEvent): Unit = {
      val data = actionEvent.getData
        actionEvent.getActionType match {
          case ActionType.BEFORE_DEAL_DAMAGE =>
            data match {
              case heroVsDamage: javafx.util.Pair[Player, Double] =>
                val hero = heroVsDamage.getKey
                if (player.getCurrentHero == hero){
                  this.previousArmor = hero.getArmor
                  var damage = heroVsDamage.getValue
                  for (i <- 0 to hero.getCollectionOfSkills.size) {
                    val skill = hero.getCollectionOfSkills.get(i)
                    if (skill.getName == "SnakeShield") {
                      val skillArmor: Double = snakeShieldSkillProxyComponent.proxySkillVsDamageMap.get(skill)
                      val comparison: Double = damage - skillArmor
                      if (comparison > 0) {
                        hero.setArmor(hero.getArmor + skillArmor)
                        snakeShieldSkillProxyComponent.destroy(skill)
                        damage = comparison
                        if (damage == 0) {
                          return
                        }
                      } else {
                        hero.setArmor(hero.getArmor + damage)
                        snakeShieldSkillProxyComponent.proxySkillVsDamageMap.put(skill, comparison * (-1.0))
                        damage = 0
                      }
                    }
                  }
                }

            }
          case ActionType.AFTER_DEAL_DAMAGE =>
            data match {
              case heroVsDamage: javafx.util.Pair[Player, Double] =>
                val hero = heroVsDamage.getKey
                if (player.getCurrentHero == hero){
                  hero.setArmor(previousArmor)
                }
            }
        }
    }

    override final def getName: String = "SnakeShield"

    override final def getCurrentHero: Player = player

    override final def isWorking: Boolean = true

    override final def setWorking(able: Boolean): Unit = throw new IllegalSwitchOffEngineComponentException()
  }
}