package pt.iul.poo.firefight.starterpack.behaviours;

import pt.iul.poo.firefight.starterpack.BurningState;

public interface IBurnable {
    
    public boolean isBurning();

    public void setOnFire();

    public void putFireOut(BurningState state);

    public int getDefaultBurningFor();

    public int getCurrentBurningFor();

    public double getChanceOfCatchingFire();
}