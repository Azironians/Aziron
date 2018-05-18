package management.pipeline.pipeNodes.treatmentPipeNode

import heroes.abstractHero.hero.Hero
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionEventFactory
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.CoreEngineComponent

class TreatmentPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline: APipeline)
    : DefaultPipeNode(pipeNodeID, hero, playerManager, pipeline) {

    init {
        this.engineComponentList.add(CoreBeforeTreatmentEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreDuringTreatmentEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreBeforeHealingEngineComponent(this.hero, this.pipeline, this.playerManager))
        this.engineComponentList.add(CoreDuringHealingEngineComponent(this.hero, this.pipeline, this.playerManager))
    }

    //Always last in engine order component:
    class CoreBeforeTreatmentEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreBeforeTreatmentEngineComponent", hero, pipeline, playerManager) {
        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.BEFORE_ATTACK)) {
                this.pipeline.push(ActionEventFactory.getDuringTreatment(this.hero))
            }
        }
    }

    class CoreDuringTreatmentEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreDuringTreatmentEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.DURING_TREATMENT)) {
                val treatment = this.hero.treatment
                this.pipeline.push(ActionEventFactory.getBeforeHealing(this.hero, treatment))
                this.pipeline.push(ActionEventFactory.getAfterTreatment(this.hero))
            }
        }
    }

    class CoreBeforeHealingEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreBeforeHealingEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.BEFORE_HEALING)) {
                val healing = actionEvent?.data
                if (checkData(healing, Double::class.java)){
                    this.pipeline.push(ActionEventFactory.getDuringHealing(this.hero, healing as Double))
                }
            }
        }
    }

    class CoreDuringHealingEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreDuringHealingEngineComponent", hero, pipeline, playerManager) {

        override fun handle(actionEvent: ActionEvent?) {
            if (this.checkEventAndHero(actionEvent, ActionType.DURING_HEALING)) {
                val healing = actionEvent?.data
                if (this.checkData(healing, Double::class.java)){
                    val isSuccessful = this.hero.getHealing(healing as Double)
                    if (isSuccessful) {
                        this.pipeline.push(ActionEventFactory.getAfterHealing(this.hero, healing))
                    }
                }
            }
        }
    }
}