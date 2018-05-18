package management.pipeline.pipeNodes.skillPipeNode;

import heroes.abstractHero.hero.Hero;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode;
import management.playerManagement.PlayerManager;

public final class SkillPipeNode extends DefaultPipeNode {

    public SkillPipeNode(Hero hero, PlayerManager playerManager, APipeline pipeline) {
        super(hero, playerManager, pipeline);
    }

    @Override
    public String getPipeNodeID() {
        return null;
    }
}
