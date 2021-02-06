import processing.core.PImage;

import java.util.List;

public class Factory {
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;


    public static Entity createBlacksmith(
            String id, Point position, List<PImage> images)
    {
        return new Blacksmith(position, images, id);
    }

    public static Entity createMinerFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerFull(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public static Entity createMinerNotFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Entity createObstacle(
            String id, Point position, List<PImage> images)
    {
        return new Obstacle(position, images, id);
    }

    public static Entity createOre(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, actionPeriod, images);
    }

    public static Entity createOreBlob(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new OreBlob(position, actionPeriod, animationPeriod, images, id);
    }

    public static Entity createQuake(
            Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD, images, position, QUAKE_ID);
    }

    public static Entity createVein(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Vein(actionPeriod, id, position, images);
    }

    public static Action createAnimationAction(Entity entity, int repeatCount) {
        return new Animation(entity, null, null,
                repeatCount);
    }

    public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }
}

