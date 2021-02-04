import processing.core.PImage;

import java.util.List;

public class Obstacle implements Entity {

    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private String id;

    public Obstacle(Point position, List<PImage> images, String id) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.id = id;
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point p){
        this.position = p;
    }

    public int getAnimationPeriod(){
        return 0;
    }

    public PImage getCurrentImage(){
        return images.get(imageIndex);
    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler){}

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world,
                                ImageStore imageStore){}
}
