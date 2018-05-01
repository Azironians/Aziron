package management.actionManagement.service.engine;

import bonus.bonuses.Bonus;
import management.actionManagement.service.components.handleComponet.HandleComponent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.actionManagement.service.engine.services.RegularHandleService;
import management.battleManagement.BattleManager;
import management.playerManagement.GameMode;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public final class EventEngine {

    private static final Logger log = Logger.getLogger(EventEngine.class.getName());

    private static final ActionEvent EMPTY_EVENT = new ActionEvent(null, null, null);

    @Inject
    private ActionManager actionManager;

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    private List<HandleComponent> bonusHandlers;

    private boolean repeatHandling = false;

    public final void install() {
        this.bonusHandlers = Collections.synchronizedList(new ArrayList<>());
        install(playerManager.getLeftATeam().getCurrentPlayer());
        install(playerManager.getRightATeam().getCurrentPlayer());
        if (playerManager.getGameMode() == GameMode._2x2) {
            install(playerManager.getLeftATeam().getAlternativePlayer());
            install(playerManager.getRightATeam().getAlternativePlayer());
        }
        log.info("EventEngine installing was successful!");
    }

    private void install(final Player player) {
        final List<Bonus> collection = player.getCurrentHero().getBonusCollection();
        for (final Bonus bonus : collection) {
            wireManagersToBonus(bonus, actionManager, battleManager, playerManager);
            if (implementsRegularHandleService(bonus)) {
                final RegularHandleService regularHandleService = (RegularHandleService) bonus;
                addHandler(regularHandleService.getRegularHandlerInstance(player));
            }
        }
    }

    private void wireManagersToBonus(final Bonus bonus
            , final ActionManager actionManager
            , final BattleManager battleManager
            , final PlayerManager playerManager) {
        bonus.setActionManager(actionManager);
        bonus.setBattleManager(battleManager);
        bonus.setPlayerManager(playerManager);
    }

    private boolean implementsRegularHandleService(final Bonus bonus) {
        final Class<?>[] interfaces = bonus.getClass().getInterfaces();
        for (final Class clazz : interfaces) {
            if (clazz.equals(RegularHandleService.class)) {
                log.info(bonus.getName() + " implements RegularHandleService");
                return true;
            }
        }
        return false;
    }

    public synchronized final void handle(final ActionEvent actionEvent) {
        this.repeatHandling = false;
        final List<HandleComponent> garbageHandlerList = new ArrayList<>();
        for (final HandleComponent bonusHandler : bonusHandlers) {
            if (bonusHandler.isWorking()) {
                bonusHandler.handle(actionEvent);
            } else {
                garbageHandlerList.add(bonusHandler);
                log.info(bonusHandler.getName() + " successfully was removed");
            }
        }
        bonusHandlers.removeAll(garbageHandlerList);
        if (repeatHandling){
            handle();
        }
    }

    public final void handle() {
        handle(EMPTY_EVENT);
    }

    public final void addHandler(final HandleComponent handler) {
        handler.setup();
        this.bonusHandlers.add(handler);
    }

    public boolean isRepeatHandling() {
        return repeatHandling;
    }

    public void setRepeatHandling(boolean repeatHandling) {
        this.repeatHandling = repeatHandling;
    }
}