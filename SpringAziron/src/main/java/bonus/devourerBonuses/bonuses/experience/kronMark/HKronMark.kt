package bonus.devourerBonuses.bonuses.experience.kronMark

import bonus.bonuses.Bonus
import heroes.abstractHero.skills.Skill
import javafx.scene.image.ImageView
import management.actionManagement.actions.ActionEvent
import management.actionManagement.service.components.handleComponet.HandleComponent
import management.actionManagement.service.components.handleComponet.IllegalSwitchOffHandleComponentException
import management.actionManagement.service.engine.services.RegularHandleService
import management.playerManagement.Player
import java.util.ArrayList

class HKronMark(name: String, id: Int, imageView: ImageView) : Bonus(name, id, imageView), RegularHandleService{

    private var kronMarkProxyComponent: KronMarkProxyComponent? = null

    override fun use() {
        if (kronMarkProxyComponent?.packSkill()!!){
            wireActionManager(kronMarkProxyComponent!!.getJustInTimeSkill()!!)
        } else {
            kronMarkProxyComponent!!.invokeSkill(playerManager)
        }
    }

    private fun wireActionManager(skill: Skill) = skill.setActionManager(actionManager)

    override fun getRegularHandlerInstance(player: Player?): HandleComponent
            = StarterHandleComponent(player, kronMarkProxyComponent)

    private class StarterHandleComponent(val player: Player?, var kronMarkProxyComponent: KronMarkProxyComponent?)
        : HandleComponent{

        override fun setup() {
            kronMarkProxyComponent = KronMarkProxyComponent(player)
        }

        override fun handle(actionEvent: ActionEvent?) {
            val garbageList = ArrayList<Skill>()
            for (skill in currentPlayer!!.currentHero.collectionOfSkills) {
                if (skill.isReady) {
                    val skillVsProxyMap = kronMarkProxyComponent!!.skillVsProxyMap
                    val fireBlast = skillVsProxyMap.getProxy(skill)
                    if (fireBlast != null) {
                        garbageList.add(fireBlast)
                    }
                }
            }
            for (skill in garbageList){
                kronMarkProxyComponent!!.destroy(skill)
            }
        }

        override fun getName(): String = "KronMark"

        override fun getCurrentPlayer(): Player? = player

        override fun isWorking(): Boolean = true

        override fun setWorking(able: Boolean) {
            throw IllegalSwitchOffHandleComponentException()
        }
    }
}