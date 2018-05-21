package management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.bonusPipeNode;

import heroes.abstractHero.abilities.bonus.Bonus
import heroes.abstractHero.hero.Hero
import heroes.abstractHero.abilities.Ability
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.AbilityPipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.CoreEngineComponent

class BonusPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : AbilityPipeNode(pipeNodeID, hero, playerManager, pipeline) {

    override fun getCoreBeforeUsedAbilityEventEngine(): CoreBeforeUsedAbilityEngineComponent
            = CoreBeforeUsedBonusEngineComponentComponent(this.hero, this.pipeline, this.playerManager)

    //Always last in engine order component:
    class CoreBeforeUsedBonusEngineComponentComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreBeforeUsedAbilityEngineComponent("CoreBeforeUsedBonusEngineComponent", hero, pipeline, playerManager) {
        override fun getCheckedBeforeActionType(): ActionType = ActionType.BEFORE_USED_BONUS

        override fun getActionEventDataFieldClass(): Class<out Ability>  = Bonus::class.java

        override fun getDuringActionType(): ActionType = ActionType.DURING_USING_BONUS

        override fun getAfterActionType(): ActionType = ActionType.AFTER_USED_BONUS
    }

    class CoreAfterUsedBonusEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreAfterUsedBonusEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if(this.checkEventAndHero(actionEvent, ActionType.AFTER_USED_BONUS)){
                this.hero.unlockAbilities()
            }
        }
    }
}