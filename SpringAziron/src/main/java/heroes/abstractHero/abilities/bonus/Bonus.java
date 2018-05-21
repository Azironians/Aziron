package heroes.abstractHero.abilities.bonus;

import annotations.sourceAnnotations.NonFinal;
import heroes.abstractHero.abilities.Ability;
import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.util.List;

public abstract class Bonus extends Ability {

    protected Bonus(final String name
            , final ImageView mainImage, final ImageView descriptionImage
            , final List<Media> voices, final Media animationSound) {
        super(name, mainImage, descriptionImage, voices, animationSound);
    }

    protected static class GUIHolder extends Ability.GUIHolder {

        protected GUIHolder(final Ability parent, final ImageView mainImage, final ImageView descriptionImage
                , final List<Media> voices, final Media animationSound) {
            super(parent, mainImage, descriptionImage, voices, animationSound);
            //Temporary:
            this.mainImage.setFitHeight(353.0);
            this.mainImage.setFitWidth(309.0);
        }

        @NonFinal
        @Override
        protected void showDescription() {
            super.showDescription();
            this.increaseBonusEntered();
        }

        @NonFinal
        @Override
        protected void hideDescription() {
            super.hideDescription();
            this.decreaseBonusExited();
        }

        //Animation:
        private void increaseBonusEntered() {
            this.mainImage.toFront();
            this.scaleBonus(this.mainImage, 1.8);
        }

        private void decreaseBonusExited() {
            this.mainImage.toBack();
            this.scaleBonus(this.mainImage, 1);
        }

        private void scaleBonus(final ImageView imageView, final double scale) {
            final ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), imageView);
            scaleTransition.setToX(scale);
            scaleTransition.setToY(scale);
            scaleTransition.play();
        }
    }

    @NonFinal
    @Override
    protected Ability.GUIHolder createGUIHolder(final Ability ability, final ImageView mainImage
            , final ImageView descriptionImage, final List<Media> voices, final Media animationSound) {
        return new GUIHolder(ability, mainImage, descriptionImage, voices, animationSound);
    }
}