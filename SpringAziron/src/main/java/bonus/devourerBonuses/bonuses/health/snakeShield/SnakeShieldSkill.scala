package bonus.devourerBonuses.bonuses.health.snakeShield

import java.util

import heroes.abstractHero.tallents.abstractSkill.AbstractSkill
import javafx.scene.image.ImageView
import javafx.scene.media.Media
import management.actionManagement.actions.ActionEventFactory
import management.battleManagement.BattleManager
import management.playerManagement.PlayerManager

final class SnakeShieldSkill(val proxyComponent: SnakeShieldSkillProxyComponent
                             , name: String = ""
                             , reload: Int = 0
                             , requiredLevel: Int = 1
                             , coefficients: util.List[java.lang.Double] = util.List.of[java.lang.Double](1.0)
                             , sprite: ImageView = new ImageView()
                             , description: ImageView = new ImageView()
                             , listOfVoices: util.List[Media] = util.List.of())
  extends AbstractSkill(name, reload, requiredLevel, coefficients, sprite, description: ImageView
    , listOfVoices: util.List[Media]) {

  override def use(battleManager: BattleManager, playerManager: PlayerManager): Unit = {
    val damage: Double = proxyComponent.proxySkillVsDamageMap.get(this)
    val currentPlayer = playerManager.getCurrentTeam.getCurrentPlayer
    val opponentPlayer = playerManager.getOpponentTeam.getCurrentPlayer
    val opponentHero = opponentPlayer.getCurrentHero
    actionEvents.add(ActionEventFactory.getBeforeDealDamage(currentPlayer, opponentHero, damage))
    if (opponentHero.getDamage(damage)) {
      actionEvents.add(ActionEventFactory.getAfterDealDamage(currentPlayer, opponentHero, damage))
    }
    this.destroy()
  }

  private def destroy(): Unit = {
    proxyComponent.destroy(this)
  }

  override def showAnimation(): Unit = {}
}