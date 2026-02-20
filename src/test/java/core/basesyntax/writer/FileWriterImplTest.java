package core.basesyntax.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String WRITE_FILE_PATH =
            "src/test/java/resourses/testWriteFileName.csv";
    private static final String EMPTY_STRING = "";
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "banana,20" + System.lineSeparator() + "apple,12" + System.lineSeparator();
    private FileWriter fileWriter = new FileWriterImpl();

    @Test
    void write_reportIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                fileWriter.write(null, WRITE_FILE_PATH));
    }

    @Test
    void write_reportIsBlank_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                fileWriter.write(EMPTY_STRING, WRITE_FILE_PATH));
    }

    @Test
    void write_fileNameIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                fileWriter.write(report, null));
    }

    @Test
    void write_fileNameIsBlank_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                fileWriter.write(report, EMPTY_STRING));
    }

    @Test
    void writeValidFile_ShouldCreateAndWriteFile() throws IOException {
        fileWriter.write(report, WRITE_FILE_PATH);

        Path path = Path.of(WRITE_FILE_PATH);

        assertTrue(Files.exists(path));

        // перевіряємо, що вміст правильний
        String actual = Files.readString(path);
        assertEquals(report, actual);

        // очищаємо після тесту
        Files.deleteIfExists(path);
    }
}
