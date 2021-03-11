import processing.core.PImage;

import java.util.List;

public class Factory {
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final String DEFAULT_IMAGE_NAME = "background_default";


    public static Entity createBlacksmith(
            String id, Point position, List<PImage> images)
    {
        return new Blacksmith(position, images, id);
    }

    public static Animatable createMinerFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerFull(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }

    public static Animatable createMinerNotFull(
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

    public static Executable createOre(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, actionPeriod, images);
    }

    public static Animatable createOreBlob(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new OreBlob(position, actionPeriod, animationPeriod, images, id);
    }

    public static Animatable createQuake(
            Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD, images, position, QUAKE_ID);
    }

    public static Entity createVein(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Vein(actionPeriod, id, position, images);
    }

    public static Action createAnimationAction(Animatable entity, int repeatCount) {
        return new Animation(entity, repeatCount);
    }

    public static Action createActivityAction(Executable entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                imageStore.getImageList(
                        DEFAULT_IMAGE_NAME));
    }

    public static Background createBackground(String id, ImageStore imageStore){
        return new Background(id,
                imageStore.getImageList(id));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, PImage.RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }
}

