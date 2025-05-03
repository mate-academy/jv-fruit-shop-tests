package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService csvFileWriterService;

    @BeforeAll
    static void setUp() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test
    void writeToFile_writeCorrectInformation_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "b,apple,10"
                + System.lineSeparator()
                + "b,banana,15";
        String content = "fruit,quantity"
                + System.lineSeparator()
                + "b,apple,10"
                + System.lineSeparator()
                + "b,banana,15";
        String fileName = "src//test//resources//Report.csv";
        csvFileWriterService.writeToFile(fileName, content);
        String actual = read(fileName);
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

    private String read(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + fileName, e);
        }
    }
}
