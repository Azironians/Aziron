package bonus.devourerBonuses.bonuses.special

import java.util.logging.Logger

import bonus.bonuses.Bonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionEventFactory, ActionType}
import management.playerManagement.Player
import management.service.components.handleComponent.EngineComponent
import management.service.engine.services.DynamicEngineService

final class SAvatarsCore(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite) with DynamicEngineService {

  val log: Logger = Logger.getLogger(classOf[SAvatarsCore].getName)

  val ADDITIONAL_DAMAGE: Double = 40.0

  var additionalDamage: Double = 0

  var handleComponent: EngineComponent = _

  override def use(): Unit = {
    this.additionalDamage += ADDITIONAL_DAMAGE
    val eventEngine = this.actionManager.getEventEngine
    if (this.handleComponent == null){
      this.handleComponent = getPrototypeEngineComponent
      eventEngine.addHandler(handleComponent)
    }
    log.info("SKILL POWER INCREASED BY 40")
  }

  override def getPrototypeEngineComponent: EngineComponent = new EngineComponent() {

    private val player = playerManager.getCurrentTeam.getCurrentPlayer

    private var work = true

    private var opponentHeroHitPoints: Double = 0.0

    private var opponentHero : Player = _

    private var isConstantOpponentHitPoints = true

    override final def setup(): Unit = {}

    override final def handle(actionEvent: ActionEvent): Unit = {
      val actionType = actionEvent.getActionType
      val player = actionEvent.getHero
      if (actionType == ActionType.BEFORE_USED_SKILL && player == this.player) {
        val opponentHero = playerManager.getOpponentTeam.getCurrentPlayer.getCurrentHero
        opponentHero.setArmor((-1) * additionalDamage)
        additionalDamage = 0
        this.opponentHero = opponentHero
        this.opponentHeroHitPoints = opponentHero.getHitPoints
      }
      if (this.opponentHero.getHitPoints == this.opponentHeroHitPoints){
        this.isConstantOpponentHitPoints = true
      }
      if (actionType == ActionType.AFTER_USED_SKILL && player == this.player && this.isConstantOpponentHitPoints){
        val eventEngine = actionManager.getEventEngine
        eventEngine.handle(ActionEventFactory.getBeforeDealDamage(this.player, this.opponentHero, additionalDamage))
        if (this.opponentHero.getDamage(additionalDamage)){
          eventEngine.handle(ActionEventFactory.getAfterDealDamage(this.player, this.opponentHero, additionalDamage))
        }
        this.work = false
        handleComponent = null
      }
    }

    override final def getName: String = "AvatarsCore"

    override final def getCurrentHero: Player = this.player

    override final def isWorking: Boolean = this.work

    override final def setWorking(able: Boolean): Unit = this.work = able
  }
}