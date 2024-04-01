package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String RESULT_FILE_PATH = "src/test/resources/result.csv";

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeCorrectDataToFile_Ok() {
        String expectedData = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        writerService.writeDataToFile(expectedData, RESULT_FILE_PATH);
        try {
            String actualData = new String(Files.readAllBytes(Paths.get(RESULT_FILE_PATH)));
            assertEquals(expectedData, actualData);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + RESULT_FILE_PATH + e);
        }
    }
}
