package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_nonExistentFile_throwsException() {
        String nonExistentFile = "nonexistent.csv";
        try {
            fileReaderService.readFromFile(nonExistentFile);
        } catch (RuntimeException e) {
            assertEquals("Can't read from file"
                    + "Something went wrong" + nonExistentFile, e.getMessage());
        }
    }

    @Test
    public void readFromFile_validFile_returnsAllLines() throws IOException {
        String filePath = "src/test/resources/input_file.csv";
        List<String> fileContent = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "p,apple,100",
                "s,banana,50"
        );

        Files.write(Paths.get(filePath), fileContent);

        List<String> lines = fileReaderService.readFromFile(filePath);
        assertEquals(fileContent.size(), lines.size());
        assertEquals(fileContent, lines);

        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    public void readFromFile_emptyFile_returnsEmptyList() throws IOException {
        String filePath = "src/test/resources/input_file.csv";
        Files.createFile(Paths.get(filePath));

        List<String> lines = fileReaderService.readFromFile(filePath);
        assertEquals(0, lines.size());

        Files.deleteIfExists(Paths.get(filePath));
    }

}
