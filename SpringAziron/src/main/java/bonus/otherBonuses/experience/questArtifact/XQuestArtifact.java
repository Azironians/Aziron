package bonus.otherBonuses.experience.questArtifact;

import bonus.bonuses.ExtendedBonus;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import management.service.components.handleComponent.EngineComponent;
import management.service.engine.services.RegularEngineService;

import static bonus.otherBonuses.experience.questArtifact.QuestArtifactEngineComponent.END_PROGRESS;
import static bonus.otherBonuses.experience.questArtifact.QuestArtifactEngineComponent.START_PROGRESS;

public final class XQuestArtifact extends ExtendedBonus implements RegularEngineService {

    private QuestArtifactEngineComponent questArtifactEngineComponent;

    private Text text;

    public XQuestArtifact(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
        this.text = new Text(START_PROGRESS + "/" + END_PROGRESS);
        this.installContainer(this.text);
    }

    @Override
    public final void use() {
        this.questArtifactEngineComponent.use();
        this.text.setText(this.questArtifactEngineComponent.progress + "/"
                + this.questArtifactEngineComponent.endProgress);
    }

    @Override
    public final EngineComponent installSingletonEngineComponent(final Hero hero) {
        if (this.questArtifactEngineComponent == null){
            this.questArtifactEngineComponent = new QuestArtifactEngineComponent();
            this.questArtifactEngineComponent.setup(this.actionManager, this.battleManager, this.playerManager, hero);
        }
        return this.questArtifactEngineComponent;
    }

    @Override
    public final EngineComponent getSingletonEngineComponent() {
        return this.questArtifactEngineComponent;
    }
}