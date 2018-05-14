package management.pipeline.pipeNodes.attackPipeNode;

import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.battleManagement.BattleManager;
import management.pipeline.pipeNodes.defaultPipeNode.DefaultPipeNode;
import management.playerManagement.PlayerManager;
import management.service.components.handleComponet.EngineComponent;
import management.service.components.handleComponet.IllegalSwitchOffEngineComponentException;

public final class AttackPipeNode extends DefaultPipeNode {

    private final Hero hero;

    public AttackPipeNode(final Hero hero) {
        this.hero = hero;
        this.engineComponentList.add(new MainAttackEngineComponent(hero));
    }

    @Override
    public final String getPipeNodeID() {
        return "AttackPipeNode";
    }

    @Override
    public final Hero getHero() {
        return this.hero;
    }

    public static final class MainAttackEngineComponent implements EngineComponent {

        private Hero hero;

        private MainAttackEngineComponent(final Hero hero) {
            this.hero = hero;
        }

        @Override
        public final void handle(final ActionEvent actionEvent) {

        }

        @Override
        public final String getName() {
            return "MainAttackEngineComponent";
        }

        @Override
        public final Hero getCurrentHero() {
            return null;
        }

        @Override
        public final boolean isWorking() {
            return false;
        }

        @Override
        public final void setWorking(boolean able) throws IllegalSwitchOffEngineComponentException {

        }

        @Override
        public void setup(ActionManager actionManager, BattleManager battleManager, PlayerManager playerManager, Hero hero) {

        }

        @Override
        public boolean isNeedPass() {
            return false;
        }
    }
}