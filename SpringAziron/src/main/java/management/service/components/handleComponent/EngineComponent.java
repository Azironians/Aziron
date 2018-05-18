package management.service.components.handleComponent;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.pipeline.APipeline;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

public abstract class EngineComponent {

    protected String name;

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

    protected abstract EngineComponent clone();

    public abstract void handle(final ActionEvent actionEvent);

    public final String getName(){
        return this.name;
    }

    public abstract boolean isWorking();

    public abstract void setWorking(final boolean isWorking) throws IllegalSwitchOffEngineComponentException;

    public final Hero getCurrentHero(){
        return this.hero;
    }

    protected boolean checkEventAndHero(final ActionEvent actionEvent, final ActionType waitingActionType){
        return actionEvent.getActionType() == waitingActionType && actionEvent.getHero() == this.hero;
    }

    protected boolean checkData(final Object data, final Class clazz){
        return data != null && data.getClass().equals(clazz);
    }
}