package com.company;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Elevator class.
 * An elevator has an id, a floor where it is currently at and list of requests it has to fulfill.
 * @param pickUp to show if an elevator is currently in use.
 */
public class Elevator {
    private String id;
    private int currentFloor;
    private LinkedList<Request> currentRequests;
    private boolean pickUp;

    public Elevator(String id, int currentFloor, LinkedList<Request> currentRequests, boolean pickUp) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.currentRequests = currentRequests;
        this.pickUp = pickUp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public LinkedList<Request> getCurrentRequests() {
        return currentRequests;
    }

    public void setCurrentRequests(LinkedList<Request> currentRequests) {
        this.currentRequests = currentRequests;
    }

    public boolean isPickUp() {
        return pickUp;
    }

    public void setPickUp(boolean pickUp) {
        this.pickUp = pickUp;
    }

    /**
     * Method to move elevators when they are ticked.
     * If the elevator is occupied it moves towards target floor with each tick.
     * If the elevator is unoccupied it moves towards the pick up location
     */
    public void tick () {

        if(currentRequests.isEmpty()) return;

        if(!this.isPickUp() && this.getCurrentFloor() < this.getCurrentRequests().peek().getCurrentFloor()){
            this.currentFloor++;
            System.out.println(this.getId() + " has moved up towards pickup on Floor " + this.getCurrentRequests().peek().getCurrentFloor() + " and is now on Floor" + this.currentFloor);
        }
        else if(!this.isPickUp() && this.getCurrentFloor() > this.getCurrentRequests().peek().getCurrentFloor()){
            this.currentFloor--;
            System.out.println(this.getId() + " has moved down towards pickup on Floor " + this.getCurrentRequests().peek().getCurrentFloor() + " and is now on Floor" + this.currentFloor);
        }
        else if(!this.isPickUp() && this.getCurrentFloor() == this.getCurrentRequests().peek().getCurrentFloor()){
            this.setPickUp(true);
            System.out.println(this.getId() + " has arrived at pickup location");
            return;
        }

        if (this.isPickUp() && currentRequests.peek().getDestinationFloor() == this.currentFloor) {
            System.out.println(this.getId() + " has reached its destination " + this.getCurrentRequests().peek().getDestinationFloor());
            this.currentRequests.poll();
            this.pickUp = false;
        }
        else if (this.isPickUp() && this.currentRequests.peek().getDirection() == "UP") {
            this.currentFloor++;
            System.out.println(this.getId() + " has moved up to Floor " + this.currentFloor);
        }
        else if (this.isPickUp() && this.currentRequests.peek().getDirection() == "DOWN"){
            this.currentFloor--;
            System.out.println(this.getId() + " has moved down to Floor " + this.currentFloor);
        }

    }

}
