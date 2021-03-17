import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class ZapperFull extends Zapper
{

    public ZapperFull(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int animationPeriod) {
        super(position, images, 0, id, actionPeriod, animationPeriod, resourceLimit);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), Blacksmith.class);

        if (fullTarget.isPresent() && this.moveToMiner(world,
                fullTarget.get(), scheduler))
        {
            this.transformZapper(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
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
