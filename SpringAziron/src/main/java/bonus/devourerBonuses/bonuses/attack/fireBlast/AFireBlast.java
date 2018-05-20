package bonus.devourerBonuses.bonuses.attack.fireBlast;

import heroes.abstractHero.abilities.bonus.Bonus;
import javafx.scene.image.ImageView;
import lib.duplexMap.DuplexMap;
import management.actionManagement.actions.ActionEvent;
import management.service.components.handleComponent.EngineComponent;
import management.service.components.handleComponent.IllegalSwitchOffEngineComponentException;
import management.service.engine.services.RegularEngineService;
import management.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

public final class AFireBlast extends Bonus implements RegularEngineService {

    private FireBlastSkillProxyComponent fireBlastSkillProxyComponent;

    public AFireBlast(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        if (fireBlastSkillProxyComponent.packSkill()) {
            wireActionManager(fireBlastSkillProxyComponent.getJustInTimeFireBlastSkill());
        } else {
            fireBlastSkillProxyComponent.invokeSkill(actionManager.getEventEngine(), playerManager);
        }
    }

    private void wireActionManager(final Skill skill){
        skill.setActionManager(actionManager);
    }

    @Override
    public final EngineComponent installSingletonEngineComponent(final Player hero) {
        return new EngineComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = hero;
                fireBlastSkillProxyComponent = new FireBlastSkillProxyComponent(currentPlayer);
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final List<Skill> garbageList = new ArrayList<>();
                for (final Skill skill: currentPlayer.getCurrentHero().getCollectionOfSkills()){
                    if (skill.isReady()){
                        final DuplexMap<Skill, Skill> skillVsProxyMap
                                = fireBlastSkillProxyComponent.getSkillVsProxyMap();
                        final Skill fireBlast = skillVsProxyMap.getProxy(skill);
                        if (fireBlast != null){
                            garbageList.add(fireBlast);
                        }
                    }
                }
                garbageList.forEach(fireBlastSkillProxyComponent::destroy);
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
            public final void setWorking(boolean isWorking) throws IllegalSwitchOffEngineComponentException {
                throw new IllegalSwitchOffEngineComponentException("FireBlast handler " +
                        "component always must work in EventEngine");
            }
        };
    }
}