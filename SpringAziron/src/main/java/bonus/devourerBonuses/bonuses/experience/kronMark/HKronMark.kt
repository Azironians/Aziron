package bonus.devourerBonuses.bonuses.experience.kronMark

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView
import management.actionManagement.actions.ActionEvent
import management.service.components.handleComponet.EngineComponent
import management.service.components.handleComponet.IllegalSwitchOffEngineComponentException
import management.service.engine.services.RegularEngineService
import management.playerManagement.Player
import java.util.ArrayList

class HKronMark(name: String, id: Int, imageView: ImageView) : Bonus(name, id, imageView), RegularEngineService {

    private var kronMarkProxyComponent: KronMarkProxyComponent? = null

    override fun use() {
        if (kronMarkProxyComponent?.packSkill()!!){
            wireActionManager(kronMarkProxyComponent!!.getJustInTimeSkill()!!)
        } else {
            kronMarkProxyComponent!!.invokeSkill(playerManager)
        }
    }

    private fun wireActionManager(skill: Skill) = skill.setActionManager(actionManager)

    override fun installSingletonEngineComponent(hero: Player?): EngineComponent
            = StarterEngineComponent(hero, kronMarkProxyComponent)

    private class StarterEngineComponent(val player: Player?, var kronMarkProxyComponent: KronMarkProxyComponent?)
        : EngineComponent {

        override fun setup() {
            kronMarkProxyComponent = KronMarkProxyComponent(player)
        }

        override fun handle(actionEvent: ActionEvent?) {
            val garbageList = ArrayList<Skill>()
            for (skill in currentHero!!.currentHero.collectionOfSkills) {
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

        override fun getCurrentHero(): Player? = player

        override fun isWorking(): Boolean = true

        override fun setWorking(able: Boolean) {
            throw IllegalSwitchOffEngineComponentException()
        }
    }
}