package management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.swapAbilityPipeNode

import heroes.abstractHero.abilities.Ability
import heroes.abstractHero.abilities.talents.swapAbility.SwapAbility
import heroes.abstractHero.hero.Hero
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.AbilityPipeNode
import management.playerManagement.PlayerManager

class SwapAbilityPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : AbilityPipeNode(pipeNodeID, hero, playerManager, pipeline) {

    override fun getCoreBeforeUsedAbilityEventEngine(): CoreBeforeUsedAbilityEngineComponent
            = CoreBeforeUsedSwapAbilityEngineComponentComponent(this.hero, this.pipeline, this.playerManager)

    //Always last in engine order component:
    class CoreBeforeUsedSwapAbilityEngineComponentComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreBeforeUsedAbilityEngineComponent("CoreBeforeUsedSwapAbilityEngineComponent", hero, pipeline, playerManager) {
        override fun getCheckedBeforeActionType(): ActionType = ActionType.BEFORE_USED_SWAP_ABILITY

        override fun getActionEventDataFieldClass(): Class<out Ability>  = SwapAbility::class.java

        override fun getDuringActionType(): ActionType = ActionType.DURING_USING_SWAP_ABILITY

        override fun getAfterActionType(): ActionType = ActionType.AFTER_USED_SWAP_ABILITY
    }
}