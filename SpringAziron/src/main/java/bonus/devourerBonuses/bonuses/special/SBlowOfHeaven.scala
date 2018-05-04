package bonus.devourerBonuses.bonuses.special

import bonus.bonuses.Bonus
import heroes.abstractHero.skills.Skill
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionEventFactory, ActionType}
import management.actionManagement.service.components.handleComponet.HandleComponent
import management.actionManagement.service.engine.services.DynamicHandleService
import management.playerManagement.Player

import scala.collection.JavaConverters._

final class SBlowOfHeaven(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with DynamicHandleService {

  private val COMPLETE_TURNS = 3

  private val START_TURN = 1

  private val DAMAGE_COEFFICIENT = 3.0

  override def use(): Unit = {
    val hero = this.playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val skills = hero.getCollectionOfSkills.asScala
    var isOneActive: Boolean = false
    var onlyReadySkill: Skill = null
    for (skill <- skills) {
      if (skill.isReady) {
        if (!isOneActive) {
          isOneActive = true
          onlyReadySkill = skill
        } else {
          return
        }
      }
    }
    onlyReadySkill.reset()
    this.actionManager.getEventEngine.addHandler(this.getHandlerInstance)
  }

  override def getHandlerInstance: HandleComponent = new HandleComponent {

    private val player = playerManager.getCurrentTeam.getCurrentPlayer

    private var work = true

    private var turnCounter = START_TURN

    override final def setup(): Unit = {}

    override final def handle(actionEvent: ActionEvent): Unit = {
      if (actionEvent.getActionType == ActionType.START_TURN && actionEvent.getPlayer == this.player){
        this.turnCounter += 1
        if (this.turnCounter == COMPLETE_TURNS){
          this.turnCounter = 0
          this.work = false
          val hero = this.player.getCurrentHero
          val damage = hero.getAttack * DAMAGE_COEFFICIENT
          val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
          val eventEngine = actionManager.getEventEngine
          eventEngine.handle(ActionEventFactory.getBeforeDealDamage(this.player, opponentHero, damage))
          if (opponentHero.getDamage(damage)){
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(this.player, opponentHero, damage))
          }
        }
      }
    }

    override final def getName: String = "BlowOfHeaven"

    override final def getCurrentPlayer: Player = this.player

    override final def isWorking: Boolean = this.work

    override final def setWorking(able: Boolean): Unit = this.work = able
  }
}