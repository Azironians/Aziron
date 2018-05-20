package bonus.generalBonuses.bonuses.attack.atack;

import heroes.abstractHero.abilities.bonus.Bonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.DynamicEngineService;

public final class AAttack extends Bonus implements DynamicEngineService {

    public AAttack(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final Hero hero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final AttackEngineBonusComponent attackEngineBonusComponent = (AttackEngineBonusComponent) getPrototypeEngineComponent();
        attackEngineBonusComponent.setup(this.actionManager, this.battleManager, this.playerManager, hero);
        attackEngineBonusComponent.use();
        this.actionManager.getEventEngine().addHandler(attackEngineBonusComponent);
    }

    @Override
    public final EngineComponent getPrototypeEngineComponent() {
        return new AttackEngineBonusComponent();
    }
}