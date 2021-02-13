import processing.core.PImage;

import java.util.List;

public abstract class Animatable extends Executable
{
    protected int animationPeriod;

    public Animatable(Point position, List<PImage> images, int imageIndex, String id, int actionPeriod, int animationPeriod){
        super(position, images, imageIndex, id, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    abstract int getAnimationPeriod();
}
