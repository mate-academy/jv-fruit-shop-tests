package core.basesyntax.service.impl.creator;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CsvFileReaderServiceImplCreator {
    private ReaderService readerService;

    public ReaderService createReader(String fromFileName) {
        try {
            return new CsvFileReaderServiceImpl(new BufferedReader(new FileReader(fromFileName)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
    }
}
