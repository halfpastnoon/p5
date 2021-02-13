import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public abstract class Entity
{
    protected Point position;
    protected List<PImage> images;
    protected int imageIndex;
    protected String id;

    public Entity(Point position, List<PImage> images, int imageIndex, String id) {
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
        this.id = id;
    }

    abstract Point getPosition();
    abstract void setPosition(Point p);
    abstract void nextImage();
    abstract PImage getCurrentImage();

    static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) //accesses private data so it goes in here but not instance data so we keep em static
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(pos);

            for (Entity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }





}
