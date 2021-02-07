
public interface Executable extends Entity{ //i am so incredibly sorry for using inheritance I could not think of a better alternative
    void executeActivity(WorldModel world,
                         ImageStore imageStore,
                         EventScheduler scheduler);

    void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);


}
