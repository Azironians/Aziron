package bonus.devourerBonuses.bonuses.special

import java.util
import java.util.logging.Logger

import heroes.abstractHero.abilities.bonus.Bonus

import scala.collection.JavaConverters._
import heroes.abstractHero.abilities.talents.skill.Skill
import javafx.scene.image.ImageView
import management.actionManagement.actions.{ActionEvent, ActionType}
import management.playerManagement.Player
import management.service.components.handleComponent.EngineComponent
import management.service.engine.services.DynamicEngineService

final class SSplit(name: String, id: Int, sprite: ImageView) extends Bonus(name, id, sprite)
  with DynamicEngineService {

  private val log = Logger.getLogger(classOf[SSplit].getName)

  private val COEFFICIENT_BOOST: Double = 0.3

  override def use(): Unit = {
    val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
    val skills = hero.getCollectionOfSkills.asScala
    var foundFlameSnakes = false
    for (skill: Skill <- skills
         if skill.getName.equals("FlameSnakes")) {
        val coefficients = skill.getCoefficients.asScala
        val newCoefficients = new util.ArrayList[java.lang.Double]
        for (coefficient <- coefficients) {
          newCoefficients.add(coefficient * (1 + COEFFICIENT_BOOST))
        }
        skill.setCoefficients(newCoefficients)
        foundFlameSnakes = true
    }
    if (foundFlameSnakes){
      this.actionManager.getEventEngine.addHandler(getPrototypeEngineComponent)
      log.info("SKILL POWER INCREASED BY 30%")
    }
  }

  override def getPrototypeEngineComponent: EngineComponent = new EngineComponent() {

    private val player = playerManager.getCurrentTeam.getCurrentPlayer

    private var work = true

    override final def setup(): Unit = {}

    override final def handle(actionEvent: ActionEvent): Unit = {
      if (actionEvent.getActionType eq ActionType.END_TURN) {
        val hero = playerManager.getCurrentTeam.getCurrentPlayer.getCurrentHero
        val skills = hero.getCollectionOfSkills.asScala
        for (skill: Skill <- skills) {
          val coefficients = skill.getCoefficients
          val newCoefficients = new util.ArrayList[java.lang.Double]
          for (coefficient <- coefficients.asScala) {
            newCoefficients.add(coefficient / (1 + COEFFICIENT_BOOST))
          }
          skill.setCoefficients(newCoefficients)
        }
        this.work = false
        log.info("SKILL POWER DECREASED BY 30%")
      }
    }

    override final def getName: String = "Split"

    override final def getCurrentHero: Player = this.player

    override final def isWorking: Boolean = this.work

    override final def setWorking(able: Boolean): Unit = this.work = able
  }
}