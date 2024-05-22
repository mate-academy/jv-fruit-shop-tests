package core.basesyntax.impl;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ReadingException;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();

    @Test
    void readFile_fileIsNotCsvExpansion_NotOk() {
        String fileName = "src/test/resources/notCsvFile.txt";
        assertThrows(ReadingException.class,() -> fileReaderService.readFile(fileName));
    }

    @Test
    void readFile_fileIsCsvExpansion_Ok() {
        String fileName = "src/test/resources/InputFile.csv";
        Class<? extends List> actualClass = fileReaderService.readFile(fileName).getClass();
        Class<ArrayList> expectedClass = ArrayList.class;
        assertSame(expectedClass, actualClass);
    }
}
