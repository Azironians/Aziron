package management.pipeline.pipes;

import annotations.sourceAnnotations.NonFinal;
import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.pipeNodes.AbstractPipeNode;

import java.util.ArrayList;
import java.util.List;

@NonFinal
public class APipe {

    protected List<AbstractPipeNode> pipeNodes = new ArrayList<>();

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
        for (final AbstractPipeNode pipeNode : this.pipeNodes){
            final boolean isNeedPass = pipeNode.listen();
            if (isNeedPass){
                return true;
            }
        }
        return false;
    }

    public final APipe addPipeNode(final AbstractPipeNode pipeNode){
        this.pipeNodes.add(pipeNode);
        return this;
    }

    public final APipe addPipeNode(final int position,  final AbstractPipeNode pipeNode){
        this.pipeNodes.add(position, pipeNode);
        return this;
    }

    public final APipe addBeforePipeNode(final String pipeNodeID, final Hero hero, final AbstractPipeNode pipeNode){
        for (int i = 0; i < this.pipeNodes.size(); i++){
            final AbstractPipeNode currentPipeNode  = this.pipeNodes.get(i);
            if (currentPipeNode.getHero() == hero && currentPipeNode.getPipeNodeID().equals(pipeNodeID)){
                this.addPipeNode(i, pipeNode);
                return this;
            }
        }
        this.addPipeNode(pipeNode);
        return this;
    }

    public final List<AbstractPipeNode> getPipeNodes() {
        return this.pipeNodes;
    }
}