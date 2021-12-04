package pt.iul.poo.firefight.starterpack.behaviours;

import pt.iul.ista.poo.utils.Point2D;

public interface IBurnable {
    
    public boolean isBurning();

    public void setOnFire();

    public int getDefaultBurningFor();

    public int getCurrentBurningFor();

    public void spread(Point2D position);

    public double getChanceOfCatchingFire();
}