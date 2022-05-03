package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileReaderServiceImplTest {

    File testFile;
    FileReaderService fileReaderServiceImpl;

    @Before
    public void setUp() {
        testFile = new File("testData.txt");
        fileReaderServiceImpl = new FileReaderServiceImpl();

        try (FileWriter fileWriter = new FileWriter(testFile)) {
            fileWriter.write("b,banana,152");
        } catch (IOException ex) {
            throw new RuntimeException("Not", ex);
        }
    }

    @After
    public void tearDown() {
        testFile.delete();
    }

    @Test
    public void readFromFile_Ok() throws RuntimeException {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,152");
        List<String> actual = fileReaderServiceImpl.readFromFile(testFile.getPath());

        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_notOk() {
        boolean thrown = false;
        try {
            fileReaderServiceImpl.readFromFile("notExistingFile.txt");
        } catch (RuntimeException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}