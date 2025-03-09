package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.impl.FileWriterImpl;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE_TO_WRITE = "src/test/resources/test.csv";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validData_Ok() throws Exception {
        fileWriter.write(TEST_FILE_TO_WRITE, "data");
        String content = new String(Files.readAllBytes(Paths.get(TEST_FILE_TO_WRITE)));
        assertEquals("data", content.trim());
    }

    @Test
    void writerHandlesEmptyData_Ok() throws IOException {
        fileWriter.write(TEST_FILE_TO_WRITE, "");
        String content = new String(Files.readAllBytes(Paths.get(TEST_FILE_TO_WRITE)));
        assertEquals("", content.trim());
    }

    @Test
    void writer_NotNull_Ok() {
        fileWriter.write(TEST_FILE_TO_WRITE,"data");
        assertNotNull(fileWriter);
    }
}
