package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String PATH_TO_WRITE = "src/test/resources/report.csv";
    private static final String WRONG_PATH = "src/test/report.csv";
    private static final String REPORT = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeServicePathIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(null, REPORT));
    }

    @Test
    void writeServiceReportIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(PATH_TO_WRITE, null));
    }

    @Test
    void writeServiceWrongPath_NotOk() {
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(WRONG_PATH,REPORT));
    }

    @Test
    void writeServiceValidPathAndReport_Ok() {
        writerService.writeToFile(PATH_TO_WRITE, REPORT);
        List<String> expected = List.of("fruit,quantity", "banana,152", "apple,90");
        try {
            assertEquals(expected, Files.readAllLines(Paths.get(PATH_TO_WRITE)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
