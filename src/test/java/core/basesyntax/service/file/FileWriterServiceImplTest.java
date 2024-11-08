package core.basesyntax.service.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.FileWriteException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String DATA = "Lorem ipsum dolor sit amet";
    private static final String PATH_TO_WRITTEN_DATA
            = "src/test/java/core/basesyntax/resources/WrittenText.csv";
    private static final String NON_EXISTING_FILE_PATH
            = "src/test/java/core/basesyntax/NonExistingFolder/NonExistingFile.csv";
    private FileWriterService fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    void write_isOk() {
        fileWriter.write(DATA, PATH_TO_WRITTEN_DATA);
        try {
            assertEquals(DATA, Files.readString(Path.of(PATH_TO_WRITTEN_DATA)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void write_nullValue_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(null, PATH_TO_WRITTEN_DATA));
        assertThrows(IllegalArgumentException.class,
                () -> fileWriter.write(DATA, null));
    }

    @Test
    void read_nonExistingFile_notOk() {
        assertThrows(FileWriteException.class,
                () -> fileWriter.write(DATA, NON_EXISTING_FILE_PATH));
    }
}
