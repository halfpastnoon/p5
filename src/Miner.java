import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class Miner extends Animatable{

    private int resourceLimit;

    public Miner(Point position, List<PImage> images, int imageIndex, String id, int actionPeriod, int animationPeriod, int resourceLimit){
        super(position, images, imageIndex, id, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() {return resourceLimit;}

    protected Point nextPositionMiner(WorldModel world, Point destPos, PathingStrategy strat)
    {
        Predicate<Point> canPassThrough = p -> !world.isOccupied(p) && world.withinBounds(p);
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;
        List<Point> ret = strat.computePath(getPosition(), destPos, canPassThrough, (p1, p2) -> p1.distanceSquared(p2) <= 1, potentialNeighbors);
        return ret.size() > 0 ? ret.get(0) : this.getPosition();
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
            Point nextPos = this.nextPositionMiner(world, target.getPosition(), new AStarPathingStrategy());

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
