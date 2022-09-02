package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.impl.FileWriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String FILE_PATH = "src/test/resources/fileWriteTest.csv";
    private static final String COMMA_CHARACTER = ",";
    private static FileWriterService fileWriter;
    private static String stringToWrite;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterServiceImpl();
        stringToWrite = "apple" + COMMA_CHARACTER + 65 + System.lineSeparator()
                + "banana" + COMMA_CHARACTER + 120 + System.lineSeparator()
                + "orange" + COMMA_CHARACTER + 45;
    }

    @Before
    public void setUp() {
        File file = new File(FILE_PATH);
        file.delete();
    }

    @Test
    public void fileWriterService_fileWrite_Ok() {
        fileWriter.writeToFile(stringToWrite,FILE_PATH);
        assertTrue(new File(FILE_PATH).isFile());
    }

    @Test
    public void fileWriterService_fileWriteAndCompareInputAndResult_Ok() {
        fileWriter.writeToFile(stringToWrite,FILE_PATH);
        File file = new File(FILE_PATH);
        String dataFromWritedFile;
        try {
            List<String> stringList = Files.readAllLines(file.toPath());
            dataFromWritedFile = stringList.stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(stringToWrite,dataFromWritedFile);
    }
}
