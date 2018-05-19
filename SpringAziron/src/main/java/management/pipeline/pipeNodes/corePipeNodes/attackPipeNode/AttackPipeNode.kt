package management.pipeline.pipeNodes.corePipeNodes.attackPipeNode

import heroes.abstractHero.hero.Hero
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionEventFactory
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.CoreEngineComponent

class AttackPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : CorePipeNode(pipeNodeID, hero, playerManager, pipeline) {

    init {
        this.engineComponentList.add(CoreBeforeAttackEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreDuringAttackEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreBeforeExperienceEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreDuringExperienceEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreBeforeDealDamageEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreDuringDealDamageEngineComponent(this.hero, this.pipeline, this.playerManager))
    }

    //Always last in engine order component:
    class CoreBeforeAttackEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreBeforeAttackEngineComponent", hero, pipeline, playerManager) {
        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.BEFORE_ATTACK)) {
                this.pipeline.push(ActionEventFactory.getDuringAttack(this.hero))
            }
        }
    }

    class CoreDuringAttackEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreDuringAttackEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.DURING_ATTACK)) {
                val opponentHero = this.playerManager.opponentTeam.currentPlayer.currentHero
                val damage = this.hero.attack
                val experience = damage
                this.pipeline.push(ActionEventFactory.getBeforeGettingExperience(this.hero, experience))
                this.pipeline.push(ActionEventFactory.getBeforeDealDamage(this.hero, opponentHero, damage))
                this.pipeline.push(ActionEventFactory.getAfterAttack(this.hero))
            }
        }
    }

    //Always last in engine order component:
    class CoreBeforeExperienceEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreBeforeExperienceEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.BEFORE_GETTING_EXPERIENCE)) {
                val experience = actionEvent?.data as Double
                this.pipeline.push(ActionEventFactory.getDuringGettingExperience(this.hero, experience))
            }
        }
    }

    class CoreDuringExperienceEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreDuringExperienceEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.DURING_GETTING_EXPERIENCE)) {
                val experience = actionEvent?.data as Double
                val isSuccessful = this.hero.addExperience(experience)
                if (isSuccessful) {
                    this.pipeline.push(ActionEventFactory.getAfterGettingExperience(this.hero, experience))
                }
            }
        }
    }


    class CoreBeforeDealDamageEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreBeforeDealDamageEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.BEFORE_DEAL_DAMAGE)) {
                val victimVsDamage = actionEvent?.data
                if (this.checkData(victimVsDamage, Pair::class.java)) {
                    victimVsDamage as Pair<*, *>
                    val victim = victimVsDamage.first
                    val damage = victimVsDamage.second
                    if (this.checkData(victim, Hero::class.java) && this.checkData(damage, Double::class.java)) {
                        this.pipeline.push(ActionEventFactory.getDuringDealDamage(this.hero, victim as Hero
                                , damage as Double))
                    }
                }
            }
        }
    }


    class CoreDuringDealDamageEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreDuringDealDamageEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.DURING_DEAL_DAMAGE)) {
                val victimVsDamage = actionEvent?.data
                if (this.checkData(victimVsDamage, Pair::class.java)) {
                    victimVsDamage as Pair<*, *>
                    val victim = victimVsDamage.first
                    val damage = victimVsDamage.second
                    if (this.checkData(victim, Hero::class.java) && this.checkData(damage, Double::class.java)) {
                        victim as Hero
                        damage as Double
                        val isSuccessful = victim.getDamage(damage)
                        if (isSuccessful) {
                            this.pipeline.push(ActionEventFactory.getAfterDealDamage(this.hero, victim, damage))
                        }
                    }
                }
            }
        }
    }
}