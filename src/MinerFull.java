import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerFull extends Miner
{

    public MinerFull(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int animationPeriod) {
        super(position, images, 0, id, actionPeriod, animationPeriod, resourceLimit);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), Blacksmith.class);

        if (fullTarget.isPresent() && this.moveTo(world,
                fullTarget.get(), scheduler))
        {
            this.transform(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public boolean _moveHelp(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
        if (getPosition().adjacent(target.getPosition())) {
            return true;
        }
       return false;
    }

    public boolean _transformHelp(){
        return true;
    }


}
