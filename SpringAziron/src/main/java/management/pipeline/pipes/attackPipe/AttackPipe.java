package management.pipeline.pipes.attackPipe;

import management.pipeline.pipeNodes.attackPipeNode.AttackPipeNode;
import management.pipeline.pipes.APipe;

public final class AttackPipe extends APipe {

    public AttackPipe() {
        this.pipeNodes.add(new AttackPipeNode());
    }
}
