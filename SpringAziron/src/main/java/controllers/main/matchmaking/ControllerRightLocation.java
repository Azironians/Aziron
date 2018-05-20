package controllers.main.matchmaking;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import management.playerManagement.Team;

import java.net.URL;
import java.util.ResourceBundle;

public final class ControllerRightLocation extends ControllerLocation implements Initializable {

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        //While empty...
    }

    public final void imageHeroOnClicked() {
        final Team rightATeam = playerManager.getRightATeam();
        makeHeroRequest(rightATeam);
    }

    public final void changeHeroOnClicked(){
        final Team rightATeam = playerManager.getRightATeam();
        trySwapHeroRequest(rightATeam);
    }

    @Override
    final Pane getHeroCollectionPane() {
        return null;
    }
}