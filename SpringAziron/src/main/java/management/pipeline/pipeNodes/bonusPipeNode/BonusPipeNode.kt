package management.pipeline.pipeNodes.bonusPipeNode;

import heroes.abstractHero.hero.Hero
import management.pipeline.APipeline
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode
import management.playerManagement.PlayerManager

class BonusPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline : APipeline)
    : DefaultPipeNode(pipeNodeID, hero, playerManager, pipeline) {


}
