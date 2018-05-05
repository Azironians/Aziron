package bonus.devourerBonuses.bonuses.experience

import bonus.bonuses.Bonus
import bonus.bonuses.subInterfaces.QuestBonus
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionType}
import management.service.components.handleComponet.HandleComponent
import management.service.engine.services.DynamicHandleService
import management.playerManagement.Player

final class XTempoSpeed(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite) with DynamicHandleService {

  private val BONUS_BOOST: Int = 2

  private var isActivated = false

  private var totalBoost: Int = 0

  override def use(): Unit = {
    if (isActivated) {
      this.totalBoost += BONUS_BOOST
    } else {
      this.actionManager.getEventEngine.addHandler(getHandlerInstance)
      this.isActivated = true
    }
  }

  override def getHandlerInstance: HandleComponent = new HandleComponent {

    private var player: Player = _

    private var work: Boolean = _

    override final def setup(): Unit = {
      this.player = playerManager.getCurrentTeam.getCurrentPlayer
      this.work = true
    }

    override final def handle(actionEvent: ActionEvent): Unit = {
      val actionType = actionEvent.getActionType
      val player = actionEvent.getHero
      if (actionType == ActionType.BEFORE_USED_BONUS && this.player == player){
        val data: Object = actionEvent.getData
        data match {
          case bonus: Bonus =>
            val clazz = bonus.getClass
            val interfaces = clazz.getInterfaces
            for (interface <- interfaces){
              if (interface.getSimpleName.equals("QuestBonus")){
                val questBonus = bonus.asInstanceOf[QuestBonus]
                questBonus.setProgress(questBonus.getProgress + totalBoost)
                actionManager.getEventEngine.setRepeatHandling(true)
              }
            }
        }
      }
    }

    override final def getName: String = "TempoSpeed"

    override final def getCurrentHero: Player = player

    override final def isWorking: Boolean = work

    override final def setWorking(able: Boolean): Unit = work = able
  }
}