package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void init() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validFilePath_Ok() throws IOException {
        String validPathName = "src/main/resources/InputFile.csv";
        List<String> expected = Files.readAllLines(Path.of(validPathName));
        List<String> actual = fileReaderService.readFromFile(validPathName);
        assertEquals(String.format("Should return list: %s \n but was: %s",
                expected, actual), expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_nullFilePath_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notValidFilePath_notOk() {
        fileReaderService.readFromFile("invalidPath");
    }
}
