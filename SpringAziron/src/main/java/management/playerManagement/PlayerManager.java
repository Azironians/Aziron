package management.playerManagement;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.windows.WindowType;
import main.AGame;
import management.battleManagement.BattleManager;
import management.battleManagement.processors.BonusLoadingProcessor;
import org.jetbrains.annotations.Contract;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.SpringManager;

import java.util.*;
import java.util.logging.Logger;

public final class PlayerManager {

    private static final Logger log = Logger.getLogger(PlayerManager.class.getName());

    @Inject
    private AGame aGame;

    @Inject
    private BattleManager battleManager;

    @Inject
    private FictionalTeams fictionalTeams;

    @Inject
    @Named("SpringCore")
    private ClassPathXmlApplicationContext context;

    private Map<String, Player> mapOfPlayers = null;

    private int countPlayers = 0;

    private GameMode gameMode;

    private ATeam rightATeam;

    private ATeam leftATeam;

    private ATeam currentATeam;

    private ATeam opponentATeam;

    //Setters:
    private void setPlayerCount(int countPlayers) {
        this.countPlayers = countPlayers;
        this.mapOfPlayers = new HashMap<>(countPlayers);
    }

    public final void setStartPosition(){
        final Random random = new Random();
        if (random.nextBoolean()) {
            currentATeam = rightATeam;
            opponentATeam = leftATeam;
        } else {
            currentATeam = leftATeam;
            opponentATeam = rightATeam;
        }
        currentATeam.setTurn(0);
        opponentATeam.setTurn(1);
        setAdditionalExperience(opponentATeam);
    }

    public final void start(){
        final SpringManager springManager = (SpringManager) context.getBean("springManager");
        System.out.println(springManager.getSpringEngine().getString());
        currentATeam.launchTimer();
        final BonusLoadingProcessor bonusLoadingProcessor = this.battleManager.getBonusLoadingProcessor();
        bonusLoadingProcessor.setHero(currentATeam.getCurrentPlayer().getCurrentHero());
        bonusLoadingProcessor.process();
    }

    private void setAdditionalExperience(final ATeam team){
        final heroes.abstractHero.hero.Hero hero = team.getCurrentPlayer().getCurrentHero();
        final double equalsAttack =  hero.getAttack();
        log.info("Adding XP: +" + equalsAttack);
        hero.addExperience(equalsAttack);
    }

    public final void setCurrentATeam(ATeam currentATeam) {
        currentATeam.launchTimer();
        this.currentATeam = currentATeam;
    }

    public final void setOpponentATeam(ATeam opponentATeam) {
        opponentATeam.pauseTimer();
        this.opponentATeam = opponentATeam;
    }

    public final void setRightATeam(final ATeam rightATeam) {
        this.rightATeam = rightATeam;
    }

    public final void setLeftATeam(final ATeam leftATeam) {
        this.leftATeam = leftATeam;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        this.rightATeam = new ATeam();
        this.leftATeam = new ATeam();
        switch (gameMode){
            case _1x1:
                setPlayerCount(2);
                set2x2(false);
                break;
            case _2x2:
                setPlayerCount(4);
                set2x2(true);
        }
    }

    private void set2x2(boolean setter) {
//        this.rightATeam.getAlternativePlayer().setAlive(false);
//        this.leftATeam.getAlternativePlayer().setAlive(false);
        final ControllerMatchMaking controllerMatchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        controllerMatchMaking.getLeftLocation().getSwapSkillPane().setVisible(setter);
        controllerMatchMaking.getRightLocation().getSwapSkillPane().setVisible(setter);
    }

    //Getters:
    @Contract(pure = true)
    public final Map<String, Player> getMapOfPlayers() {
        return mapOfPlayers;
    }

    @Contract(pure = true)
    public final int getCountPlayers() {
        return this.countPlayers;
    }

    @Contract(pure = true)
    public final ATeam getRightATeam() {
        return rightATeam;
    }

    @Contract(pure = true)
    public final ATeam getLeftATeam() {
        return leftATeam;
    }

    @Contract(pure = true)
    public final ATeam getCurrentTeam() {
        return currentATeam;
    }

    @Contract(pure = true)
    public final ATeam getOpponentTeam() {
        return opponentATeam;
    }

    public final List<ATeam> getAllTeams() {
        //TODO: Make implementation
        return new ArrayList<>();
    }
}
