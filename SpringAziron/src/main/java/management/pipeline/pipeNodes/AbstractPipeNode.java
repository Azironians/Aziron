package management.pipeline.pipeNodes;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.pipes.APipe;
import management.service.components.handleComponent.EngineComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPipeNode {

    public abstract ActionEvent handleEvent(final ActionEvent actionEvent);

    protected final List<EngineComponent> engineComponentList = new ArrayList<>();

    protected final Map<String, APipe> innerPipeMap = new HashMap<>();

    public abstract String getPipeNodeID();

    public abstract Hero getHero();

    public final List<EngineComponent> getEngineComponentList(){
        return this.engineComponentList;
    }

    public final Map<String, APipe> getInnerPipeMap(){
        return this.innerPipeMap;
    }
}