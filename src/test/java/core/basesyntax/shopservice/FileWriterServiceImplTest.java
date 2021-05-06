package core.basesyntax.shopservice;

import static org.junit.Assert.assertEquals;

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
    public void writeToFile_ExistingPath_Ok() {
        new FileWriterServiceImpl().writeToFile(data, filePathActual);
        try {
            assertEquals(Files.readAllLines(Path.of(filePathExpected)),
                    Files.readAllLines(Path.of(filePathActual)));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to path " + "\"" + filePathActual + "\"", e);
        }
    }

    @Test
    public void writeToFile_NonExistingFile_Ok() {
        new FileWriterServiceImpl().writeToFile(data, invalidFilePathActual);
        try {
            assertEquals(Files.readAllLines(Path.of(filePathExpected)),
                    Files.readAllLines(Path.of(invalidFilePathActual)));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to path " + "\""
                    + invalidFilePathActual + "\"", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_EmptyPath_Bad() {
        new FileWriterServiceImpl().writeToFile(data, "");
    }
}
