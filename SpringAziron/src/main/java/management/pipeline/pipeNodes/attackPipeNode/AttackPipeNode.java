package management.pipeline.pipeNodes.attackPipeNode;

import management.actionManagement.actions.ActionEvent;
import management.pipeline.pipeNodes.APipeNode;
import management.pipeline.pipes.APipe;
import management.service.components.handleComponet.EngineComponent;

import java.util.HashMap;
import java.util.Map;

public final class AttackPipeNode implements APipeNode {

    private final Map<String, EngineComponent> engineComponentMap = new HashMap<>();

    private final Map<String, APipe> innerPipeMap = new HashMap<>();

    @Override
    public final ActionEvent handleEvent(final ActionEvent actionEvent) {
        for (final EngineComponent engineComponent : this.engineComponentMap.values()) {
            engineComponent.handle(actionEvent);
        }
        return actionEvent;
    }

    @Override
    public final Map<String, APipe> getInnerPipeMap() {
        return this.innerPipeMap;
    }

    @Override
    public final boolean listen() {
        for (final String engineComponentName : this.engineComponentMap.keySet()) {
            final EngineComponent engineComponent = this.engineComponentMap.get(engineComponentName);
            if (engineComponent.isWorking()) {
                final boolean isNeedPass = engineComponent.isNeedPass();
                if (isNeedPass) {
                    return true;
                }
            } else {
                this.engineComponentMap.remove(engineComponentName);
            }
        }
        return false;
    }

    @Override
    public final Map<String, EngineComponent> getEngineComponentMap() {
        return this.engineComponentMap;
    }
}
