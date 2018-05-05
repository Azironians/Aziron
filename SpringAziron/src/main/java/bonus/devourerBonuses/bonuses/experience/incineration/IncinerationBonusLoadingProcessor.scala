package bonus.devourerBonuses.bonuses.experience.incineration

import gui.service.graphicEngine.GraphicEngine
import management.battleManagement.processors.BonusLoadingProcessor
import management.service.components.chainComponet.ChainComponent

private final class IncinerationBonusLoadingProcessor(graphicEngine: GraphicEngine)
  extends BonusLoadingProcessor(graphicEngine) with ChainComponent[BonusLoadingProcessor] {

  private var replacedBonusLoadingProcessor: BonusLoadingProcessor = _

  override def getReplacedComponent: BonusLoadingProcessor = this.replacedBonusLoadingProcessor

  override def setReplacedComponent(bonusLoadingProcessor: BonusLoadingProcessor): Unit = {
    this.replacedBonusLoadingProcessor = bonusLoadingProcessor
  }
}
