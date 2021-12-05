package pt.iul.poo.firefight.starterpack.interfaces;

import pt.iul.poo.firefight.starterpack.props.Fire;
import pt.iul.poo.firefight.starterpack.utils.BurningState;

public interface IBurnable {
    
    public boolean isBurning();

    public void trySetOnFire();

    public void setOnFire(Fire fire);

    public void putFireOut(BurningState state);

    public int getDefaultBurningFor();

    public int getCurrentBurningFor();

    /**
     * Given as % probability
     * @return
     */
    public int getChanceOfCatchingFire();
}