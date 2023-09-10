package core.basesyntax.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.readservice.WriterService;
import core.basesyntax.service.readservice.WriterServiceImpl;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteServiceImplTest {
    private static final String INPUT_VALID_DATA_FILE =
            "src/main/java/datafiles/new_report_data.csv";
    private static final String INPUT_INVALID_DATA_FILE =
            "src/main/java/datafiles/report_data123.csv";
    private static final String NEW_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private static WriterService writerService;

    @BeforeAll
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToPathFile_Ok() {
        writerService.write(NEW_REPORT, INPUT_VALID_DATA_FILE);
        Assertions.assertTrue(Files.exists(Path.of(INPUT_VALID_DATA_FILE)));
    }

    @Test
    public void write_nullValue_toFilePath_notOk() {
        assertThrows(RuntimeException.class, () ->
                writerService.write(null, INPUT_VALID_DATA_FILE));
    }

    @Test
    public void writeToInvalidPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                writerService.write(null, INPUT_INVALID_DATA_FILE));
    }

    @Test
    void wrire_nullPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                writerService.write(NEW_REPORT, null));
    }
}
