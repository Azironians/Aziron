package heroes.abstractHero.abilities;

import annotations.sourceAnnotations.NonFinal;
import controllers.main.matchmaking.ControllerLocation;
import heroes.abstractHero.hero.Hero;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Duration;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.actions.ActionEventFactory;
import management.playerManagement.PlayerManager;

import java.util.List;
import java.util.logging.Logger;

public abstract class Ability {

    private static final Logger log = Logger.getLogger(Ability.class.getName());

    //Ability name:
    private final String name;

    //Access:
    private boolean abilityAccess;

    //Parent:
    protected Hero parentHero;

    protected ControllerLocation controllerLocation;

    //PlayerManager:
    protected PlayerManager playerManager;

    protected Ability(final String name
            , final ImageView mainImage, final ImageView descriptionImage
            , final List<Media> voices, final Media animationSound) {
        //Possibility name:
        this.name = name;
        this.abilityAccess = true;
        //GUI:
        this.guiHolder = this.createGUIHolder(this, mainImage, descriptionImage, voices, animationSound);
    }

    private void makeAbilityRequest() {
        this.controllerLocation.makeAbilityRequest(this.getActionEvent());
    }

    @NonFinal
    protected ActionEvent getActionEvent() {
        return ActionEventFactory.getBeforeUsedPossibility(this.parentHero, this);
    }

    //GUI:
    protected GUIHolder guiHolder;

    @NonFinal
    public static class GUIHolder {

        protected static final int START_OPACITY = 0;

        protected ImageView mainImage;

        protected ImageView descriptionImage;

        protected Pane container;

        //Audio:
        private Media animationSound;

        private List<Media> voices;

        protected GUIHolder(final Ability parent, final ImageView mainImage, final ImageView descriptionImage
                , final List<Media> voices, final Media animationSound) {
            mainImage.setOnMouseEntered(event -> this.showDescription());
            mainImage.setOnMouseExited(event -> this.hideDescription());
            mainImage.setOnMouseClicked(event -> parent.makeAbilityRequest());
            this.descriptionImage = descriptionImage;
            this.mainImage = mainImage;
            this.voices = voices;
            this.animationSound = animationSound;
        }

        public final void bindToLocation(final Pane parentPane, final double mainImageX, final double mainImageY
                , final double descriptionX, final double descriptionY, final boolean invert) {
            this.descriptionImage.setLayoutY(descriptionY); //-127
            this.descriptionImage.setOpacity(START_OPACITY);
            //init mainImage:
            final int inversion = invert ? -1 : 1;
            this.container = new Pane() {{
                final ObservableList<Node> elements = getChildren();
                this.setScaleX(inversion);
                this.setLayoutX(mainImageX);
                this.setLayoutY(mainImageY);
                elements.add(mainImage);
            }};
            parentPane.getChildren().add(this.container);
        }

        @NonFinal
        protected void showDescription() {
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), this.descriptionImage);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }

        @NonFinal
        protected void hideDescription() {
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), this.descriptionImage);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }

        @NonFinal
        public void showAnimation() {
        }

        public final Pane getContainer() {
            return this.container;
        }

        public final Media getAnimationSound() {
            return this.animationSound;
        }

        public final List<Media> getVoices() {
            return this.voices;
        }

        public final ImageView getMainImage() {
            return this.mainImage;
        }
    }

    public final void installControllerLocation(final ControllerLocation controllerLocation)
            throws AbilityInstallationException {
        if (this.controllerLocation == null) {
            this.controllerLocation = controllerLocation;
        } else {
            throw new AbilityInstallationException("ControllerLocation already installed!");
        }
    }

    public final void installPlayerManager(final PlayerManager playerManager) throws AbilityInstallationException {
        if (this.playerManager == null) {
            this.playerManager = playerManager;
        } else {
            throw new AbilityInstallationException("PlayerManager already installed");
        }
    }

    public final void installParentHero(final Hero parentHero) throws AbilityInstallationException {
        if (this.parentHero == null) {
            this.parentHero = parentHero;
        } else {
            throw new AbilityInstallationException("Parent hero already installed");
        }
    }

    /**
     * Getters & setters:
     */

    public final boolean isAbilityAccess() {
        return this.abilityAccess;
    }

    public final void setAbilityAccess(final boolean abilityAccess) {
        this.abilityAccess = abilityAccess;
    }

    public final String getName() {
        return this.name;
    }

    public final Hero getParentHero() {
        return this.parentHero;
    }

    @NonFinal
    protected GUIHolder createGUIHolder(final Ability ability, final ImageView mainImage
            , final ImageView descriptionImage, final List<Media> voices, final Media animationSound){
        return new GUIHolder(ability, mainImage, descriptionImage, voices, animationSound);
    }

    public final GUIHolder getGuiHolder() {
        return this.guiHolder;
    }

    public final void setGuiHolder(final GUIHolder guiHolder) {
        this.guiHolder = guiHolder;
    }

    @NonFinal
    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                '}';
    }
}