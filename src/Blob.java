import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class Blob extends Animatable{
    public Blob(Point position, int actionPeriod, int animationPeriod, List<PImage> images, String id){
        super(position, images, 0, id, actionPeriod, animationPeriod);
    }

    protected boolean moveToBlob(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = this.nextPositionBlob(world, target.getPosition(), new AStarPathingStrategy());

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

    private Point nextPositionBlob(WorldModel world, Point destPos, PathingStrategy strat)
    {
        Predicate<Point> canPassThrough = p -> {
            Optional<Entity> occupant = world.getOccupant(p);
            return !(occupant.isPresent() && !(occupant.get().getClass() == Ore.class)) && world.withinBounds(p);
        };
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS::apply;
        List<Point> ret = strat.computePath(getPosition(), destPos, canPassThrough, (p1, p2) -> p1.distanceSquared(p2) <= 1, potentialNeighbors);
        return ret.size() > 0 ? ret.get(0) : this.getPosition();

    }
}
