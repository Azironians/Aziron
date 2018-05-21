package management.pipeline.pipeNodes.corePipeNodes.mainPipeNode

import heroes.abstractHero.hero.Hero
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionEventFactory
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.CoreEngineComponent
import java.util.logging.Logger

class AGamePipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : CorePipeNode(pipeNodeID, hero, playerManager, pipeline) {

    class CoreStartTurnEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreStartTurnEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.START_TURN) && this.checkCurrentHero()) {
                this.pipeline.push(ActionEventFactory.getShowedBonus(this.hero))
            }
        }
    }

    class CoreShowed3RandomBonusEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreShowed3RandomBonusEngineComponent", hero, pipeline, playerManager) {

        private val log = Logger.getLogger(CoreShowed3RandomBonusEngineComponent::class.java.name)

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.SHOWED_BONUSES) && this.checkCurrentHero()) {
                val bonusList = this.hero.bonusCollection
                val providerComponents = this.hero.bonusManager.providerComponentList
                val firstBonus = providerComponents[0].value
                var secondBonus = providerComponents[1].value
                var thirdBonus = providerComponents[2].value
                while (secondBonus == firstBonus) {
                    secondBonus = providerComponents[1].value
                }
                while (thirdBonus == firstBonus || thirdBonus == secondBonus) {
                    thirdBonus = providerComponents[2].value
                }
                this.hero.graphicLocation.show3Bonuses(bonusList, firstBonus, secondBonus, thirdBonus)
                log.info("BONUS ID: $firstBonus")
                log.info("BONUS ID: $secondBonus")
                log.info("BONUS ID: $thirdBonus")
            }
        }
    }
}