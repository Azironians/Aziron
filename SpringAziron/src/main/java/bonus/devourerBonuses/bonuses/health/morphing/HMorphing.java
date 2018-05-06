package bonus.devourerBonuses.bonuses.health.morphing;

import bonus.bonuses.ExtendedBonus;
import bonus.bonuses.service.annotations.Engine;
import bonus.bonuses.service.annotations.implementations.QuestEngine;
import bonus.bonuses.service.parameterType.ParameterType;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import management.actionManagement.actions.ActionEvent;
import management.playerManagement.Player;
import management.service.components.handleComponet.EngineComponent;
import management.service.components.handleComponet.IllegalSwitchOffEngineComponentException;
import management.service.engine.services.RegularEngineService;

import java.util.ArrayList;
import java.util.List;

import static bonus.devourerBonuses.bonuses.health.morphing.HMorphing.MorphingEngineComponent.END_COUNT;
import static bonus.devourerBonuses.bonuses.health.morphing.HMorphing.MorphingEngineComponent.START_COUNT;

public final class HMorphing extends ExtendedBonus implements RegularEngineService {

    private MorphingEngineComponent morphingEngineComponent;

    private Text text;

    public HMorphing(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.text = new Text(START_COUNT + "/" + END_COUNT);
        this.installContainer(this.text);
    }

    @Override
    public final void use() {
        this.morphingEngineComponent.use();
    }

    @Override
    public final EngineComponent installSingletonEngineComponent(final Player player) {
        if (this.morphingEngineComponent == null){
            this.morphingEngineComponent = new MorphingEngineComponent(player);
        }
        return this.morphingEngineComponent;
    }

    @Override
    public final EngineComponent getSingletonEngineComponent() {
        return this.morphingEngineComponent;
    }

    @QuestEngine(engine = @Engine(name = "count", parameterType = ParameterType.VALUE))
    final class MorphingEngineComponent implements EngineComponent {

        static final int START_COUNT = 0;

        static final int END_COUNT = 7;

        private int count = START_COUNT;

        private final Hero hero;

        private MorphingEngineComponent(final Player player) {
            this.hero = player.getCurrentHero();
        }

        private void use() {
            if (this.count + 1 >= END_COUNT) {
                this.changeRegenerationSkills();
                this.count = START_COUNT;
            } else {
                this.count++;
                text.setText(this.count + "/" + END_COUNT);
            }
        }

        private void changeRegenerationSkills() {
            final List<Skill> skills = this.hero.getCollectionOfSkills();
            for (int i = 0; i < skills.size(); i++) {
                if (skills.get(i).getName().equals("Regeneration")) {
                    skills.set(i, getMorphingSkillInstance());
                }
            }
        }

        private Skill getMorphingSkillInstance() {
            return new MorphingSkill(new ImageView(), new ImageView(), new ArrayList<>());
        }

        @Override
        public final void setup() {
            //Nothing...
        }

        @Override
        public final void handle(final ActionEvent actionEvent) {
            //Nothing...
        }

        @Override
        public final String getName() {
            return "Morphing";
        }

        @Override
        public final Hero getCurrentHero() {
            return this.hero;
        }

        @Override
        public final boolean isWorking() {
            return true;
        }

        @Override
        public final void setWorking(final boolean able) throws IllegalSwitchOffEngineComponentException {
            throw new IllegalSwitchOffEngineComponentException("MorphingEngine");
        }
    }
}