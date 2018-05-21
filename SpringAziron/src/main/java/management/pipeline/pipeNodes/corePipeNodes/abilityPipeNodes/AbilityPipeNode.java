package management.pipeline.pipeNodes.corePipeNodes.abilityPipeNodes;

import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.abilities.Ability;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionType;
import management.pipeline.APipeline;
import management.pipeline.pipeNodes.corePipeNodes.CorePipeNode;
import management.playerManagement.PlayerManager;
import management.service.components.handleComponent.CoreEngineComponent;

public abstract class AbilityPipeNode extends CorePipeNode {

    public AbilityPipeNode(final String pipeNodeID, final Hero hero, final PlayerManager playerManager
            , final APipeline pipeline) {
        super(pipeNodeID, hero, playerManager, pipeline);
        this.engineComponentList.add(this.getCoreBeforeUsedAbilityEventEngine());
    }

    protected abstract CoreBeforeUsedAbilityEngineComponent getCoreBeforeUsedAbilityEventEngine();

    public static abstract class CoreBeforeUsedAbilityEngineComponent extends CoreEngineComponent {

        public CoreBeforeUsedAbilityEngineComponent(final String name, final Hero hero, final APipeline pipeline
                , final PlayerManager playerManager) {
            super(name, hero, pipeline, playerManager);
        }

        protected abstract ActionType getCheckedBeforeActionType();

        protected abstract Class<? extends Ability> getActionEventDataFieldClass();

        protected abstract ActionType getDuringActionType();

        protected abstract ActionType getAfterActionType();

        @Override
        public final void handle(final ActionEvent actionEvent) {
            if (this.checkEventAndHero(actionEvent, this.getCheckedBeforeActionType())) {
                final Object data = actionEvent.getData();
                if (this.checkData(data, this.getActionEventDataFieldClass())) {
                    final Ability ability = this.getActionEventDataFieldClass().cast(data);
                    this.pipeline.push(new ActionEvent(this.getDuringActionType(), this.hero, ability));
                    this.pipeline.push(new ActionEvent(this.getAfterActionType(), this.hero, ability));
                }
            }
        }
    }
}