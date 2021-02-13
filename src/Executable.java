import processing.core.PImage;

import java.util.List;

public abstract class Executable extends Entity
{
    protected int actionPeriod;

    public Executable(Point position, List<PImage> images, int imageIndex, String id, int actionPeriod){
        super(position, images, imageIndex, id);
        this.actionPeriod = actionPeriod;
    }


    abstract void executeActivity(WorldModel world,
                         ImageStore imageStore,
                         EventScheduler scheduler);

    abstract void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);


}
