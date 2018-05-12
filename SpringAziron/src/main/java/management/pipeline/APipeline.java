package management.pipeline;

import com.google.inject.Singleton;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.pipeline.pipes.APipe;

import java.util.*;

@Singleton
public final class APipeline {

    private final Map<String, APipe> pipes = new HashMap<>() {{
        this.put("MainPipe", new APipe());
        this.put("AttackPipe", new APipe());
        this.put("TreatmentPipe", new APipe());
        this.put("SkillPipe", new APipe());
        this.put("BonusPipe", new APipe());
    }};

    public final void push(final ActionEvent actionEvent) {
        final Collection<APipe> pipes = this.pipes.values();
        for (final APipe pipe : pipes) {
            pipe.push(actionEvent);
        }
        for (final APipe pipe: pipes){
            final boolean isNeedPass = pipe.listen();
            if (isNeedPass){
                this.push(ActionEventFactory.getNullableEvent());
            }
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
}