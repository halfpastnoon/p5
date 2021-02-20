import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public abstract class Entity
{
    protected Point position;
    protected List<PImage> images;
    protected String id;

    public Entity(Point position, List<PImage> images, String id) {
        this.position = position;
        this.images = images;
        this.id = id;
    }

    public Point getPosition() {
        return this.position;
    }

    public PImage getCurrentImage(){
        return images.get(0);
    } //sorry about the magic number

    public void setPosition(Point p){
        this.position = p;
    }

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
