import processing.core.PImage;

import java.util.List;

public abstract class Animatable extends Executable {
    private int animationPeriod;
    private int imageIndex;

    public Animatable(Point position, List<PImage> images, int imageIndex, String id, int actionPeriod, int animationPeriod) {
        super(position, images, id, actionPeriod);
        this.animationPeriod = animationPeriod;
        this.imageIndex = imageIndex;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.getImages().size();
    }

    public PImage getCurrentImage(){
        return getImages().get(imageIndex);
    }

    public void scheduleActions(EventScheduler scheduler, //changed method signature so this could be brought up in hierarchy, see scheduleActions() in VirtualWorld
                                WorldModel world,
                                ImageStore imageStore,
                                int repeatCount) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, repeatCount),
                animationPeriod);

    }
}
