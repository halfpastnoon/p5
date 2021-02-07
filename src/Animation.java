public class Animation implements Action{
    private Entity entity;
    private int repeatCount;

    public Animation(
            Entity entity,
            int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }
    public void executeAction(EventScheduler scheduler)
    {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity,
                    Factory.createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1,
                                    0)),
                    ((Animatable)this.entity).getAnimationPeriod()); //decided casting here made more sense, other entity functions are used
        }
    }

}
