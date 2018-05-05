package bonus.devourerBonuses.bonuses.experience

import bonus.bonuses.ExtendedBonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import management.actionManagement.actions.{ActionEvent, ActionType}
import management.service.components.handleComponet.HandleComponent
import management.service.engine.services.DynamicHandleService
import management.playerManagement.{ATeam, Player}

import scala.collection.mutable
import scala.collection.JavaConverters._

final class XKronRecall(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite)
  with DynamicHandleService {

  private val START_COUNT = 0

  private val END_COUNT = 3

  private var count: Int = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  this.installContainer(this.text)

  override def use(): Unit = {
    if (this.count + 1 == END_COUNT) {
      //Put handler component:
      this.actionManager.getEventEngine.addHandler(getHandlerInstance)
      this.count = START_COUNT
    }
    else {
      this.count += 1
      this.text.setText(count + "/" + END_COUNT)
    }
  }

  override def getHandlerInstance: HandleComponent = new HandleComponent {

    private val hero: Hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero

    private val opponentTeam: ATeam = playerManager.getOpponentTeam

    private var work: Boolean = true

    private val opponentLevelMap = new mutable.HashMap[Hero, java.lang.Integer]

    private var additionalBonusCount: Int = 0

    override final def setup(): Unit = {
      val allOpponentPlayers = opponentTeam.getAllPlayers
      for (player <- allOpponentPlayers.asScala;
           opponentHero <- player.getAllHeroes.asScala) {
        this.opponentLevelMap.+=(opponentHero -> opponentHero.getLevel)
      }
    }

    override final def handle(actionEvent: ActionEvent): Unit = {
      for (pair <- this.opponentLevelMap){
        val hero = pair._1
        val level = pair._2
        val currentLevel = hero.getLevel
        val comparison = currentLevel - level
        if (comparison > 0){
          this.additionalBonusCount += 1
        }
        this.opponentLevelMap.+=(hero -> currentLevel)
      }
      if (actionEvent.getActionType == ActionType.AFTER_USED_BONUS
        && this.hero == actionEvent.getHero && this.additionalBonusCount > 0) {
        val processor = battleManager.getBonusLoadingProcessor
        processor.setHero(this.hero)
        processor.process()
        this.additionalBonusCount -= 1
      }
    }

    override final def getName: String = "KronRecall"

    override final def getCurrentHero: Hero = this.hero

    override final def isWorking: Boolean = this.work

    override final def setWorking(able: Boolean): Unit = this.work = able
  }
}