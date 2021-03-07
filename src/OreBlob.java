import processing.core.PImage;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OreBlob extends Animatable{

    private static final String QUAKE_KEY = "quake";

    public OreBlob(Point position, int actionPeriod, int animationPeriod, List<PImage> images, String id) {
        super(position, images, 0, id, actionPeriod, animationPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> blobTarget =
                world.findNearest(this.getPosition(), Vein.class);
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (moveToOreBlob(world, blobTarget.get(), scheduler)) {
                Animatable quake = Factory.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore, VirtualWorld.QUAKE_ANIMATION_REPEAT_COUNT);
            }
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                nextPeriod);
    }

    private boolean moveToOreBlob(
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
            Point nextPos = this.nextPositionOreBlob(world, target.getPosition(), new AStarPathingStrategy());

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

    private Point nextPositionOreBlob(WorldModel world, Point destPos, PathingStrategy strat)
    {
//        int horiz = Integer.signum(destPos.x - this.getPosition().x);
//        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
//
//        Optional<Entity> occupant = world.getOccupant(newPos);
//
//        if (horiz == 0 || (occupant.isPresent() && !(occupant.get().getClass()
//                == Ore.class)))
//        {
//            int vert = Integer.signum(destPos.y - this.getPosition().y);
//            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
//            occupant = world.getOccupant(newPos);
//
//            if (vert == 0 || (occupant.isPresent() && !(occupant.get().getClass()
//                    == Ore.class)))
//            {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;
        Predicate<Point> canPassThrough = p -> {
            Optional<Entity> occupant = world.getOccupant(p);
            return !(occupant.isPresent() && !(occupant.get().getClass() == Ore.class)) && world.withinBounds(p);
        };
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS::apply;
        List<Point> ret = strat.computePath(getPosition(), destPos, canPassThrough, (p1, p2) -> p1.distanceSquared(p2) <= 1, potentialNeighbors);
        return ret.size() > 0 ? ret.get(0) : this.getPosition();

    }
}
