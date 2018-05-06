package management.service.engine;

import bonus.bonuses.Bonus;
import management.service.components.handleComponet.EngineComponent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import management.actionManagement.ActionManager;
import management.actionManagement.actions.ActionEvent;
import management.service.engine.services.RegularEngineService;
import management.battleManagement.BattleManager;
import management.playerManagement.GameMode;
import management.playerManagement.Player;
import management.playerManagement.PlayerManager;

import java.util.*;
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

    private Set<EngineComponent> handlers;

    private boolean repeatHandling = false;

    public final void install() {
        this.handlers = Collections.synchronizedSet(new HashSet<>());
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
                final RegularEngineService regularEngineService = (RegularEngineService) bonus;
                this.addHandler(regularEngineService.installSingletonEngineComponent(player.getCurrentHero()));
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
            if (clazz.equals(RegularEngineService.class)) {
                log.info(bonus.getName() + " implements RegularHandleService");
                return true;
            }
        }
        return false;
    }

    public synchronized final void handle(final ActionEvent actionEvent) {
        this.repeatHandling = false;
        final List<EngineComponent> garbageHandlerList = new ArrayList<>();
        for (final EngineComponent bonusHandler : this.handlers) {
            if (bonusHandler.isWorking()) {
                bonusHandler.handle(actionEvent);
            } else {
                garbageHandlerList.add(bonusHandler);
                log.info(bonusHandler.getName() + " successfully was removed");
            }
        }
        this.handlers.removeAll(garbageHandlerList);
        if (this.repeatHandling) {
            this.handle();
        }
    }

    public synchronized final void handle() {
        this.handle(EMPTY_EVENT);
    }

    public synchronized final void handle(final List<ActionEvent> actionEvents) {
        for (final ActionEvent actionEvent : actionEvents){
            this.handle(actionEvent);
        }
    }

    public final void addHandler(final EngineComponent handler) {
        this.handlers.add(handler);
    }

    public final boolean containsHandler(final EngineComponent engineComponent) {
        return this.handlers.contains(engineComponent);
    }

    public final boolean containsHandler(final String name) {
        for (final EngineComponent engineComponent : this.handlers) {
            if (engineComponent.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public final Set<EngineComponent> getHandlers(){
        return this.handlers;
    }

    public boolean isRepeatHandling() {
        return this.repeatHandling;
    }

    public void setRepeatHandling(final boolean repeatHandling) {
        this.repeatHandling = repeatHandling;
    }
}