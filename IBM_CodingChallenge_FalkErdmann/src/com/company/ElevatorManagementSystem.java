package com.company;

import java.util.*;

/**
 * ElevatorManagementSystem class creates and controls all elevators and requests
 */
public class ElevatorManagementSystem {

    private final int maxElevators = 7;
    Elevator[] elevators = new Elevator[maxElevators];
    Queue<Request> requestQueue = new LinkedList<>();

    /**
     * Method that creates elevators and calls method to automatically generate some initial requests.
     * Also starts runSystem method.
     */
    public void initiateSystem() {

        //create elevators
        for (int i = 0; i < maxElevators; i++) {
            LinkedList<Request> elevatorQueue = new LinkedList<>();
            Elevator elevator = new Elevator("Elevator" + i, 0, elevatorQueue, false);
            elevators[i] = elevator;
            //   System.out.println(elevators[i].getId() + " has been initiated at Floor " + elevators[i].getCurrentFloor());
        }
        requestQueue.addAll(Request.generateRequests());

        runSystem(elevators, requestQueue);
    }

    /**
     *
     * Method which handles the logic of the elevator system.
     * Currently elevators only go from A to B without stopovers.
     * Ticks are used to simulate passing of time and movement of elevators.
     * Using while running to simulate that the system never really turns off, can be modified in different ways to stop the system as desired.
     * While elevators are available requests are removed from the queue and linked request to the available elevator(s).
     * If no elevators are available, the request is queued in the elevator that will be closest to the current floor of the request.
     * After requests are handled the system ticks once and every 100 ticks new requests are added (new requests could also be randomized of course).
     * @Thread.sleep() is used to visualize passing of time.
     *
     * @param elevators Array which contains all elevators in the system
     * @param requestQueue Queue which contains all the requests currently in the system
     */
    public void runSystem(Elevator[] elevators, Queue<Request> requestQueue) {
        long tickCount = 0; //
        boolean running = true; //

        while (running) {

            System.out.println("\nCurrent tick is " + tickCount);
            System.out.println(requestQueue.size() + " requests are left in the queue");

            while (!requestQueue.isEmpty() && getClosestAvailableElevator(requestQueue.peek().getCurrentFloor(), elevators) != null) {

                Request request = requestQueue.poll();
                if (request.checkValidRequest()) {

                    Elevator elevator = getClosestAvailableElevator(request.getCurrentFloor(), elevators);

                    System.out.println("Found closest available Elevator: " + elevator.getId());

                    elevator.getCurrentRequests().add(request);
                }
            }
            if (!requestQueue.isEmpty() && getClosestAvailableElevator(requestQueue.peek().getCurrentFloor(), elevators) == null) {

                Request request = requestQueue.poll();

                if (request.checkValidRequest()) {

                    Elevator elevator = getClosestUnavailableElevator(request.getCurrentFloor(), elevators);

                    System.out.println("Found closest unavailable Elevator: " + elevator.getId() + " which will come after reaching " + elevator.getCurrentRequests().peek().getDestinationFloor());

                    elevator.getCurrentRequests().add(request);

                    System.out.println("Current Queue for this elevator: " + elevator.getCurrentRequests().size());
                }
            }


            for (Elevator elevator : elevators) {
                elevator.tick();
            }
            tickCount++;
            if(tickCount%100==0){
                requestQueue.addAll(Request.generateRequests());
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to find closest elevator if there are idle elevators.
     * Finds the elevator which is closest to the pick up location.
     * If no elevators are idle returns null.
     *
     * @param currentFloor where the elevator has to pick someone up.
     * @param elevators which are currently running in the system.
     * @return closest available elevator
     */
    public Elevator getClosestAvailableElevator(int currentFloor, Elevator[] elevators) {

        Elevator closestElevator = null;

        for (Elevator elevator : elevators) {
            if (elevator.getCurrentRequests().isEmpty()) {
                if (closestElevator == null) {
                    closestElevator = elevator;
                } else {
                    if (Math.abs(elevator.getCurrentFloor() - currentFloor) < Math.abs(closestElevator.getCurrentFloor() - currentFloor)) {
                        closestElevator = elevator;
                    }
                }
            }
        }
        return closestElevator;
    }

    /**
     * Method to find closest elevator, if all elevators are in use.
     * Finds the elevator that will be closest to pick up location after completing its current requests.
     * Also considers the current length of the internal elevator queue.
     *
     * @param currentFloor
     * @param elevators
     * @return closest unavailable elevator
     */
    public Elevator getClosestUnavailableElevator(int currentFloor, Elevator[] elevators) {

        Elevator closestElevator = null;

        for (Elevator elevator : elevators) {
            if (!elevator.getCurrentRequests().isEmpty()) {
                if (closestElevator == null) {
                    closestElevator = elevator;
                } else {
                    if (Math.abs(elevator.getCurrentRequests().getLast().getDestinationFloor() - currentFloor) < Math.abs(closestElevator.getCurrentRequests().peek().getDestinationFloor() - currentFloor)
                            && elevator.getCurrentRequests().size() <= closestElevator.getCurrentRequests().size()) {
                        closestElevator = elevator;
                    }
                }
            }
        }
        return closestElevator;
    }


}
