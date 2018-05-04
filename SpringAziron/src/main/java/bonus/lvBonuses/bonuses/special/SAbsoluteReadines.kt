package bonus.lvBonuses.bonuses.special

import bonus.bonuses.Bonus
import javafx.scene.image.ImageView

class SAbsoluteReadines(name: String, id: Int, sprite: ImageView) : Bonus(name, id, sprite) {

    override fun use() {
        val team = this.playerManager.currentTeam
        val hero = team.currentPlayer.currentHero
        for (skill in hero.collectionOfSkills){
            skill.temp = skill.reload
        }
        actionManager.endTurn(team)
    }
}