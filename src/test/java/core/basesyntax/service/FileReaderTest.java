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
    private static final String FILE_EXIST = "src/main/resources/test.csv";
    private static final String FILE_NOT_EXIST = "src/main/resources/wrong.csv";
    private static FileReader readFile;
    private static List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        readFile = new FileReaderImpl();
    }

    @Test
    public void readExistedFile_Ok() {
        File file = new File(FILE_EXIST);
        String testString = "Hello Mates!";
        List<String> testList = new ArrayList<>();
        testList.add(testString);
        try {
            Files.write(file.toPath(), testString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file" + FILE_EXIST, e);
        }
        lines = readFile.getData(FILE_EXIST);
        assertTrue(!lines.isEmpty());
        assertEquals(lines, testList);
    }

    @Test(expected = RuntimeException.class)
    public void readNonExistedFile_NotOk() {
        readFile.getData(FILE_NOT_EXIST);
    }
}
