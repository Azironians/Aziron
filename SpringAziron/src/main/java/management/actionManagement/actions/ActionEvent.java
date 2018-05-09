package management.actionManagement.actions;

import heroes.abstractHero.hero.Hero;

public final class ActionEvent {

    private ActionType actionType;

    private Hero hero;

    private Object data; //everything type of data

    public ActionEvent(final ActionType actionType, final Hero hero, final Object data) {
        this.actionType = actionType;
        this.hero = hero;
        this.data = data;
    }

    public ActionEvent(final ActionType actionType, final Hero hero) {
        this.actionType = actionType;
        this.hero = hero;
    }

    public final ActionType getActionType() {
        return actionType;
    }

    public final Hero getHero() {
        return hero;
    }

    public final Object getData() {
        return data;
    }

    public final void setActionType(final ActionType actionType) {
        this.actionType = actionType;
    }

    public final void setHero(final Hero hero) {
        this.hero = hero;
    }

    public final void setData(final Object data) {
        this.data = data;
    }
}