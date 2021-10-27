package com.program.batches.test;


import com.program.batches.exception.InvalidRowException;
import com.program.batches.exception.RowFullException;
import com.program.batches.main.BatchProcessor;
import com.program.batches.reader.JSONReader;
import org.junit.Test;

import java.io.IOException;

public class BatchProgramTest {

    private BatchProcessor batchProcessor;

    @Test
    public void test1() throws IOException, RowFullException, InvalidRowException {
        batchProcessor = new BatchProcessor(JSONReader.readJSONFile("E:\\spring\\BatchesProgram\\test1.json"));
        batchProcessor.addToSeat( 'L', 1 );
        batchProcessor.addToSeat( 'E', 1 );
        batchProcessor.addToSeat( 'A', 1 );
        batchProcessor.addToSeat( 'G', 1 );
        batchProcessor.addToSeat( 'P', 2 );
        batchProcessor.addToSeat( 'D', 2 );
        batchProcessor.addToSeat( 'H', 2 );
        batchProcessor.addToSeat( 'C', 3 );
        batchProcessor.addToSeat( 'B', 3 );

//        System.out.println( batchProcessor.getState() );
        System.out.println("Exit Plan: ");
        System.out.println( String.join("\n", batchProcessor.getExitPlan()) );
    }
}
