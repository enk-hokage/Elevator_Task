import java.util.List;

public class Drawer {
    private final Elevator elevator;

    public Drawer(Elevator elevator) {
        this.elevator = elevator;
    }

    public void draw(){
        for (int i = elevator.getListFloor().size() - 1; i >= 0; i--){
            String direction = elevator.getListFloor().get(i).getFloorNumber() < elevator.getTargetFloor() ? "^" : "v";
            System.out.println(elevator.getListFloor().get(i).getLeftPassengers() + "|" + direction + getElevatorImage(elevator.getPassInElevatorList())
            + direction + "|" + getQueueImage(elevator.getListFloor().get(i)));
        }
        System.out.println("-------------------");
    }

    private String getElevatorImage(List<Passenger> passengers){
        StringBuilder builder = new StringBuilder();
        for (Passenger passenger: passengers){
            builder.append(passenger.getTo());
        }
        if (passengers.size() < elevator.getMaxPass()){
            for (int i = 0; i < elevator.getMaxPass() - passengers.size(); i++){
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    private String getQueueImage(Floor floor){
        StringBuilder builder = new StringBuilder();
        for (Passenger passenger: floor.getGoUpQueue()){
            builder.append(passenger.getTo());
        }
        for (Passenger passenger: floor.getGoDownQueue()){
            builder.append(passenger.getTo());
        }
        return builder.toString();
    }
}
