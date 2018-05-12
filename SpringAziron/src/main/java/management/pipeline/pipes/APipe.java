package management.pipeline.pipes;

import annotations.sourceAnnotations.NonFinal;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.pipeNodes.APipeNode;

import java.util.ArrayList;
import java.util.List;

@NonFinal
public class APipe {

    protected List<APipeNode> pipeNodes = new ArrayList<>();

    public final void push(final ActionEvent actionEvent){
        final int startPosition = 0;
        this.push(actionEvent, startPosition);
    }

    private void push(final ActionEvent actionEvent, final int position){
        if (this.pipeNodes.size() > position){
            final ActionEvent newActionEvent = this.pipeNodes.get(position).handleEvent(actionEvent);
            this.push(newActionEvent, position + 1);
        }
    }

    public final boolean listen() {
        for (final APipeNode pipeNode : this.pipeNodes){
            final boolean isNeedPass = pipeNode.listen();
            if (isNeedPass){
                return true;
            }
        }
        return false;
    }

    public final List<APipeNode> getPipeNodes() {
        return this.pipeNodes;
    }
}