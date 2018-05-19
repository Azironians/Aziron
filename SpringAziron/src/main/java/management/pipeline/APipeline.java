package management.pipeline;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import heroes.abstractHero.hero.Hero;
import management.actionManagement.actions.ActionEvent;
import management.pipeline.pipeNodes.corePipeNodes.attackPipeNode.AttackPipeNode;
import management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes.bonusPipeNode.BonusPipeNode;
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode;
import management.pipeline.pipeNodes.corePipeNodes.mainPipeNode.MainPipeNode;
import management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes.skillPipeNode.SkillPipeNode;
import management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes.swapAbilityPipeNode.SwapAbilityPipeNode;
import management.pipeline.pipeNodes.corePipeNodes.treatmentPipeNode.TreatmentPipeNode;
import management.pipeline.pipes.APipe;
import management.pipeline.pipes.APipeBuilder;
import management.playerManagement.ATeam;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public final class APipeline {

    private Map<String, APipe> pipes = new HashMap<>();

    @Inject
    private APipelineInitializer pipelineInitializer;

    public final void push(final List<ActionEvent> actionEventList) {
        actionEventList.forEach(this::push);
    }

    public final void push(final ActionEvent... actionEvents) {
        for (final ActionEvent actionEvent : actionEvents) {
            this.push(actionEvent);
        }
    }

    public final void push(final ActionEvent actionEvent) {
        final Collection<APipe> pipes = this.pipes.values();
        for (final APipe pipe : pipes) {
            pipe.push(actionEvent);
        }
    }

    public final Map<String, APipe> getPipes() {
        return this.pipes;
    }

    public final APipe getMainPipe() {
        return this.getPipe("MainPipe");
    }

    public final APipe getAttackPipe() {
        return this.getPipe("AttackPipe");
    }

    public final APipe getTreatmentPipe() {
        return this.getPipe("TreatmentPipe");
    }

    public final APipe getSkillPIpe() {
        return this.getPipe("SkillPipe");
    }

    public final APipe getBonusPipe() {
        return this.getPipe("BonusPipe");
    }

    private APipe getPipe(final String pipeID) {
        return this.pipes.get(pipeID);
    }

    public final void install() {
        this.pipelineInitializer.install();
    }

    @Singleton
    private static final class APipelineInitializer {

        @Inject
        private PlayerManager playerManager;

        @Inject
        private APipeBuilder pipeBuilder;

        public final void install() {
            this.installCorePipeNodes(MainPipeNode.class);
            this.installCorePipeNodes(AttackPipeNode.class);
            this.installCorePipeNodes(TreatmentPipeNode.class);
            this.installCorePipeNodes(SkillPipeNode.class);
            this.installCorePipeNodes(SwapAbilityPipeNode.class);
            this.installCorePipeNodes(BonusPipeNode.class);
        }

        private void installCorePipeNodes(final Class<? extends CorePipeNode> clazz) {
            //Get PipeNode -> Pipe:
            final String className = clazz.getName();
            final int withoutNodeWord = 4;
            final String pipeName = className.substring(0, className.length() - withoutNodeWord);
            //Build pipe:
            final APipe pipe = this.pipeBuilder.build(pipeName);
            try {
                final Constructor constructor = clazz.getDeclaredConstructor(Hero.class, PlayerManager.class);
                for (final ATeam team : this.playerManager.getAllTeams()) {
                    for (final Player player : team.getAllPlayers()) {
                        for (final Hero hero : player.getAllHeroes()) {
                            final CorePipeNode newPipeNode = (CorePipeNode) constructor.newInstance(hero
                                    , this.playerManager);
                            pipe.addPipeNode(newPipeNode);
                        }
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}