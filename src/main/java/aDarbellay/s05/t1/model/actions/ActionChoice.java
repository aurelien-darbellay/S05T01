package aDarbellay.s05.t1.model.actions;

public class ActionChoice {
    private ActionType actionType;
    private boolean isUsed;

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
