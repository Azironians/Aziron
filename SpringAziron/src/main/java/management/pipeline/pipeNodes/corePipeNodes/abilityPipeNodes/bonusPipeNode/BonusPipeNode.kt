package management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.bonusPipeNode;

import heroes.abstractHero.abilities.bonus.Bonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.abilities.Ability
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.AbilityPipeNode
import management.playerManagement.PlayerManager

class BonusPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : AbilityPipeNode(pipeNodeID, hero, playerManager, pipeline) {

    override fun getCoreBeforeUsedAbilityEventEngine(): CoreBeforeUsedAbilityEventEngine
            = CoreBeforeUsedBonusEngineComponent(this.hero, this.pipeline, this.playerManager)

    //Always last in engine order component:
    class CoreBeforeUsedBonusEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreBeforeUsedAbilityEventEngine("CoreBeforeUsedBonusEngineComponent", hero
            , pipeline, playerManager) {

        override fun getCheckedBeforeActionType(): ActionType = ActionType.BEFORE_USED_BONUS

        override fun getActionEventDataFieldClass(): Class<out Ability>  = Bonus::class.java

        override fun getDuringActionType(): ActionType = ActionType.DURING_USING_BONUS
    }
}