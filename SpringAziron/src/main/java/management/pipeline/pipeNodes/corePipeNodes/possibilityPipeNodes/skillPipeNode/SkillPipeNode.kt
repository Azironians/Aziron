package management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes.skillPipeNode

import heroes.abstractHero.hero.Hero
import heroes.abstractHero.possibility.APossibility
import heroes.abstractHero.skills.Skill
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode
import management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes.PossibilityPipeNode
import management.playerManagement.PlayerManager

class SkillPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : CorePipeNode(pipeNodeID, hero, playerManager, pipeline) {

    init {
        this.engineComponentList.add(CoreBeforeUsedSkillEngineComponent(this.hero, this.pipeline, this.playerManager))
    }

    //Always last in engine order component:
    class CoreBeforeUsedSkillEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : PossibilityPipeNode.CoreBeforeUsedPossibilityEventEngine("CoreBeforeUsedSkillEngineComponent", hero
            , pipeline, playerManager) {

        override fun getCheckedBeforeActionType(): ActionType = ActionType.BEFORE_USED_SKILL

        override fun getActionEventDataFieldClass(): Class<out APossibility>  = Skill::class.java

        override fun getDuringActionType(): ActionType = ActionType.DURING_USING_SKILL
    }
}