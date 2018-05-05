package management.battleManagement.processors;

import bonus.bonuses.Bonus;
import gui.service.graphicEngine.GraphicEngine;
import heroes.abstractHero.hero.Hero;
import management.processors.Processor;
import management.service.components.providerComponent.ProviderComponent;

import java.util.List;
import java.util.logging.Logger;

public final class BonusLoadingProcessor implements Processor {

    private static final Logger log = Logger.getLogger(BonusLoadingProcessor.class.getName());

    private final GraphicEngine graphicEngine;

    private Hero hero;

    public BonusLoadingProcessor(final GraphicEngine graphicEngine){
        this.graphicEngine = graphicEngine;
    }

    @Override
    public final void process() {
        final List<Bonus> bonusList = this.hero.getBonusCollection();
        final List<ProviderComponent<Integer>> providerComponents = this.hero.getBonusManager()
                .getProviderComponentList();
        final int firstBonus = providerComponents.get(0).getValue();
        int secondBonus = providerComponents.get(1).getValue();
        int thirdBonus = providerComponents.get(2).getValue();
        while (secondBonus == firstBonus){
            secondBonus = providerComponents.get(1).getValue();
        }
        while (thirdBonus == firstBonus || thirdBonus == secondBonus){
            thirdBonus = providerComponents.get(2).getValue();
        }
        this.graphicEngine.show3Bonuses(bonusList, firstBonus, secondBonus, thirdBonus);
        log.info("BONUS ID: " + firstBonus);
        log.info("BONUS ID: " + secondBonus);
        log.info("BONUS ID: " + thirdBonus);
    }

    public final void setHero(final Hero hero){
        this.hero = hero;
    }
}