package core.basesyntax.service.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceTest {
    private static FileService fileService;
    private static final String READ_FILEPATH = "src/test/java/resources/activities.csv";
    private static final String WRITE_FILEPATH = "src/test/java/resources/report.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileService = new FileService();
    }

    @Test
    public void read_readCorrect_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        List<String> actual;
        actual = fileService.read(READ_FILEPATH);
        assertTrue(actual.containsAll(expected));
        String emptyLine = "";
        try {
            Files.write(Paths.get(WRITE_FILEPATH), emptyLine.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Can't write report data to file ", e);
        }
    }

    @Test
    public void write_writeCorrect_Ok() {
        String expectedToWrite = "anyway, no one will read this";
        assertTrue(fileService.write(expectedToWrite, WRITE_FILEPATH));
        List<String> readedData = fileService.read(WRITE_FILEPATH);
        assertEquals(readedData.size(), 1);
        assertEquals(readedData.get(0), expectedToWrite);
    }

    @Test(expected = RuntimeException.class)
    public void read_noFileToRead_NotOk() {
        fileService.read("src/activities.csv");
    }

    @Test(expected = RuntimeException.class)
    public void write_noFileToWrite_NotOk() {
        fileService.write("","wrongsrc/activities.csv");
    }

    @Test(expected = RuntimeException.class)
    public void write_nullDataToWrite_NotOk() {
        fileService.write(null, "src/main/resources/report.csv");
    }
}
