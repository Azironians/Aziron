package bonus.devourerBonuses.bonuses.special.openPortal

import bonus.bonuses.ExtendedBonus
import bonus.devourerBonuses.bonuses.special.openPortal.darknessShadow.DarknessShadowProviderComponent
import bonus.devourerBonuses.bonuses.special.openPortal.flameSnakesSpawn.FlameSnakesProviderComponent
import bonus.devourerBonuses.bonuses.special.openPortal.kronArmy.KronArmyProviderComponent
import javafx.scene.image.ImageView
import javafx.scene.text.Text
import management.actionManagement.service.components.providerComponent.ProviderComponent

import scala.collection.JavaConverters._
import scala.collection.mutable

final class SOpenPortal(name: String, id: Int, sprite: ImageView) extends ExtendedBonus(name, id, sprite){

  private val START_COUNT = 0

  private val END_COUNT = 3

  private var count = START_COUNT

  private val text: Text = new Text(START_COUNT + "/" + END_COUNT)

  private var previousProviderComponentList = new mutable.MutableList[ProviderComponent[Int]]

  this.installContainer(this.text)

  override def use(): Unit = {
    if (this.count + 1 == END_COUNT) {
      this.count = START_COUNT
      val player = playerManager.getCurrentTeam.getCurrentPlayer
      val hero = player.getCurrentHero
      val bonusManager = hero.getBonusManager
      for (providerComponent: ProviderComponent[Int] <- bonusManager.getProviderComponentList.asScala){
        this.previousProviderComponentList.+=(providerComponent)
      }
      this.previousProviderComponentList.clear()
      bonusManager.setCustomProviderComponent(0, new KronArmyProviderComponent )
      bonusManager.setCustomProviderComponent(1, new DarknessShadowProviderComponent)
      bonusManager.setCustomProviderComponent(2, new FlameSnakesProviderComponent)
      //add provider component to all opponent heroes.
    }
    else {
      this.count += 1
      this.text.setText(count + "/" + END_COUNT)
    }
  }
}