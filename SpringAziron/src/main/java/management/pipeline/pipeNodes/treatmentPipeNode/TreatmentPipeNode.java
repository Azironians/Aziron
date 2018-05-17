package management.pipeline.pipeNodes.treatmentPipeNode;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.AbstractPipeNode;
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode;
import management.playerManagement.PlayerManager;

public class TreatmentPipeNode extends DefaultPipeNode {

    public TreatmentPipeNode(Hero hero, PlayerManager playerManager, APipeline pipeline) {
        super(hero, playerManager, pipeline);
    }

    @Override
    public String getPipeNodeID() {
        return null;
    }
}
