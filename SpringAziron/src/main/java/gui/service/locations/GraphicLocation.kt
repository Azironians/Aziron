package gui.service.locations

import controllers.main.matchmaking.ControllerLocation
import controllers.main.matchmaking.ControllerMatchMaking
import heroes.abstractHero.abilities.bonus.Bonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.text.Text
import java.util.logging.Logger

data class GraphicLocation(var rootAnchorPane: AnchorPane, var invertPosition: Boolean) {

    companion object {

        private val log = Logger.getLogger(GraphicLocation::class.java.name)
        private val SWAP_ABILITY_INDEX = 0

        private val BONUS_STACK_INDEX = 1
        private val SKILL_INDEX = 3
        private val FACE_INDEX = 4
        private val CHARACTERISTIC_INDEX = 2
        private val ATTACK_INDEX = 0

        private val HIT_POINTS_INDEX = 1
        private val SUPPLY_HEALTH_INDEX = 2
        private val TREATMENT_INDEX = 3
        private val CURRENT_LEVEL_INDEX = 4
        private val CURRENT_EXPERIENCE_INDEX = 5
        private val REQUIRED_LEVEL_INDEX = 6
        private val TIME_INDEX = 7
        private val NAME_INDEX = 8
    }

    var controllerMatchMaking: ControllerMatchMaking? = null

    var rootChildrenList = rootAnchorPane.children
    var faceImageView: ImageView = this.rootChildrenList[FACE_INDEX] as ImageView
    var skillPane: AnchorPane = this.rootChildrenList[SKILL_INDEX] as AnchorPane
    var swapAbilityPane: AnchorPane = this.rootChildrenList[SWAP_ABILITY_INDEX] as AnchorPane
    var characteristicsPane: AnchorPane = this.rootChildrenList[CHARACTERISTIC_INDEX] as AnchorPane
    var characteristicsChildrenList = characteristicsPane.children

    var attackText = characteristicsChildrenList[ATTACK_INDEX] as Text
    var hitPointsText = characteristicsChildrenList[HIT_POINTS_INDEX] as Text
    var supplyHealthText = characteristicsChildrenList[SUPPLY_HEALTH_INDEX] as Text
    var treatmentText = characteristicsChildrenList[TREATMENT_INDEX] as Text
    var currentLevelText = characteristicsChildrenList[CURRENT_LEVEL_INDEX] as Text
    var currentExperienceText = characteristicsChildrenList[CURRENT_EXPERIENCE_INDEX] as Text
    var requiredExperienceText = characteristicsChildrenList[REQUIRED_LEVEL_INDEX] as Text
    var timeText = characteristicsChildrenList[TIME_INDEX] as Text
    var name = characteristicsChildrenList[NAME_INDEX] as Text

    fun placeSuperSkills(parentHero: Hero, controllerLocation: ControllerLocation) {
        val startY = 0.0
        var startX = if (invertPosition) 0.0 else 150.0
        val shiftX = if (invertPosition) +75.0 else -75.0
        this.skillPane.children.clear()
        for (skill in parentHero.collectionOfSkills) {
            skill.installControllerLocation(controllerLocation)
            skill.guiHolder.bindToLocation(skillPane, startX, startY, startX, -127.0, invertPosition)
            startX += shiftX
        }
    }

    fun placeSwapSkills(parentHero: Hero, controllerLocation: ControllerLocation) {
        //Stub...
    }

    //Default bonus showing method:
    fun show3Bonuses(firstBonus: Bonus, secondBonus: Bonus, thirdBonus: Bonus) {
        val bonusLocationPane = this.controllerMatchMaking?.bonusLocationPane
        val bonusPane = bonusLocationPane!!.children[0] as Pane
        val bonusPaneChildren = bonusPane.children
        val firstImageView = firstBonus.guiHolder.mainImage
        val secondImageView = secondBonus.guiHolder.mainImage
        val thirdImageView = thirdBonus.guiHolder.mainImage
        bonusLocationPane.toFront()
        firstImageView.layoutX = 0.0
        bonusPaneChildren.add(firstImageView)
        secondImageView.layoutX = 335.0
        bonusPaneChildren.add(secondImageView)
        thirdImageView.layoutX = 660.0
        bonusPaneChildren.add(thirdImageView)
        //Log:
        log.info("Bonuses load successfully")
    }

    //Flexible bonus showing method:
    fun showBonuses(vararg bonuses: Bonus, shiftX: Double) {
        val bonusLocationPane = this.controllerMatchMaking?.bonusLocationPane
        val bonusPane = bonusLocationPane!!.children[0] as Pane
        val bonusPaneChildren = bonusPane.children
        var currentLayoutX = 0.0
        for (bonus in bonuses) {
            val bonusImageView = bonus.guiHolder.mainImage
            bonusImageView.layoutX = currentLayoutX
            bonusPaneChildren.add(bonusImageView)
            currentLayoutX += shiftX
        }
    }
}