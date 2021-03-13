import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class CannibalBlob extends Blob {
    public CannibalBlob(Point position, int actionPeriod, int animationPeriod, List<PImage> images, String id) {
        super(position, actionPeriod, animationPeriod, images, id);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> blobTarget = world.findNearest(getPosition(), Miner.class);
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent()) {
            Point tgt = blobTarget.get().getPosition();

            if(moveToBlob(world, blobTarget.get(), scheduler)) {
                nextPeriod += this.getActionPeriod();
            }
        }
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                nextPeriod);

    }
}

