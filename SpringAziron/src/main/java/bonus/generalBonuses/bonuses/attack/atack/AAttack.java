package bonus.generalBonuses.bonuses.attack.atack;

import bonus.bonuses.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.service.components.handleComponet.EngineComponent;
import management.service.engine.services.DynamicEngineService;

public final class AAttack extends Bonus implements DynamicEngineService {

    public AAttack(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final AttackEngineComponent attackEngineComponent = (AttackEngineComponent) getPrototypeEngineComponent();
        attackEngineComponent.setup(this.actionManager, this.battleManager, this.playerManager, hero);
        attackEngineComponent.use();
        this.actionManager.getEventEngine().addHandler(attackEngineComponent);
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new AttackEngineComponent();
    }
}