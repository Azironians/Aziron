package bonus.devourerBonuses.bonuses.experience.fireWall

import heroes.abstractHero.abilities.bonus.Bonus
import heroes.abstractHero.hero.Hero
import javafx.scene.image.ImageView
import management.service.components.providerComponent.ProviderComponent

final class DestroyWall(val hero: Hero, val previousProviderComponent: ProviderComponent[java.lang.Integer]
                        , name: String = "", id: Int = 0, imageView: ImageView = new ImageView())
  extends Bonus(name, id, imageView){

  override def use(): Unit = {
    val bonusManager = hero.getBonusManager
    val providerComponentList = bonusManager.getProviderComponentList
    val index = providerComponentList.indexOf(this)
    bonusManager.returnPreviousProviderComponent(index, hero.getCollectionOfSkills.size(), previousProviderComponent)
  }
}