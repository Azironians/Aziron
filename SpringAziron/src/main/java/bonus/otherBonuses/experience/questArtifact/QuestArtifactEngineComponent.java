package bonus.otherBonuses.experience.questArtifact;

import bonus.bonuses.service.annotations.EngineField;
import bonus.bonuses.service.annotations.implementations.QuestEngine;
import bonus.bonuses.service.parameterType.ParameterType;
import heroes.abstractHero.hero.Hero;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.battleManagement.BattleManager;
import management.playerManagement.PlayerManager;
import management.service.components.handleComponet.EngineComponent;
import management.service.components.handleComponet.IllegalSwitchOffEngineComponentException;
import management.service.engine.EventEngine;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;


@QuestEngine(engineProgress = @EngineField(fieldName = "progress", parameterType = ParameterType.VALUE)
        , engineEndProgress = @EngineField(fieldName = "endProgress", parameterType = ParameterType.VALUE)
        , engineStep = @EngineField(fieldName = "stepProgress", parameterType = ParameterType.VALUE))
public final class QuestArtifactEngineComponent implements EngineComponent {

    private static final int STEP_BOOST = 1;

    private static final int START_STEP_PROGRESS = 1;

    static final int START_PROGRESS = 0;

    static final int END_PROGRESS = 5;

    private Hero hero;

    private ActionManager actionManager;

    private BattleManager battleManager;

    private PlayerManager playerManager;

    private final ParameterType parameterType = ParameterType.VALUE;

    int progress = START_PROGRESS;

    int endProgress = END_PROGRESS;

    int stepProgress = START_STEP_PROGRESS;

    @Override
    public final void setup(final ActionManager actionManager, final BattleManager battleManager
            , final PlayerManager playerManager, Hero hero) {
        this.actionManager = actionManager;
        this.battleManager = battleManager;
        this.playerManager = playerManager;
        this.hero = hero;
    }

    public final void use() {
        if (this.progress + this.stepProgress >= this.endProgress) {
            this.addAllProgress();
            this.progress = START_PROGRESS;
        } else {
            this.progress += this.stepProgress;
        }
    }

    private void addAllProgress() {
        final heroes.abstractHero.hero.Hero hero = this.playerManager.getCurrentTeam().getCurrentPlayer().getCurrentHero();
        final EventEngine eventEngine = this.actionManager.getEventEngine();
        final Set<EngineComponent> engineComponents = eventEngine.getHandlers();
        for (final EngineComponent engineComponent : engineComponents) {
            if (hero == engineComponent.getCurrentHero() && this != engineComponent) {
                final Class engineComponentClass = engineComponent.getClass();
                if (engineComponentClass.isAnnotationPresent(QuestEngine.class)) {
                    final QuestEngine questEngineAnnotation = (QuestEngine) engineComponentClass
                            .getAnnotation(QuestEngine.class);
                    final EngineField engineFieldAnnotation = questEngineAnnotation.engineProgress();
                    if (this.parameterType == engineFieldAnnotation.parameterType()) {
                        final String fieldName = engineFieldAnnotation.fieldName();
                        final Field field = ReflectionUtils.findField(engineComponentClass, fieldName);
                        ReflectionUtils.makeAccessible(field);
                        final int count = (int) ReflectionUtils.getField(field, engineComponent);
                        ReflectionUtils.setField(field, engineComponent, count + STEP_BOOST);
                    }
                }
            }
        }
    }

    @Override
    public final void handle(final ActionEvent actionEvent) {
        //Nothing...
    }

    @Override
    public final String getName() {
        return "QuestArtifact";
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
    public final void setWorking(boolean able) throws IllegalSwitchOffEngineComponentException {
        throw new IllegalSwitchOffEngineComponentException("QuestArtifact");
    }
}