import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Miner extends Animatable{

    private int resourceLimit;

    public Miner(Point position, List<PImage> images, int imageIndex, String id, int actionPeriod, int animationPeriod, int resourceLimit){
        super(position, images, imageIndex, id, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    protected int getResourceLimit() {return resourceLimit;}

    protected Point nextPositionMiner(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

    protected boolean moveToMiner(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (_moveHelp(world, target, scheduler)) {
            return true;
        }
        else {
            Point nextPos = this.nextPositionMiner(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    protected boolean transformMiner(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (_transformHelp()) {
            Animatable miner = this.getClass() == MinerNotFull.class ?
                    Factory.createMinerFull(this.getId(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.getImages())
                    :
                    Factory.createMinerNotFull(this.getId(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(),
                    this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore, 0);

            return true;
        }

        return false;
    }

    abstract boolean _moveHelp(WorldModel world,
                               Entity target,
                               EventScheduler scheduler);

    abstract boolean _transformHelp();

}
