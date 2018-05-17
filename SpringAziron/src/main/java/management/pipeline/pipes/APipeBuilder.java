package management.pipeline.pipes;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import management.pipeline.APipeline;
import management.playerManagement.PlayerManager;

@Singleton
public final class APipeBuilder {

    @Inject
    private APipeline pipeline;

    @Inject
    private PlayerManager playerManager;

    public final APipe build(final String pipeName){
        final APipe pipe = new APipe(pipeName, this.pipeline, this.playerManager);
        this.pipeline.getPipes().put(pipeName, pipe);
        return pipe;
    }
}