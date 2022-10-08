package core.basesyntax.service;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class FileWriterServiceImplTest {

    @Test
    public void writeToFile_validFilePath_Ok() throws IOException {
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        String validFilePath = "/home/nata/Java/Projects/jv-fruit-shop-tests/testFile.csv";
        String checkedText = "newTextForTest";
        fileWriterService.writeToFile(validFilePath, checkedText);

        assertEquals(checkedText, Files.readString(
                Path.of("/home/nata/Java/Projects/jv-fruit-shop-tests/testFile.csv")));
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        String invalidFilePath = "/home/nata/Java/Projects/jv-fruit-shop-tets/testFile.csv";
        fileWriterService.writeToFile(invalidFilePath,"invalidTestText");
    }
}
