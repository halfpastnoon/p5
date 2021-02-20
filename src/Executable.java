import processing.core.PImage;

import java.util.List;

public abstract class Executable extends Entity
{
    protected int actionPeriod;

    public Executable(Point position, List<PImage> images, String id, int actionPeriod){
        super(position, images, id);
        this.actionPeriod = actionPeriod;
    }


    abstract void executeActivity(WorldModel world,
                         ImageStore imageStore,
                         EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world,
                                ImageStore imageStore){
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }


}
