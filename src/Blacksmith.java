import processing.core.PImage;

import java.util.List;

public class Blacksmith extends Entity{

//    private Point position;
//    private List<PImage> images;
//    private int imageIndex;
//    private String id; //this was left because it could be useful later although its not being used now

    public Blacksmith(Point position, List<PImage> images, String id) {
        super(position, images, 0, id);
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

    public PImage getCurrentImage(){
        return images.get(imageIndex);
    }
}
