package management.pipeline.pipeNodes.mainPipeNode;

import heroes.abstractHero.hero.Hero;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode;
import management.playerManagement.PlayerManager;

public final class MainPipeNode extends DefaultPipeNode {

    public MainPipeNode(final String pipeNodeID, final Hero hero, final PlayerManager playerManager
            , final APipeline pipeline) {
        super(pipeNodeID, hero, playerManager, pipeline);
    }
}