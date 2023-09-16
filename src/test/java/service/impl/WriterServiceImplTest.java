package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.WriterService;

class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String expected = "fruit,quantity" + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90";
    private static final String reportPath = "src/test/resources/reportTest.csv";

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_goodTest_ok() {
        writerService.writeToFile(expected,reportPath);
        final StringBuilder actual = new StringBuilder();
        try (BufferedReader fromFileRead = new BufferedReader(new FileReader(reportPath))) {
            actual.append(fromFileRead.readLine());
            while (fromFileRead.ready()) {
                actual.append(System.lineSeparator()).append(fromFileRead.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual.toString());
    }

    @Test
    void writeToFile_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(expected, null));
    }

    @Test
    void writeToFile_nullContent_notOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(null, reportPath));
    }
}
