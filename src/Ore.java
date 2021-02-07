import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Ore implements Entity, Executable{

    private String id;
    private Point position;
    private int actionPeriod;
    private List<PImage> images;
    private int imageIndex;

    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;
    private static final Random rand = new Random();

    public Ore(String id, Point position, int actionPeriod, List<PImage> images){
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.images = images;
        this.imageIndex = 0;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point p){
        this.position = p;
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public PImage getCurrentImage(){
        return images.get(imageIndex);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Point pos = this.position;

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Entity blob = Factory.createOreBlob(this.id + BLOB_ID_SUFFIX, pos,
                this.actionPeriod / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(
                        BLOB_ANIMATION_MAX
                                - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        ((Executable)blob).scheduleActions(scheduler, world, imageStore);
    }

    public void scheduleActions(EventScheduler scheduler,
                               WorldModel world,
                               ImageStore imageStore){
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

}