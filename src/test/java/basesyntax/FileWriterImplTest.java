package basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.io.FileWriterImpl;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {

    @Test
    void shouldThrowException_whenFilePathIsNull() {
        FileWriterImpl writer = new FileWriterImpl();

        assertThrows(RuntimeException.class,
                () -> writer.write("data", null));
    }

    @Test
    void shouldThrowException_whenFilePathIsBlank() {
        FileWriterImpl writer = new FileWriterImpl();

        assertThrows(RuntimeException.class,
                () -> writer.write("data", "   "));
    }

    @Test
    void shouldThrowException_whenDataIsNull() {
        FileWriterImpl writer = new FileWriterImpl();

        assertThrows(RuntimeException.class,
                () -> writer.write(null, "test.txt"));
    }

    @Test
    void shouldWriteFileSuccessfully() throws Exception {
        FileWriterImpl writer = new FileWriterImpl();

        Path tempFile = Files.createTempFile("test", ".txt");

        String data = "hello world";

        writer.write(data, tempFile.toString());

        String result = Files.readString(tempFile);

        org.junit.jupiter.api.Assertions.assertEquals(data, result);
    }

    @Test
    void shouldThrowException_whenFileCannotBeWritten() {
        FileWriterImpl writer = new FileWriterImpl();

        // folder zamiast pliku → IOException branch
        File invalidFile = new File("non_existing_dir/file.txt");

        assertThrows(RuntimeException.class,
                () -> writer.write("data", invalidFile.getPath()));
    }
}
