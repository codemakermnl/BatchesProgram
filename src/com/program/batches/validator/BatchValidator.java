package com.program.batches.validator;

import com.program.batches.dto.BatchDTO;
import com.program.batches.exception.InvalidBatchJSONException;

public class BatchValidator {

    public static void validate(final BatchDTO batchDTO ) {
        if( !"l".equalsIgnoreCase(batchDTO.getEntrance()) && !"r".equalsIgnoreCase(batchDTO.getEntrance())  ) {
            throw new InvalidBatchJSONException("Entrance should only be l or r");
        }

        if( !"l".equalsIgnoreCase(batchDTO.getEntrance()) && !"r".equalsIgnoreCase(batchDTO.getExit())  ) {
            throw new InvalidBatchJSONException("Exit should only be l or r");
        }
    }
}
