import processing.core.PImage;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OreBlob extends Blob{

    private static final String QUAKE_KEY = "quake";
    private static final String CANNIBAL_KEY = "cblob";

    public OreBlob(Point position, int actionPeriod, int animationPeriod, List<PImage> images, String id) {
        super(position, actionPeriod, animationPeriod, images, id);
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

            if (moveToBlob(world, blobTarget.get(), scheduler)) {
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

    public void goNuts(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        Animatable blob = Factory.createCannibalBlob(this.getId(), this.getPosition(),
                this.getActionPeriod()/2, this.getAnimationPeriod(), imageStore.getImageList(CANNIBAL_KEY));

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore, 0);
    }
}
