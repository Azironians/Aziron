package bonus.devourerBonuses.bonuses.attack.hybridisation;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import lib.duplexMap.DuplexMap;
import management.actionManagement.actions.ActionEvent;
import management.service.components.handleComponent.EngineComponent;
import management.service.components.handleComponent.IllegalSwitchOffEngineComponentException;
import management.service.engine.services.RegularEngineService;
import management.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

public final class AHybridisation extends Bonus implements RegularEngineService {

    private HybridisationSkillProxyComponent hybridisationSkillProxyComponent;

    public AHybridisation(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        if (hybridisationSkillProxyComponent.packSkill()) {
            wireActionManager(hybridisationSkillProxyComponent.getJustInTimeHybridisationSkill());
        }
    }

    private void wireActionManager(final Skill skill) {
        skill.setActionManager(actionManager);
    }

    @Override
    public final EngineComponent installSingletonEngineComponent(final Player hero) {
        return new EngineComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = hero;
                hybridisationSkillProxyComponent = new HybridisationSkillProxyComponent(currentPlayer);
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final List<Skill> garbageList = new ArrayList<>();
                for (final Skill skill : currentPlayer.getCurrentHero().getCollectionOfSkills()) {
                    if (skill.isReady()) {
                        final DuplexMap<Skill, Skill> skillVsProxyMap
                                = hybridisationSkillProxyComponent.getSkillVsProxyMap();
                        final Skill hybridisation = skillVsProxyMap.getProxy(skill);
                        if (hybridisation != null) {
                            garbageList.add(hybridisation);
                        }
                    }
                }
                garbageList.forEach(hybridisationSkillProxyComponent::destroy);
            }

            @Override
            public final String getName() {
                return "FireBlast";
            }

            @Override
            public final Player getCurrentHero() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setWorking(boolean able)
                    throws IllegalSwitchOffEngineComponentException {
                throw new IllegalSwitchOffEngineComponentException("Hybridisation " +
                        "handler " +
                        "component always must work in EventEngine");
            }
        };
    }
}