package bonus.devourerBonuses.bonuses.special

import bonus.bonuses.Bonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.skills.ASkill
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionEventFactory, ActionType}
import management.service.components.handleComponent.EngineComponent
import management.service.engine.services.DynamicEngineService

import scala.collection.JavaConverters._

final class SBlowOfHeaven(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with DynamicEngineService {

  private val COMPLETE_TURNS = 3

  private val START_TURN = 1

  private val DAMAGE_COEFFICIENT = 3.0

  override def use(): Unit = {
    val hero = this.playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val skills = hero.getCollectionOfSkills.asScala
    var isOneActive: Boolean = false
    var onlyReadySkill: ASkill = null
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
    this.actionManager.getEventEngine.addHandler(this.getPrototypeEngineComponent)
  }

  override def getPrototypeEngineComponent: EngineComponent = new EngineComponent {

    private val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero

    private var work = true

    private var turnCounter = START_TURN

    override final def setup(): Unit = {}

    override final def handle(actionEvent: ActionEvent): Unit = {
      if (actionEvent.getActionType == ActionType.START_TURN && actionEvent.getHero == this.hero){
        this.turnCounter += 1
        if (this.turnCounter == COMPLETE_TURNS){
          this.turnCounter = 0
          this.work = false
          val damage = this.hero.getAttack * DAMAGE_COEFFICIENT
          val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
          val eventEngine = actionManager.getEventEngine
          eventEngine.handle(ActionEventFactory.getBeforeDealDamage(this.hero, opponentHero, damage))
          if (opponentHero.getDamage(damage)){
            eventEngine.handle(ActionEventFactory.getAfterDealDamage(this.hero, opponentHero, damage))
          }
        }
      }
    }

    override final def getName: String = "BlowOfHeaven"

    override final def getCurrentHero: Hero = this.hero

    override final def isWorking: Boolean = this.work

    override final def setWorking(able: Boolean): Unit = this.work = able
  }
}