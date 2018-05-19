package management.pipeline.pipeNodes.corePipeNodes.possibilityPipeNodes;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.possibility.APossibility;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode;
import management.playerManagement.PlayerManager;
import management.service.components.handleComponent.CoreEngineComponent;

public abstract class PossibilityPipeNode extends CorePipeNode {

    public PossibilityPipeNode(final String pipeNodeID, final Hero hero, final PlayerManager playerManager
            , final APipeline pipeline) {
        super(pipeNodeID, hero, playerManager, pipeline);
        this.engineComponentList.add(this.getCoreBeforeUsedPossibilityEventEngine());
    }

    protected abstract CoreBeforeUsedPossibilityEventEngine getCoreBeforeUsedPossibilityEventEngine();

    public static abstract class CoreBeforeUsedPossibilityEventEngine extends CoreEngineComponent {

        public CoreBeforeUsedPossibilityEventEngine(final String name, final Hero hero, final APipeline pipeline
                , final PlayerManager playerManager) {
            super(name, hero, pipeline, playerManager);
        }

        protected abstract ActionType getCheckedBeforeActionType();

        protected abstract Class<? extends APossibility> getActionEventDataFieldClass();

        protected abstract ActionType getDuringActionType();

        @Override
        public final void handle(final ActionEvent actionEvent) {
            if (this.checkEventAndHero(actionEvent, this.getCheckedBeforeActionType())) {
                final Object data = actionEvent.getData();
                if (this.checkData(data, this.getActionEventDataFieldClass())) {
                    final APossibility possibility = this.getActionEventDataFieldClass().cast(data);
                    this.pipeline.push(new ActionEvent(this.getDuringActionType(), this.hero, possibility));
                }
            }
        }
    }
}