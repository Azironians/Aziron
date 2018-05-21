package management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.skillPipeNode

import heroes.abstractHero.hero.Hero
import heroes.abstractHero.abilities.Ability
import heroes.abstractHero.abilities.talents.skill.Skill
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes.AbilityPipeNode
import management.playerManagement.PlayerManager

class SkillPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : AbilityPipeNode(pipeNodeID, hero, playerManager, pipeline) {

    override fun getCoreBeforeUsedAbilityEventEngine(): CoreBeforeUsedAbilityEngineComponent
            = CoreBeforeUsedSkillEngineComponentComponent(this.hero, this.pipeline, this.playerManager)

    //Always last in engine order component:
    class CoreBeforeUsedSkillEngineComponentComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreBeforeUsedAbilityEngineComponent("CoreBeforeUsedSkillEngineComponent", hero, pipeline, playerManager) {
        override fun getCheckedBeforeActionType(): ActionType = ActionType.BEFORE_USED_SKILL

        override fun getActionEventDataFieldClass(): Class<out Ability>  = Skill::class.java

        override fun getDuringActionType(): ActionType = ActionType.DURING_USING_SKILL

        override fun getAfterActionType(): ActionType = ActionType.AFTER_USED_SKILL
    }
}