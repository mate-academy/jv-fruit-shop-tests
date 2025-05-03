package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.interfaces.ReaderService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ReaderServiceImplTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_fileWithContent_ok() throws IOException {
        File inputFile = tempFolder.newFile("input.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write("b,banana,20\np,apple,100\ns,banana,50");
        }
        List<String> lines = readerService.readFromFile(inputFile.getAbsolutePath());
        assertEquals(3, lines.size());
        assertEquals("b,banana,20", lines.get(0));
        assertEquals("p,apple,100", lines.get(1));
        assertEquals("s,banana,50", lines.get(2));
    }

    @Test
    public void readFromFile_emptyFile_notOk() throws IOException {
        File inputFile = tempFolder.newFile("emptyInput.csv");
        List<String> lines = readerService.readFromFile(inputFile.getAbsolutePath());
        assertEquals(0, lines.size());
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nonExistentFile_notOk() {
        String nonExistentFile = "nonexistent.csv";
        readerService.readFromFile(nonExistentFile);
    }
}
