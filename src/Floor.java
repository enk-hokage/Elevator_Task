import java.util.PriorityQueue;
import java.util.Queue;

public class Floor {

    private int floorNumber;
    private int leftPassengers;

    Queue <Passenger> goDownQueue = new PriorityQueue<>();
    Queue <Passenger> goUpQueue = new PriorityQueue<>();

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Floor(int floorNumber, int floors) {
        this.floorNumber = floorNumber;
        for (int i = 1; i < Util.randomInRange(0, 3); i++){
            Passenger passenger = new Passenger();
            int floor = Util.randomInRange(1, floors);
            while (floor == floorNumber){
                floor = Util.randomInRange(1, floors);
            }
            passenger.setTo(floor);
            if (floor < floorNumber){
                goDownQueue.add(passenger);
            } else {
                goUpQueue.add(passenger);
            }
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }



    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Queue<Passenger> getGoDownQueue() {
        return goDownQueue;
    }

    public void setGoDownQueue(Queue<Passenger> goDownQueue) {
        this.goDownQueue = goDownQueue;
    }

    public Queue<Passenger> getGoUpQueue() {
        return goUpQueue;
    }

    public void setGoUpQueue(Queue<Passenger> goUpQueue) {
        this.goUpQueue = goUpQueue;
    }

    public int getLeftPassengers() {
        return leftPassengers;
    }

    public void setLeftPassengers(int leftPassengers) {
        this.leftPassengers = leftPassengers;
    }
}
