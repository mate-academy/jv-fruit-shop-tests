package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private FileWriterImpl fileWriter;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_nullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, null));
    }

    @Test
    void write_emptyInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("", ""));
    }

    @Test
    void write_emptyAndNullFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, ""));
    }

    @Test
    void write_emptyReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write("", tempDir.toAbsolutePath().toString()));
    }

    @Test
    void write_nullReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(null, tempDir.toAbsolutePath().toString()));
    }

    @Test
    void write_validResultingReport_Ok() throws IOException {
        Path file = tempDir.resolve("report.csv");
        fileWriter.write("banana,10", file.toString());
        assertEquals("banana,10", Files.readString(file));
    }
}
