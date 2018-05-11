package heroes.abstractHero.skills;

import annotations.sourceAnnotations.NonFinal;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;

import java.util.List;

public abstract class ASwapSkill extends ASkill{

    protected ASwapSkill(final String name, final int reload, final int requiredLevel, final List<Double> coefficients
            , final ImageView mainImage, final ImageView descriptionImage, final List<Media> voices
            , final Media animationSound) {
        super(name, reload, requiredLevel, coefficients, mainImage, descriptionImage, voices, animationSound);
    }

    @NonFinal
    protected ActionEvent getActionEvent(){
        return ActionEventFactory.getBeforeUsedSwapSkill(this.parentHero, this);
    }
}