package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriteServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private static final String EMPTY_FILE_NAME = "";
    private static final String FILENAME = "src/test/resources/output_db.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
                      + "banana,152" + System.lineSeparator()
                      + "apple,90";
    private static WriteService writeService;

    @BeforeAll
    static void beforeAll() {
        writeService = new WriteServiceImpl();
    }

    @Test
    void writeToFile_emptyFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(REPORT, EMPTY_FILE_NAME));
    }

    @Test
    void writeToFile_nullFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile(REPORT, null));
    }

    @Test
    void writeToFile_ValidData_Ok() {
        writeService.writeToFile(FILENAME, REPORT);
        try {
            String actual = Files.readString(new File(FILENAME).toPath());
            assertEquals(REPORT, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file by path: " + FILENAME, e);
        }
    }
}
