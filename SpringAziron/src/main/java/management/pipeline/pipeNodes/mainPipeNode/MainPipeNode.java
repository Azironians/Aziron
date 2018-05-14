package management.pipeline.pipeNodes.mainPipeNode;

import heroes.abstractHero.hero.Hero;
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode;
import management.playerManagement.PlayerManager;

public final class MainPipeNode extends DefaultPipeNode {

    public MainPipeNode(final Hero hero, final PlayerManager playerManager) {
        super(hero, playerManager);
    }

    @Override
    public final String getPipeNodeID() {
        return null;
    }
}
