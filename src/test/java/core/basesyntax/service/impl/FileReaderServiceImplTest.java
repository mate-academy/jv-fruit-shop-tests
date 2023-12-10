package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileReaderServiceImplTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_fileWithContent_ok() throws IOException {
        File inputFile = tempFolder.newFile("input_file.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write("type,fruit,quantity\nb,banana,20\np,apple,100\ns,banana,50");
        }
        List<String> lines = fileReaderService.readFromFile(inputFile.getAbsolutePath());
        assertEquals(3, lines.size());
        assertEquals("b,banana,20", lines.get(0));
        assertEquals("p,apple,100", lines.get(1));
        assertEquals("s,banana,50", lines.get(2));
    }

    @Test
    public void readFromFile_emptyFile_notOk() throws IOException {
        File inputFile = tempFolder.newFile("emptyInput.csv");
        List<String> lines = fileReaderService.readFromFile(inputFile.getAbsolutePath());
        assertEquals(0, lines.size());
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nonExistentFile_notOk() {
        String nonExistentFile = "nonexistent.csv";
        fileReaderService.readFromFile(nonExistentFile);
    }
}
