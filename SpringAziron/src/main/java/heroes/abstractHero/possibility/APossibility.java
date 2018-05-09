package heroes.abstractHero.possibility;

import controllers.main.matchmaking.ControllerLocation;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import management.playerManagement.PlayerManager;

import java.util.List;
import java.util.logging.Logger;

public abstract class APossibility {

    private static final Logger log = Logger.getLogger(APossibility.class.getName());

    protected static final int START_OPACITY = 0;

    //Possibility name:
    private final String name;

    //Main characteristics:
    protected int step = 1;

    protected int reload;

    protected int requiredLevel;

    protected List<Double> coefficients;

    //GUI:
    protected ImageView mainImage;

    protected ImageView descriptionImage;

    protected Pane container;

    //Audio:
    private Media animationSound;

    private List<Media> voices;

    //Access:
    private boolean possibilityAccess;

    //Parent:
    protected Hero parentHero;

    protected ControllerLocation controllerLocation;

    //PlayerManager:
    protected PlayerManager playerManager;

    protected APossibility(final String name, final int reload, final int requiredLevel
            , final List<Double> coefficients, final ImageView mainImage, final ImageView descriptionImage
            , final List<Media> voices, final Media animationSound) {
        //Possibility name:
        this.name = name;
        this.reload = reload;
        this.requiredLevel = requiredLevel;
        this.coefficients = coefficients;
        this.possibilityAccess = true;
        //GUI:
        this.initGUI(mainImage, descriptionImage, voices, animationSound);
    }

    private void initGUI(final ImageView mainImage, final ImageView descriptionImage, final List<Media> voices
            , final Media animationSound) {
        mainImage.setOnMouseEntered(event -> this.showDescription());
        mainImage.setOnMouseExited(event -> this.hideDescription());
        mainImage.setOnMouseClicked(event -> this.makePossibilityRequest());
        this.descriptionImage = descriptionImage;
        this.mainImage = mainImage;
        this.voices = voices;
        this.animationSound = animationSound;
    }

    private void makePossibilityRequest() {
        this.controllerLocation.makePossibilityRequest(this.parentHero, this);
    }

    private void hideDescription() {

    }

    private void showDescription() {

    }


    public final void installControllerLocation(final ControllerLocation controllerLocation)
            throws APossibilityInstallationException {
        if (this.controllerLocation == null) {
            this.controllerLocation = controllerLocation;
        } else {
            throw new APossibilityInstallationException("ControllerLocation already installed!");
        }
    }

    public final void installPlayerManager(final PlayerManager playerManager) throws APossibilityInstallationException {
        if (this.playerManager == null) {
            this.playerManager = playerManager;
        } else {
            throw new APossibilityInstallationException("PlayerManager already installed");
        }
    }

    public final void installParentHero(final Hero parentHero) throws APossibilityInstallationException {
        if (this.parentHero == null) {
            this.parentHero = parentHero;
        } else {
            throw new APossibilityInstallationException("Parent hero already installed");
        }
    }

    /**
     * Getters & setters:
     */
    
    public final boolean isPossibilityAccess() {
        return possibilityAccess;
    }

    public final void setPossibilityAccess(final boolean possibilityAccess) {
        this.possibilityAccess = possibilityAccess;
    }

    public final String getName() {
        return this.name;
    }
}