package com.company;

import java.util.ArrayList;

/**
 * Request class to describe requests which enter the system as they were described in the task.
 */
public class Request {
    private int currentFloor;
    private int destinationFloor;
    private String direction;
    private final int maxFloor = 55;

    public Request(int currentFloor, int destinationFloor, String direction) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        this.direction = direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Method to check the validity of requests to ensure that all requests are valid in the current scope of the system (i.e. no negative floors, only possible to go from floor 0 to higher floors or other way around).
     * @return if the request is valid.
     */
    public boolean checkValidRequest(){
        boolean isValid = true;
        if(this.getCurrentFloor() != 0 && this.getDirection().equals("UP")){
            System.out.println("Request invalid, can only go to floor 0 from current floor");
            isValid = false;
        } else if(this.getCurrentFloor() == 0 && this.getDirection().equals("DOWN")){
            System.out.println("Request invalid, already on lowest floor");
            isValid = false;
        } else if(this.getCurrentFloor() > maxFloor || this.getDestinationFloor() > maxFloor){
            System.out.println("Request invalid, elevators can't go higher than " + maxFloor);
            isValid = false;
        } else if(this.getCurrentFloor() == this.getDestinationFloor()){
            System.out.println("Request invalid, elevator is already on floor " + this.getDestinationFloor());
            isValid = false;
        }
        return isValid;
    }

    /**
     * Method that generates random requests.
     * Can be modified to submit invalid requests if needed.
     * @return randomly generated requests to test the system
     */
    public static ArrayList<Request> generateRequests() {
        ArrayList<Request> testRequests = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * 55 +1);
            Request testRequest = new Request(0, random, "UP");
            System.out.println("Request: " + testRequest.getCurrentFloor() + " " + testRequest.getDestinationFloor() + " " + testRequest.getDirection() + " has been generated");
            testRequests.add(testRequest);
        }
        for (int j = 0; j < 5; j++) {
            int random = (int) (Math.random() * 55 + 1);
            Request testRequest = new Request(random, 0, "DOWN");
            System.out.println("Request: " + testRequest.getCurrentFloor() + " " + testRequest.getDestinationFloor() + " " + testRequest.getDirection() + " has been generated");
            testRequests.add(testRequest);
        }

        return testRequests;
    }
}
