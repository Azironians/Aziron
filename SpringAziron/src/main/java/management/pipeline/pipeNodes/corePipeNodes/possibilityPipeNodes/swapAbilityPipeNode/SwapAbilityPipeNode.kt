package management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes.swapAbilityPipeNode

import heroes.abstractHero.hero.Hero
import heroes.abstractHero.skills.ASwapAbility
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionEventFactory
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.CoreEngineComponent


class SwapAbilityPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : CorePipeNode(pipeNodeID, hero, playerManager, pipeline) {

    init {
        this.engineComponentList.add(CoreBeforeUsedSwapAbilityEngineComponent(this.hero, this.pipeline
                , this.playerManager))
    }

    //Always last in engine order component:
    class CoreBeforeUsedSwapAbilityEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreBeforeUsedSwapAbilityEngineComponent", hero, pipeline, playerManager) {
        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.BEFORE_USED_SWAP_ABILITY)) {
                val swapAbility = actionEvent?.data
                if (this.checkData(swapAbility, ASwapAbility::class.java)) {
                    this.pipeline.push(ActionEventFactory.getDuringUsingSwapAbility(this.hero
                            , swapAbility as ASwapAbility))
                }
            }
        }
    }
}