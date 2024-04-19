package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.Writer;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final String FILE_PATH = "src/main/resources/report.csv";
    private static Writer writer;
    private static File file;

    @BeforeEach
    void setUp() {
        writer = new WriterImpl();
        file = new File(FILE_PATH);
    }

    @Test
    void createReport_checkIfCreated_ok() {
        String content = """
                fruit,quantity
                banana,12
                apple,5""";
        writer.writeToFile(content, FILE_PATH);
        assertTrue(file.exists());
    }

    @Test
    void createReport_checkIfCreated_notOk() {
        String content = """
                fruit,quantity
                banana,12
                apple,5""";
        assertThrows(RuntimeException.class, () -> writer.writeToFile(content, ""));
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }
}
