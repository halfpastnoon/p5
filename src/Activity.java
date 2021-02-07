public class Activity implements Action {
    private Executable entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(
            Executable entity,
            WorldModel world,
            ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }


    public void executeAction(EventScheduler scheduler) {
       this.entity.executeActivity(this.world, this.imageStore, scheduler);
    }
}
