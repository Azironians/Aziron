package management.pipeline;

import com.google.inject.Singleton;
import management.actionManagement.actions.ActionEvent;

import java.util.ArrayList;
import java.util.List;

@Singleton
public final class APipeline {

    private List<APipe> pipes = new ArrayList<>();

    public final void push(final ActionEvent actionEvent) {
        for (final APipe pipe : this.pipes){
            pipe.push(actionEvent);
        }
    }

    public final List<APipe> getPipes() {
        return this.pipes;
    }
}