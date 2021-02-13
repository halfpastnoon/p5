import processing.core.PImage;

import java.util.List;

public class Quake extends Animatable{

//    private int actionPeriod;
//    private int animationPeriod;
//    private List<PImage> images;
//    private int imageIndex;
//    private Point position;
//    private String id; //this was left because it could be useful later although its not being used now

    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(int actionPeriod, int animationPeriod, List<PImage> images, Point position, String id) {
        super(position, images, 0, id, actionPeriod, animationPeriod);
    }

    public int getAnimationPeriod() {
        return animationPeriod;
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
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world,
                                ImageStore imageStore){
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this,
                QUAKE_ANIMATION_REPEAT_COUNT),
                animationPeriod);
    }
}
