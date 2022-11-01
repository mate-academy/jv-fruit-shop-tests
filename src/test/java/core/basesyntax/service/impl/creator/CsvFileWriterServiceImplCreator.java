package core.basesyntax.service.impl.creator;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriterServiceImplCreator {
    private WriterService writerService;

    public WriterService createWriter(String toFileName) {
        try {
            return new CsvFileWriterServiceImpl(new BufferedWriter(new FileWriter(toFileName)));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + toFileName, e);
        }
    }
}
