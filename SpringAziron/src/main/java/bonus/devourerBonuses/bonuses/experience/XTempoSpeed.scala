package bonus.devourerBonuses.bonuses.experience

import bonus.bonuses.service.annotations.EngineField
import bonus.bonuses.service.annotations.implementations.{BonusEngine, QuestEngine}
import bonus.bonuses.service.parameterType.ParameterType
import heroes.abstractHero.abilities.bonus.Bonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionType}
import management.playerManagement.Player
import management.service.components.handleComponent.{EngineComponent, IllegalSwitchOffEngineComponentException}
import management.service.engine.services.RegularEngineService
import org.springframework.util.ReflectionUtils

final class XTempoSpeed(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with RegularEngineService {

  private var tempoSpeedEngineComponent: TempoSpeedEngineComponent = _

  override def use(): Unit = {
    this.tempoSpeedEngineComponent.use()
  }

  override def installSingletonEngineComponent(player: Player): EngineComponent = {
    if (this.tempoSpeedEngineComponent == null) {
      this.tempoSpeedEngineComponent = new TempoSpeedEngineComponent(player)
    }
    this.tempoSpeedEngineComponent
  }

  override def getSingletonEngineComponent: EngineComponent = this.tempoSpeedEngineComponent

  @BonusEngine(engine = new EngineField("bonusBoost", ParameterType.VALUE))
  private final class TempoSpeedEngineComponent(player: Player) extends EngineComponent {

    private val START_BONUS_BOOST: Int = 2

    private val START_TOTAL_BOOST: Int = 0

    private var bonusBoost = START_BONUS_BOOST

    private var totalBoost: Int = START_TOTAL_BOOST

    private val checkedParameterType = ParameterType.VALUE

    private val hero: Player = player.getCurrentHero

    def use(): Unit = this.totalBoost += this.bonusBoost

    override def setup(): Unit = {}

    override def handle(actionEvent: ActionEvent): Unit = {
      val actionType = actionEvent.getActionType
      val hero = actionEvent.getHero
      if (actionType == ActionType.BEFORE_USED_BONUS && this.hero == hero) {
        val data: Object = actionEvent.getData
        val bonus = data.asInstanceOf[Bonus]
        val clazz = bonus.getClass
        val interfaces = clazz.getInterfaces
        for (interface <- interfaces) {
          if (interface.getName == "RegularEngineService") {
            val regularEngineService = bonus.asInstanceOf[RegularEngineService]
            val component = regularEngineService.getSingletonEngineComponent
            val componentClass = component.getClass
            if (componentClass.isAnnotationPresent(Class[QuestEngine])) {
              val questEngineAnnotation = componentClass.getAnnotation(Class[QuestEngine]).asInstanceOf[QuestEngine]
              val engineAnnotation = questEngineAnnotation.engineProgress()
              val fieldName = engineAnnotation.fieldName()
              val parameterType = engineAnnotation.parameterType()
              if (this.checkedParameterType == parameterType) {
                val field = componentClass.getField(fieldName)
                ReflectionUtils.makeAccessible(field)
                val count = ReflectionUtils.getField(field, component).asInstanceOf[Integer]
                ReflectionUtils.setField(field, component, count + this.bonusBoost)
                this.totalBoost = 0
              }
            }
          }
        }
      }
    }

    override def getName: String = "TempoSpeed"

    override def getCurrentHero: Player = this.hero

    override def isWorking: Boolean = true

    override def setWorking(able: Boolean): Unit = throw  new IllegalSwitchOffEngineComponentException("TempoSpeed")
  }
}