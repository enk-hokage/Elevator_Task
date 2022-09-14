import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Elevator {
    private Floor currentFloor;
    private int maxPass = 3;

    private List<Passenger> passInElevatorList = new ArrayList<>();
    private int targetFloor;

    private List<Floor> listFloor = new ArrayList<>();
    private Drawer drawer = new Drawer(this);

    public Elevator() {
        int floors = Util.randomInRange(5, 7);
        for (int i = 1; i < floors; i++){
            listFloor.add(new Floor(i, floors));
        }
    }

    public void start(){
        currentFloor = listFloor.get(0);
        drawer.draw();

        while (true){
            checkToOpenDoors();

            if (passInElevatorList.size() < maxPass){
                if (currentFloor.getFloorNumber() < targetFloor &&
                    !currentFloor.getGoUpQueue().isEmpty()){
                    while (passInElevatorList.size() < maxPass || !currentFloor.getGoUpQueue().isEmpty()){
                        passInElevatorList.add(currentFloor.getGoUpQueue().poll());
                        updateTargetFloor(true);
                    }
                } else if (currentFloor.getFloorNumber() > targetFloor &&
                            !currentFloor.getGoDownQueue().isEmpty()){
                    while (passInElevatorList.size() < maxPass || !currentFloor.getGoDownQueue().isEmpty()){
                        passInElevatorList.add(currentFloor.getGoDownQueue().poll());
                        updateTargetFloor(false);
                    }
                }
            }

            if (currentFloor.getFloorNumber() < targetFloor){
                goUp();
            } else if (currentFloor.getFloorNumber() > targetFloor){
                goDown();
            } else {
                if (checkPeopleOnFloors()){
                    break;
                }
            }
        }
    }

    private boolean checkPeopleOnFloors(){
        boolean isEnd = true;
        for (int i = listFloor.size() - 1; i >= 0; i--){
            Floor floorToCheck = listFloor.get(i);
            if (!floorToCheck.getGoDownQueue().isEmpty()){
                while (passInElevatorList.size() < maxPass || !floorToCheck.getGoDownQueue().isEmpty()){
                    passInElevatorList.add(currentFloor.getGoDownQueue().poll());
                    updateTargetFloor(false);
                }
                isEnd = false;
                break;
            } else if (!floorToCheck.getGoUpQueue().isEmpty()){
                while (passInElevatorList.size() < maxPass || !floorToCheck.getGoUpQueue().isEmpty()){
                    passInElevatorList.add(currentFloor.getGoUpQueue().poll());
                    updateTargetFloor(true);
                }
                isEnd = false;
                break;
            }
        }
        return isEnd;
    }
    
    private void setInitialTargetFloor(){
        int target = 1;
        for (Floor floor: listFloor){
            if (!floor.getGoUpQueue().isEmpty()){
                for (Passenger passenger: floor.getGoUpQueue()){
                    if (passenger.getTo() > target){
                        target = passenger.getTo();
                    }
                }
            } else if (!floor.getGoDownQueue().isEmpty()){
                for (Passenger passenger: floor.getGoDownQueue()){
                    if (passenger.getTo() < target){
                        target = passenger.getTo();
                    }
                }
            }
        }
    }
    private void checkToOpenDoors(){
        for (Passenger pass: passInElevatorList){
            if (pass.getTo() == currentFloor.getFloorNumber()){
                releasePassengers();
                break;
            }
        }
    }
    private void updateTargetFloor(boolean isUp) {
        if ((isUp && passInElevatorList.get(passInElevatorList.size() - 1).getTo() > targetFloor) ||
                (!isUp && passInElevatorList.get(passInElevatorList.size() - 1).getTo() < targetFloor)) {
            targetFloor = passInElevatorList.get(passInElevatorList.size() - 1).getTo();
        }
    }

    private void releasePassengers(){
        int oldSize = passInElevatorList.size();
        passInElevatorList = passInElevatorList.stream()
                .filter(pass -> pass.getTo() != currentFloor.getFloorNumber())
                .collect(Collectors.toList());
        currentFloor.setLeftPassengers(currentFloor.getLeftPassengers() + (oldSize - passInElevatorList.size()));
    }

    private void goUp(){
        if (currentFloor.getFloorNumber() < 1) {
            currentFloor = listFloor.get(listFloor.indexOf(currentFloor) + 1);
            drawer.draw();
        }
    }

    private void goDown(){
        if (currentFloor.getFloorNumber() > 1) {
            currentFloor = listFloor.get(listFloor.indexOf(currentFloor) - 1);
            drawer.draw();
        }
    }

    public List<Floor> getListFloor() {
        return listFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public List<Passenger> getPassInElevatorList() {
        return passInElevatorList;
    }

    public int getMaxPass() {
        return maxPass;
    }
}
