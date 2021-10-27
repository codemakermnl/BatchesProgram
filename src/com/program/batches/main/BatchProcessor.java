package com.program.batches.main;

import com.program.batches.dto.BatchDTO;
import com.program.batches.exception.InvalidRowException;
import com.program.batches.exception.RowFullException;

import java.util.*;

public class BatchProcessor {

    private List<Character> customerList;

    private BatchDTO batchDTO;

    private List<String> exitPlan;

    private List<Collection<Character>> rows;

    private Map<Integer, Integer> rowCounterMap;

    public BatchProcessor(BatchDTO batchDTO) {
        this.batchDTO = batchDTO;
        exitPlan = new ArrayList<java.lang.String>();
        customerList = new ArrayList<>();
        rows = new ArrayList<>();
        rowCounterMap = new HashMap<>();

        initialize();
    }

    private void initialize() {
        initializeMapCounter();
        if( isStackUsed() ) {
            batchDTO.getRows().forEach( s -> rows.add(new Stack<>()) );
            return;
        }

        batchDTO.getRows().forEach( s -> rows.add(new LinkedList<>()) );
    }

    private void initializeMapCounter() {
        for(int i = 0; i < batchDTO.getRows().size(); i++) {
            rowCounterMap.put( i, 0 );
        }

//        rowCounterMap.forEach( (k,v) -> System.out.println("row: " + k + ", val: " + v) );
    }

    private boolean isStackUsed() {
        return batchDTO.isLeftEntrance() && batchDTO.isLeftExit() || batchDTO.isRightEntrance() && batchDTO.isRightExit();
    }

    private boolean isQueueUsed() {
        return batchDTO.isLeftEntrance() && batchDTO.isRightExit() || batchDTO.isRightEntrance() && batchDTO.isLeftExit();
    }

    public boolean areRowSeatsUnfilled(int rowNumber) throws InvalidRowException {
        rowNumber--;
        if( rowCounterMap.containsKey( rowNumber ) ) {
            return rowCounterMap.get(rowNumber) < batchDTO.getRows().get( rowNumber );
        }else {
            throw new InvalidRowException("Row " + rowNumber + " does not exist.");
        }
    }

    public void addToSeat( Character customer, int rowNumber ) throws RowFullException, InvalidRowException {

        if( !areRowSeatsUnfilled( rowNumber ) ) {
            throw new RowFullException("Sorry, that row is full.");
        }

        rowNumber--;
        rowCounterMap.put(rowNumber, rowCounterMap.get(rowNumber)+1);

        if( isStackUsed() && rows.get( rowNumber ) instanceof Stack ) {
            ((Stack<Character>) rows.get( rowNumber ) ).push( customer );
        }else if( isQueueUsed() && rows.get( rowNumber ) instanceof LinkedList ) {
            ((LinkedList<Character>) rows.get( rowNumber ) ).addLast( customer );
        }
    }

    public List<java.lang.String> getState() {
        for(int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
            final StringBuilder exitPlanString = new StringBuilder();
            if( isStackUsed() ) {

                while( !rows.get( rowNumber ).isEmpty() ) {
                    exitPlanString.append( ((Stack<Character>) rows.get( rowNumber ) ).pop() );
                }

                exitPlanString.reverse();
            }else{

                while( !rows.get( rowNumber ).isEmpty() ) {
                    exitPlanString.append( ((LinkedList<Character>) rows.get( rowNumber ) ).poll() );
                }
            }

            exitPlan.add( exitPlanString.toString() );
        }

        return exitPlan;
    }

    private int getHighestNumberOfSeats() {
        int highest = Integer.MIN_VALUE;

        for( int i = 0; i < batchDTO.getRows().size(); i++ ) {
            if( highest <= batchDTO.getRows().get(i) ) {
                highest = batchDTO.getRows().get(i);
            }
        }
        return highest;
    }

    public List<java.lang.String> getExitPlan() {
        int highestNumberOfSeats = getHighestNumberOfSeats();
//        System.out.println("highestNumberOfSeats: " + highestNumberOfSeats  + "\nRows: " + rows.size());
        char[][] customerMatrix = new char[rows.size()][highestNumberOfSeats];
        for ( int i =0 ; i < rows.size(); i++) {
            for( int j = 0; j < highestNumberOfSeats; j++ ) {
                customerMatrix[i][j]= '0';
            }
        }

//        System.out.println( "exit: " + batchDTO.getExit() +  ", left exit? " + batchDTO.isLeftExit());
        if( batchDTO.isLeftExit() ) {
            for(int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
                List<Character> transferList = new ArrayList<>();
                if( isStackUsed() ) {
                    while( !rows.get( rowNumber ).isEmpty() ) {
                        transferList.add( ((Stack<Character>) rows.get( rowNumber ) ).pop() );
                    }
                    Collections.reverse(transferList);
                }else{
                    while( !rows.get( rowNumber ).isEmpty() ) {
                        transferList.add( ((LinkedList<Character>) rows.get( rowNumber ) ).poll() );
                    }
                }

                for( int i = 0; i < transferList.size(); i++ ) {
                    customerMatrix[rowNumber][i] = transferList.get(i);
                }
            }

//            for(int i = 0; i < customerMatrix.length; i++) {
//                for(int j = 0; j < customerMatrix[i].length; j++) {
//                    System.out.print( customerMatrix[i][j] + " " );
//                }
//                System.out.println();
//            }

            for(int i = 0; i < highestNumberOfSeats; i++) {
                final StringBuilder exitPlanString = new StringBuilder();
                for(int j = 0; j < rows.size(); j++) {
//                    System.out.println("i: " + i + ", j: " + j);
                    if( customerMatrix[j][i] != '0' ) {
                        exitPlanString.append( customerMatrix[j][i] );
                    }
                }
                exitPlan.add( exitPlanString.toString() );
            }
        }else{
            for(int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
                List<Character> transferList = new ArrayList<>();
//                System.out.println( "rowNumber " + rowNumber + " size: " + rows.get( rowNumber ).size());
                if( isStackUsed() ) {
                    while( !rows.get( rowNumber ).isEmpty() ) {
                        transferList.add( ((Stack<Character>) rows.get( rowNumber ) ).pop() );
                    }
                    Collections.reverse(transferList);
                }else{
                    while( !rows.get( rowNumber ).isEmpty() ) {
                        transferList.add( ((LinkedList<Character>) rows.get( rowNumber ) ).poll() );
                    }
                }
//                System.out.println("transfer: " + transferList);
                int ctr = highestNumberOfSeats - 1;
                for( int i = transferList.size()-1; i >= 0; i-- ) {
//                    System.out.println("(rowNumber: " + rowNumber + ", i: " + i + ") => " + transferList.get(i));
                    customerMatrix[rowNumber][ctr] = transferList.get(i);
                    ctr--;
                }
            }

            for(int i = highestNumberOfSeats - 1; i >= 0; i--) {
                final StringBuilder exitPlanString = new StringBuilder();
                for(int j = 0; j < rows.size(); j++) {
//                    System.out.println("j: " + j + ", i: " + i);
                   if( customerMatrix[j][i] != '0' ) {
                       exitPlanString.append( customerMatrix[j][i] );
                   }
                }
                exitPlan.add( exitPlanString.toString() );
            }
        }



        return exitPlan;
    }
}
