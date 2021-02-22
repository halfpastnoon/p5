import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends Miner{
    private int resourceCount;

    public MinerNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(position, images, 0, id, actionPeriod, animationPeriod, resourceLimit);
        this.resourceCount = resourceCount;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget =
                world.findNearest(this.getPosition(), Ore.class);

        if (!notFullTarget.isPresent() || !moveTo(world,
                notFullTarget.get(),
                scheduler)
                || !this.transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public boolean _moveHelp(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
        if (getPosition().adjacent(target.getPosition())) {
            resourceCount++;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        return false;
    }

    public boolean _transformHelp(){
        if(this.resourceCount >= this.getResourceLimit()){
            return true;
        }
        return false;
    }


}
