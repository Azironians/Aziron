package bonus.devourerBonuses.bonuses.experience.incineration

import bonus.bonuses.Bonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionType}
import management.battleManagement.processors.BonusLoadingProcessor
import management.service.components.chainComponet.ChainComponent
import management.service.components.handleComponent.EngineComponent
import management.service.engine.services.DynamicEngineService

final class XIncineration(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with DynamicEngineService {

  private var customBonusLoadingProcessor: ChainComponent[BonusLoadingProcessor] = _

  private var previousBonusLoadingProcessor: BonusLoadingProcessor = _

  private def installCustomProcessor(): Unit = {
    if (this.customBonusLoadingProcessor == null){
      this.customBonusLoadingProcessor = new IncinerationBonusLoadingProcessor(this.actionManager.getGraphicEngine)
    }
  }

  override def use(): Unit = {
    //Check installation of customProcessor:
    this.installCustomProcessor()
    //Install handler:
    this.actionManager.getEventEngine.addHandler(getPrototypeEngineComponent)
  }

  override def getPrototypeEngineComponent: EngineComponent = new EngineComponent {

    private val hero: Hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero

    private var work: Boolean = _

    override final def setup(): Unit = {}

    override final def handle(actionEvent: ActionEvent): Unit = {
      val actionType = actionEvent.getActionType
      val hero = actionEvent.getHero
      if (actionType == ActionType.START_TURN && this.hero == hero){
        previousBonusLoadingProcessor = battleManager.getBonusLoadingProcessor
        customBonusLoadingProcessor.setReplacedComponent(previousBonusLoadingProcessor)
        battleManager.setCustomProcessor(customBonusLoadingProcessor.asInstanceOf)
      }
      if (actionType == ActionType.END_TURN && this.hero == hero){
        battleManager.returnPreviousBonusLoadingProcessor(previousBonusLoadingProcessor)
        this.work = false
      }
    }

    override final def getName: String = "Incineration"

    override final def getCurrentHero: Hero = hero

    override final def isWorking: Boolean = work

    override final def setWorking(able: Boolean): Unit = work = able
  }
}