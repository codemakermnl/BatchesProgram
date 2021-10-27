package com.program.batches.main;

import com.program.batches.dto.BatchDTO;
import com.program.batches.exception.InvalidBatchJSONException;
import com.program.batches.exception.InvalidRowException;
import com.program.batches.exception.RowFullException;
import com.program.batches.reader.JSONReader;
import com.program.batches.validator.BatchValidator;

import java.io.IOException;
import java.util.Scanner;

public class MainProgram {

    public static void main(String[] args) {
        final String filePath = "E:\\spring\\BatchesProgram\\test1.json";
        try {
            BatchDTO batchDTO = JSONReader.readJSONFile( filePath );
//            System.out.println(batchDTO);
            BatchValidator.validate(batchDTO);
            Scanner sc = new Scanner(System.in);
            displayMenu();
            int input = sc.nextInt();
            BatchProcessor processor = new BatchProcessor( batchDTO );

            while(input != 3) {
                switch( input ) {
                    case 1:
                        try {
                            inputCustomer(processor,sc);
                        } catch (InvalidRowException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        printExitPlan(processor);
                        break;
                    case 3:
                        break;
                }
                displayMenu();
                input = sc.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch( InvalidBatchJSONException e) {
            System.out.println(e.getMessage());
        }catch ( Exception e ) {
            e.printStackTrace();;
            System.out.println("Error reading JSON file.");
        }

    }

    public static void inputCustomer( BatchProcessor processor, Scanner sc ) throws InvalidRowException {
        System.out.println("What is the customer's name?");
        Character customer = sc.next().charAt(0);
        System.out.println("What is the customer's row?");
        int rowNumber = sc.nextInt();

        while( !processor.areRowSeatsUnfilled(rowNumber) ) {
            System.out.print("Sorry, that row is full.\nChoose another one: ");
            rowNumber = sc.nextInt();
        }

        try {
            processor.addToSeat( customer, rowNumber );
        } catch (RowFullException e) {
            e.printStackTrace();
        }
    }

    public static void printExitPlan(BatchProcessor processor) {
        System.out.println("Exit Plan: ");
        System.out.println( String.join("\n", processor.getExitPlan()) );
    }

    public static void displayMenu() {
        System.out.println("[1] Input a customer\n[2] Print exit plan\n[3] Exit");
    }
}
