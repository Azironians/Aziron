package bonus.generalBonuses.bonuses.attack.doubleInHead;

import heroes.abstractHero.abilities.bonus.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;

public final class ADoubleInHead extends Bonus implements DynamicEngineService {

    public ADoubleInHead(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final DoubleInHeadEngineComponent engineComponent = (DoubleInHeadEngineComponent) getPrototypeEngineComponent();
        engineComponent.setup(this.actionManager, this.battleManager, this.playerManager, hero);
        engineComponent.use();
        this.actionManager.getEventEngine().addHandler(engineComponent);
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new DoubleInHeadEngineComponent();
    }
}