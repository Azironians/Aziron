package management.pipeline;

import management.actionManagement.actions.ActionEvent;

import java.util.ArrayList;
import java.util.List;

public final class APipe {

    private List<APipeNode> pipeNodes = new ArrayList<>();

    public final void push(final ActionEvent actionEvent){
        if (this.pipeNodes.size() > 0){
            final ActionEvent newActionEvent = this.pipeNodes.get(0).handleEvent(actionEvent);
            this.push(newActionEvent, 1);
        }
    }

    private void push(final ActionEvent actionEvent, int position){
        if (this.pipeNodes.size() > position){
            final ActionEvent newActionEvent = this.pipeNodes.get(position).handleEvent(actionEvent);
            this.push(newActionEvent, position + 1);
        }
    }

    public final List<APipeNode> getPipeNodes() {
        return this.pipeNodes;
    }
}