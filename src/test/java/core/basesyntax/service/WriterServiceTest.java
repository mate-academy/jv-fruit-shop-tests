package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String NOT_EXISTING_FILE_NAME = "src/test/resources/report.csv";
    private static WriterService service;

    @BeforeEach
    void setUp() {
        service = new WriterServiceImpl();
    }

    @Test
    void writeToFile_null_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> service.writeToFile(NOT_EXISTING_FILE_NAME, null));
    }

    @Test
    void writeToFile_nullPath_notOk() {
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        lines.add(System.lineSeparator() + "banana,20");
        lines.add(System.lineSeparator() + "apple,10");
        Assertions.assertThrows(RuntimeException.class, () -> service.writeToFile(null, lines));
    }

    @Test
    void writeToFile_validDataAndPath_ok() {
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        lines.add(System.lineSeparator() + "banana,20");
        lines.add(System.lineSeparator() + "apple,10");
        service.writeToFile(NOT_EXISTING_FILE_NAME, lines);
    }
}
