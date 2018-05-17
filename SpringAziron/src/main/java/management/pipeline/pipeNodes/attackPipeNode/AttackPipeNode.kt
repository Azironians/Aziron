package management.pipeline.pipeNodes.attackPipeNode;

import heroes.abstractHero.hero.Hero
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionEventFactory
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.CoreEngineComponent
import management.service.components.handleComponent.MainEngineComponent

class AttackPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline : APipeline)
    : DefaultPipeNode(pipeNodeID, hero, playerManager, pipeline) {

    init {
        this.engineComponentList.add(CoreAttackEngineComponent(this.hero, this.pipeline,this.playerManager))
    }

    class CoreAttackEngineComponent(hero: Hero, pipeline: APipeline, playerManager: PlayerManager)
        : CoreEngineComponent("CoreAttackEngineComponent", hero, pipeline, playerManager ) {

        override fun isWorking(): Boolean {
        }

        override fun setWorking(able: Boolean) {
        }

        override fun handle(actionEvent: ActionEvent?) {
            if (actionEvent?.actionType === ActionType.DURING_ATTACK && actionEvent.hero === this.hero){
                val opponentHero = this.playerManager.opponentTeam.currentPlayer.currentHero
                val attack = hero.attack
                val experience = attack
                this.pipeline.push(ActionEventFactory.getBeforeAttack(hero))
                this.pipeline.push(ActionEventFactory.getBeforeGettingExperience(hero, experience))
                if (hero.addExperience(experience)){
                    this.pipeline.push(ActionEventFactory.getAfterGettingExperience(hero, experience))
                }
                this.pipeline.push(ActionEventFactory.getBeforeDealDamage(hero, opponentHero, attack))
                if (opponentHero.getDamage(attack)) {
                    this.pipeline.push(ActionEventFactory.getAfterDealDamage(hero, opponentHero, attack))
                }
                actionManager.refreshScreen()
                if (battleManager.isEndTurn()) {
                    actionManager.endTurn(attackTeam)
                }
            }
        }
    }
}