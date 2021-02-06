import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein implements Entity, Executable{
    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final Random rand = new Random();

    private int actionPeriod;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Vein(int actionPeriod, String id, Point position, List<PImage> images){
        this.actionPeriod = actionPeriod;
        this.id = id;
        this.position = position;
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
        Optional<Point> openPt = world.findOpenAround(position);

        if (openPt.isPresent()) {
            Entity ore = Factory.createOre(ORE_ID_PREFIX + this.id, openPt.get(),
                    ORE_CORRUPT_MIN + rand.nextInt(
                            ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(Parse.ORE_KEY));
            world.addEntity(ore);
            ((Executable)ore).scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world,
                                ImageStore imageStore){
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }

}
