package bonus.devourerBonuses.bonuses.experience.snakeCollapse

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionType}
import management.playerManagement.Player
import management.processors.Processor
import management.service.components.handleComponent.EngineComponent
import management.service.engine.services.DynamicEngineService

final class XSnakeCollapse(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with DynamicEngineService {

  private var previousProcessor: Processor = _

  override def use(): Unit = {
    actionManager.getEventEngine.addHandler(getPrototypeEngineComponent)
  }

  def installCustomSkillProcessor(): Unit = {
    previousProcessor = actionManager.getSkillProcessor
    val snakeCollapseProcessor = new SnakeCollapseProcessor(actionManager, battleManager, playerManager)
    actionManager.setSkillProcessor(snakeCollapseProcessor)
  }

  def installPreviousProcessor(): Unit = actionManager.setSkillProcessor(previousProcessor)

  override def getPrototypeEngineComponent: EngineComponent = new EngineComponent {

    private var player: Player = _

    private var work: Boolean = _

    override final def setup(): Unit = {
      this.player = playerManager.getCurrentTeam.getCurrentPlayer
      this.work = true
    }

    override final def handle(actionEvent: ActionEvent): Unit = {
      if (actionEvent.getHero == player) {
        actionEvent.getActionType match {
          case ActionType.BEFORE_USED_SKILL =>
            val data = actionEvent.getData
            data match {
              case skillName: String =>
                if (skillName.equals("FlameSnakes")) {
                  installCustomSkillProcessor()
                }
            }
          case ActionType.AFTER_USED_SKILL =>
            val data = actionEvent.getData
            data match {
              case skillName: String =>
                if (skillName.equals("SkillCollapse")) {
                  installPreviousProcessor()
                }
            }
        }
      }
    }

    override final def getName: String = "SnakeCollapse"

    override final def getCurrentHero: Player = player

    override final def isWorking: Boolean = work

    override final def setWorking(able: Boolean): Unit = work = able
  }
}