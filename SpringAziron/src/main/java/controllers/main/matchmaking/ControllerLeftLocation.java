package controllers.main.matchmaking;

import javafx.fxml.Initializable;
import management.playerManagement.ATeam;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class ControllerLeftLocation extends ControllerLocation implements Initializable {

    private static final Logger log = Logger.getLogger(ControllerLeftLocation.class.getName());

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {

    }

    public final void imageHeroOnClicked() {
        final ATeam leftATeam = playerManager.getLeftATeam();
        makeHeroRequest(leftATeam);
    }

    public final void changeHeroOnClicked(){
        final ATeam leftTeam = playerManager.getLeftATeam();
        makeSwapHeroRequest(leftTeam);
    }
}
