package management.service.components.handleComponent;

import heroes.abstractHero.hero.Hero;
import management.playerManagement.PlayerManager;

public abstract class MainEngineComponent implements EngineComponent{

    protected Hero hero;

    protected boolean isNeedPass = false;

    @Override
    public final Hero getCurrentHero() {
        return this.hero;
    }

    @Override
    public final boolean isWorking() {
        return true;
    }

    @Override
    public final void setWorking(boolean able) throws IllegalSwitchOffEngineComponentException {
        throw new IllegalSwitchOffEngineComponentException("Cannot make operation under MainEngineComponent");
    }

    @Override
    public final void setup(final PlayerManager playerManager, final Hero hero) {
        //stub
    }

    @Override
    public final boolean isNeedPass() {
        return this.isNeedPass;
    }
}
