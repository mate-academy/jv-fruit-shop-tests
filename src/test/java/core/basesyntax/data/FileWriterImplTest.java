package core.basesyntax.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private final FileWriterImpl fileWriterImpl = new FileWriterImpl();

    @TempDir
    private Path tempDirectory;

    @Test
    void write_nullPath_notOk() {
        String path = null;
        String data = "fruit,quantity\nbanana,100";
        assertThrows(IllegalArgumentException.class,
                () -> fileWriterImpl.write(data, path));
    }

    @Test
    void write_nullData_notOk() {
        Path path = tempDirectory.resolve("report.csv");
        String data = null;
        assertThrows(IllegalArgumentException.class,
                () -> fileWriterImpl.write(data, path.toString()));
    }

    @Test
    void write_emptyPath_notOk() {
        String path = "";
        String data = "fruit,quantity\nbanana,100";
        assertThrows(IllegalArgumentException.class,
                () -> fileWriterImpl.write(data, path));
    }

    @Test
    void write_emptyData_notOk() {
        Path path = tempDirectory.resolve("report.csv");
        String data = "";
        assertThrows(IllegalArgumentException.class,
                () -> fileWriterImpl.write(data, path.toString()));
    }

    @Test
    void write_invalidPath_notOk() {
        String path = "invalid/path/file.csv";
        String data = "fruit,quantity\nbanana,100";
        assertThrows(IllegalArgumentException.class, () ->
                fileWriterImpl.write(data, path)
        );
    }

    @Test
    void write_validData_writesReport() throws IOException {
        Path path = tempDirectory.resolve("report.csv");
        String data = "fruit,quantity\nbanana,100";
        fileWriterImpl.write(data, path.toString());
        assertEquals(data, Files.readString(path));
    }
}
