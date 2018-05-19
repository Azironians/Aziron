package management.pipeline.pipeNodes.corePipeNodes.mainPipeNode;

import heroes.abstractHero.hero.Hero;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode;
import management.playerManagement.PlayerManager;

public final class MainPipeNode extends CorePipeNode {

    public MainPipeNode(final String pipeNodeID, final Hero hero, final PlayerManager playerManager
            , final APipeline pipeline) {
        super(pipeNodeID, hero, playerManager, pipeline);
    }
}