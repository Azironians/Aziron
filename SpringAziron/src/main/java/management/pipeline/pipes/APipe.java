package management.pipeline.pipes;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.APipeNode;
import management.playerManagement.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public final class APipe {

    private String pipeName;

    private APipeline pipeline;

    private PlayerManager playerManager;

    APipe(final String pipeName, final APipeline pipeline, final PlayerManager playerManager){
        this.pipeline = pipeline;
        this.playerManager = playerManager;
    }

    private List<APipeNode> pipeNodes = new ArrayList<>();

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

    public final APipe addPipeNode(final APipeNode pipeNode){
        this.pipeNodes.add(pipeNode);
        return this;
    }

    public final APipe addPipeNode(final int position,  final APipeNode pipeNode){
        this.pipeNodes.add(position, pipeNode);
        return this;
    }

    public final APipe addBeforePipeNode(final String pipeNodeID, final Hero hero, final APipeNode pipeNode){
        for (int i = 0; i < this.pipeNodes.size(); i++){
            final APipeNode currentPipeNode  = this.pipeNodes.get(i);
            if (currentPipeNode.getHero() == hero && currentPipeNode.getPipeNodeID().equals(pipeNodeID)){
                this.addPipeNode(i, pipeNode);
                return this;
            }
        }
        this.addPipeNode(pipeNode);
        return this;
    }

    public final List<APipeNode> getPipeNodes() {
        return this.pipeNodes;
    }
}