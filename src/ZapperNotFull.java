import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class ZapperNotFull extends Zapper{
    private int resourceCount;

    public ZapperNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(position, images, 0, id, actionPeriod, animationPeriod, resourceLimit);
        this.resourceCount = resourceCount;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget =
                world.findNearest(this.getPosition(), MinerFull.class);

        if (!notFullTarget.isPresent() || !moveToMiner(world,
                notFullTarget.get(),
                scheduler)
                || !this.transformZapper(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean _moveHelp(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
        Miner taarget = (Miner) target;
        Animatable zapped = Factory.createMinerNotFull(target.getId(), taarget.getResourceLimit(), target.getPosition(), taarget.getActionPeriod(), taarget.getAnimationPeriod(), target.getImages()); //turns miner into notFull to avoid being targeted after zapping
        if (getPosition().adjacent(target.getPosition())) {
            resourceCount++;
            world.removeEntity(target);
            world.addEntity(zapped);
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
