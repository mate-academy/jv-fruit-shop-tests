package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterToFileImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteToFileImplTest {
    private static final String INCORRECT_FILE_NAME = "src/test/resources/";
    private static final String REPORT_FILE_NAME = "src/test/resources/reportTestFile.csv";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,90";
    private static WriterToFile writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriterToFileImpl();
    }

    @Test
    void writeToFile_incorrectFileName_NotOk() {
        assertThrows(RuntimeException.class, () ->
                writer.writeToFile(INCORRECT_FILE_NAME,REPORT));
    }

    @Test
    void writeToFile_correctFileName_Ok() {
        writer.writeToFile(REPORT_FILE_NAME,REPORT);
        List<String> expected = List.of("fruit,quantity","banana,90");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(REPORT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + REPORT_FILE_NAME,e);
        }
        assertEquals(expected,actual);
    }
}
