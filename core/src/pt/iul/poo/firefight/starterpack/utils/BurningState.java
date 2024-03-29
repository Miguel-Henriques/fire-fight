package pt.iul.poo.firefight.starterpack.utils;

public enum BurningState {
    NORMAL(""), BURNING(""), BURNT("burnt");

    private String state;

    private BurningState(String state) {
        this.state=state;
    }

    public String getState() {
        return state;
    }
}