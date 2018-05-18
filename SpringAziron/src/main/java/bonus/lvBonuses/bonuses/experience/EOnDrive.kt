package bonus.lvBonuses.bonuses.experience

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import management.actionManagement.actions.ActionEvent
import management.actionManagement.actions.ActionType
import management.service.components.handleComponent.EngineComponent
import management.service.engine.services.DynamicEngineService

private const val EXPERIENCE_BOOST = 10.0

class EOnDrive(name: String, id: Int, sprite: ImageView) : Bonus(name, id, sprite), DynamicEngineService {

    override fun use() {
        this.actionManager.eventEngine.addHandler(this.prototypeEngineComponent)
    }

    override fun getPrototypeEngineComponent(): EngineComponent = object : EngineComponent {
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

        override fun setWorking(isWorking: Boolean) {
            this.work = isWorking
        }
    }
}