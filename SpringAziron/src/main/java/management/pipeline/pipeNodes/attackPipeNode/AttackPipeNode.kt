package management.pipeline.pipeNodes.attackPipeNode;

import heroes.abstractHero.hero.Hero
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionEventFactory
import management.actionManagement.actions.ActionType
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode
import management.playerManagement.PlayerManager
import management.service.components.handleComponent.MainEngineComponent

class AttackPipeNode(hero: Hero, playerManager: PlayerManager) : DefaultPipeNode(hero, playerManager) {

    init {
        this.engineComponentList.add(MainAttackEngineComponent())
    }

    override fun getPipeNodeID(): String = "AttackPipeNode"

    class MainAttackEngineComponent : MainEngineComponent() {

        override fun handle(actionEvent: ActionEvent?) {
            if (actionEvent?.actionType === ActionType.DURING_ATTACK && actionEvent.hero === this.hero){
                val attackValue = hero.attack



            }

            val attackValue = attackHero.getAttack()!!
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

        override fun getName(): String = "MainAttackEngineComponent"
    }
}