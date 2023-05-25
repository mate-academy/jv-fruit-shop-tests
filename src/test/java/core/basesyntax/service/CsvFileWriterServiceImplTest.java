package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceImplTest {
    private static CsvFileReaderService fileReaderService;
    private static CsvFileWriterService csvFileWriterService;

    @BeforeAll
    static void setUp() {
        fileReaderService = new CsvFileReaderServiceImpl();
        csvFileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test
    void writeToFile_writeCorrectInformation_ok() {
        List<String> expected = List.of("b,apple,10", "b,banana,15");
        String content = "fruit,quantity"
                + System.lineSeparator()
                + "b,apple,10"
                + System.lineSeparator()
                + "b,banana,15";
        String fileName = "src//test//resources//Report.csv";
        csvFileWriterService.writeToFile(fileName, content);
        List<String> actual = fileReaderService.readLines(fileName);
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_cantWriteToCloseFile_notOk() {
        File file = new File("src//test//resources//Close.csv");
        file.setWritable(false);
        String content = "fruit,quantity"
                + System.lineSeparator()
                + "b,apple,10"
                + System.lineSeparator()
                + "b,banana,15";
        String fileName = "src//test//resources//Close.csv";
        assertThrows(RuntimeException.class,
                () -> csvFileWriterService.writeToFile(fileName, content));
    }
}
