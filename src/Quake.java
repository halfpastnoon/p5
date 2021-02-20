import processing.core.PImage;

import java.util.List;

public class Quake extends Animatable{

    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(int actionPeriod, int animationPeriod, List<PImage> images, Point position, String id) {
        super(position, images, 0, id, actionPeriod, animationPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

}
