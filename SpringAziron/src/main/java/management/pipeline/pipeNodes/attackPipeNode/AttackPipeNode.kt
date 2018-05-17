package management.pipeline.pipeNodes.attackPipeNode;

import heroes.abstractHero.hero.Hero
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionEventFactory
import management.actionManagement.actions.ActionType
import management.pipeline.APipeline
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.MainEngineComponent

class AttackPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline : APipeline)
    : DefaultPipeNode(pipeNodeID, hero, playerManager, pipeline) {

    init {
        this.engineComponentList.add(MainAttackEngineComponent())
    }

    class MainAttackEngineComponent(hero: Hero, playerManager: PlayerManager, pipeline: APipeline) : MainEngineComponent("MainAttackEngineComponent", hero ) {

        override fun handle(actionEvent: ActionEvent?) {
            if (actionEvent?.actionType === ActionType.DURING_ATTACK && actionEvent.hero === this.hero){
                val attackValue = hero.attack



                val eventEngine = actionManager.getEventEngine()
                if (attackHero.addExperience(attackValue!!)) {
                    eventEngine.handle()
                }
                val victimHero = victimTeam.getCurrentPlayer().getCurrentHero()
                if (victimHero.getDamage(attackValue!!)) {
                    eventEngine.handle(ActionEventFactory.getAfterDealDamage(attackPlayer, victimHero, attackValue!!))
                }
                actionManager.refreshScreen()
                if (battleManager.isEndTurn()) {
                    actionManager.endTurn(attackTeam)
                }
            }


        }

        override fun getName(): String = "MainAttackEngineComponent"
    }
}