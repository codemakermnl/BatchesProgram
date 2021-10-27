package com.program.batches.reader;

import com.program.batches.dto.BatchDTO;
import com.program.batches.util.JSONMapperUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {

    public static BatchDTO readJSONFile(final String filePath ) throws IOException {
        final String text = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

        return JSONMapperUtil.getObject( text, BatchDTO.class );
    }
}
