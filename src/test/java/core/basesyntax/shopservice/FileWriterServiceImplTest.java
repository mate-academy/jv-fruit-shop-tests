package core.basesyntax.shopservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String data = "Hello" + System.lineSeparator()
            + "mates" + 47 + System.lineSeparator() + "!";
    private static final String filePathActual
            = "src/test/resources/testFileWriterServiceActual.csv";
    private static final String invalidFilePathActual = "src/test/resources/InvalidPath.csv";
    private static final String filePathExpected
            = "src/test/resources/testFileWriterServiceExpected.csv";

    @Test
    public void writeToFile_ExistingPath_Ok() throws IOException {
        new FileWriterServiceImpl().writeToFile(data, filePathActual);
        assertEquals(Files.readAllLines(Path.of(filePathExpected)),
                Files.readAllLines(Path.of(filePathActual)));
    }

    @Test
    public void writeToFile_NonExistingFile_Ok() throws IOException {
        new FileWriterServiceImpl().writeToFile(data, invalidFilePathActual);
        assertEquals(Files.readAllLines(Path.of(filePathExpected)),
                Files.readAllLines(Path.of(invalidFilePathActual)));
    }

    @Test
    public void writeToFile_EmptyPath_Bad() {
        boolean actual = true;
        try {
            new FileWriterServiceImpl().writeToFile(data, "");
        } catch (RuntimeException e) {
            actual = false;
        }
        assertFalse(actual);
    }
}
