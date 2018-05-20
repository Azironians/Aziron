package controllers.main.matchmaking;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import management.playerManagement.Team;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class ControllerLeftLocation extends ControllerLocation implements Initializable {

    private static final Logger log = Logger.getLogger(ControllerLeftLocation.class.getName());

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        //While empty...
    }

    public final void imageHeroOnClicked() {
        final Team leftATeam = playerManager.getLeftATeam();
        makeHeroRequest(leftATeam);
    }

    public final void changeHeroOnClicked() {
        final Team leftTeam = playerManager.getLeftATeam();
        trySwapHeroRequest(leftTeam);
    }

    @Override
    final Pane getHeroCollectionPane() {
        return null;
    }
}