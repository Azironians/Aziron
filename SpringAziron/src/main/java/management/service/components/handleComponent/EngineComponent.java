package management.service.components.handleComponent;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.APipeline;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

public abstract class EngineComponent {

    private String name;

    protected Hero hero;

    protected PlayerManager playerManager;

    protected APipeline pipeline;

    public EngineComponent(final String name, final Hero hero, final APipeline pipeline
            , final PlayerManager playerManager){
        this.name = name;
        this.hero = hero;
        this.pipeline = pipeline;
        this.playerManager = playerManager;
    }

    public abstract void handle(final ActionEvent actionEvent);

    public final String getName(){
        return this.name;
    }

    public abstract boolean isWorking();

    public abstract void setWorking(final boolean able) throws IllegalSwitchOffEngineComponentException;

    public final Hero getCurrentHero(){
        return this.hero;
    }
}