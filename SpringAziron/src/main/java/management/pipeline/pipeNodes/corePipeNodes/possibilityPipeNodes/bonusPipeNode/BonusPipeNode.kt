package management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes.bonusPipeNode;

import heroes.abstractHero.hero.Hero
import management.pipeline.APipeline
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode
import management.playerManagement.PlayerManager

class BonusPipeNode(pipeNodeID: String, hero: Hero, playerManager: PlayerManager, pipeline : APipeline)
    : CorePipeNode(pipeNodeID, hero, playerManager, pipeline) {


}
