package bonus.lvBonuses.bonuses.experience

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionType
import management.service.components.handleComponet.HandleComponent
import management.service.engine.services.DynamicHandleService

private const val EXPERIENCE_BOOST = 10.0

class EOnDrive(name: String, id: Int, sprite: ImageView) : Bonus(name, id, sprite), DynamicHandleService {

    override fun use() {
        this.actionManager.eventEngine.addHandler(this.handlerInstance)
    }

    override fun getHandlerInstance(): HandleComponent = object : HandleComponent {
        //FIXME: CHANGE TO HERO!!!
        val player = playerManager.currentTeam.currentPlayer

        var work = true

        override fun setup() = TODO()

        override fun handle(actionEvent: ActionEvent?) {
            val actionType = actionEvent?.actionType
            val player = actionEvent?.hero
            if (actionType == ActionType.BEFORE_USED_SKILL && this.player == player){
                this.player.currentHero.addExperience(EXPERIENCE_BOOST)
            }
        }

        override fun getName() = "OnDrive"

        override fun getCurrentHero() = this.player

        override fun isWorking() = this.work

        override fun setWorking(able: Boolean) {
            this.work = able
        }
    }
}