package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.WriterService;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String FILE_TO_WRITE_PATH = "src/test/resources/testReadService.csv";
    private static WriterService writerService;

    @BeforeAll
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void readFile_validPath_ok() {
        String expectedString = "someReport";
        writerService.writeReport(FILE_TO_WRITE_PATH, expectedString);
        String actualString;
        try {
            actualString = Files.readString(Paths.get(FILE_TO_WRITE_PATH));
        } catch (Exception e) {
            throw new RuntimeException("File " + FILE_TO_WRITE_PATH + " not found.");
        }
        assertEquals(expectedString, actualString);
    }

    @Test
    void readFile_nullPath_notOk() {
        assertThrows(Exception.class,
                () -> writerService.writeReport(null, "some text"));
    }
}
