package management.pipeline.pipeNodes;

import management.actionManagement.actions.ActionEvent;
import management.pipeline.pipes.APipe;
import management.service.components.handleComponet.EngineComponent;

import java.util.List;
import java.util.Map;

public interface APipeNode {

    ActionEvent handleEvent(final ActionEvent actionEvent);

    Map<String, APipe> getInnerPipeMap();

    boolean listen();

    Map<String, EngineComponent> getEngineComponentMap();

    String getPipeNodeID();
}