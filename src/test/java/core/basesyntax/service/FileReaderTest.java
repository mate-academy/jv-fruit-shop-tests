package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String FILE_TEST = "src/test/resources/test.csv";
    private static final String FILE_NOT_EXIST = "src/test/resources/wrong.csv";
    private static FileReader readFile;
    private static List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        readFile = new FileReaderImpl();
    }

    @Test
    public void reader_readExistedFile_ok() {
        String testString = "Hello Mates!";
        List<String> testList = new ArrayList<>();
        testList.add(testString);
        File file = new File(FILE_TEST);
        createTestFile(file, testString);
        lines = readFile.getData(FILE_TEST);
        assertTrue(!lines.isEmpty());
        assertEquals(lines, testList);
    }

    @Test(expected = RuntimeException.class)
    public void reader_readNoneExistedFile_notOk() {
        readFile.getData(FILE_NOT_EXIST);
    }

    private void createTestFile(File file, String testString) {
        try {
            Files.write(file.toPath(), testString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file" + FILE_TEST, e);
        }
    }
}
