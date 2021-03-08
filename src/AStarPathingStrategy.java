
import java.util.List;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy
{

    public List<Point> computePath(Point start, Point end,
                            Predicate<Point> canPassThrough,
                            BiPredicate<Point, Point> withinReach,
                            Function<Point, Stream<Point>> potentialNeighbors)
    {
        Stack<AEntry> closedList = new Stack<>();

        ArrayDeque<AEntry> openList = new ArrayDeque<>();
        AEntry current = new AEntry(start,0, Math.abs(start.x - end.x) + Math.abs(start.y - end.y), null);
        while(!withinReach.test(current.getPos(), end)){ //start of step 3 on algorithm
            List<Point> neighbors = potentialNeighbors.apply(current.getPos()) //get all valid neighbors
                    .filter(canPassThrough)
                    .filter(p -> !closedList.contains(new AEntry(p, 0, 0, null)))
                    .collect(Collectors.toList());

            for(Point i : neighbors){ //analyze all valid neighbors
                AEntry test = new AEntry(i,
                        current.getG()+1,
                        Math.abs(i.x - end.x) + Math.abs(i.y - end.y),
                        current);

                boolean foundFlag = false;
                for(AEntry j : openList){
                    if(j.equals(test)){
                        foundFlag = true;
                        if(j.getG() > test.getG()){
                            j.setPrev(current);
                            j.setG(test.getG());
                        }
                        break;
                    }
                }
                if(!foundFlag) openList.addLast(test);

            }

            openList.remove(current);
            closedList.push(current);
            AEntry bestF = null;                                               //search for point w best f value
            int smallF = -1;
            for(AEntry i : openList){
                if(smallF == -1 || i.getF() < smallF){
                    smallF = i.getF();
                    bestF = i;
                }
            }
            current = bestF;
            if(bestF == null) break;
        }

        AEntry starter = current;
        ArrayList<Point> ret = new ArrayList<>();
        while(starter != null && starter.getPrev() != null){
            ret.add(0, starter.getPos());
            starter = starter.getPrev();
        }
        return ret;
    }

    private class AEntry{ //remove unused getters/setters
        private Point pos;
        private int g;
        private int h;
        private int f;
        private AEntry prev;

        public AEntry(Point pos, int g, int h, AEntry prev){
            this.g = g;
            this.pos = pos;
            this.h = h;
            this.f = g+h;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AEntry aEntry = (AEntry) o;
            return Objects.equals(getPos(), aEntry.getPos());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPos(), getG(), getH(), getF());
        }

        public String toString() {return this.pos.toString();}

        public AEntry getPrev() {
            return prev;
        }

        public void setPrev(AEntry prev) {
            this.prev = prev;
        }

        public Point getPos() {
            return pos;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
            this.f = this.g + this.h;
        }

        public int getH() {
            return h;
        }

        public int getF() {
            return f;
        }
    }
}
