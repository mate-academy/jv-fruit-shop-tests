package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final String INVALID_NAME_OF_FILE = "scr/test/resources/abs.csv";
    private static final String VALID_NAME_OF_FILE = "src/test/resources/report.csv";
    private static WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_nullPathAndReport_notOk() {
        assertThrows(
                NullPointerException.class,
                () -> writerService.writeToFile(INVALID_NAME_OF_FILE, null)
        );
    }

    @Test
    void writeToFile_nullReport_notOk() {
        assertThrows(
                NullPointerException.class,
                () -> writerService.writeToFile(VALID_NAME_OF_FILE, null));
    }

    @Test
    void writeToFile_nullPath_notOk() {
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        lines.add(System.lineSeparator() + "banana,20");
        lines.add(System.lineSeparator() + "apple,10");
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(null, lines));
    }

    @Test
    void writeToFile_validPathAndData_ok() {
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        lines.add(System.lineSeparator() + "banana,20");
        lines.add(System.lineSeparator( + "apple,10");
        writerService.writeToFile(VALID_NAME_OF_FILE, lines);
    }
}
