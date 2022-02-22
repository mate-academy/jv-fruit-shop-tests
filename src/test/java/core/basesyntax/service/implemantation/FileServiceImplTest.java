package core.basesyntax.service.implemantation;

import core.basesyntax.service.FileService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileServiceImplTest {
    private FileService fileService;

    @Before
    public void setUp() {
        fileService = new FileServiceImpl();
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("src/test/resources/TestReport.txt"));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileNotExists_notOk() {
        fileService.readFromFile(Path.of("src/main/resources/FakeURL.csv"));
    }

    @Test
    public void readFromFile_correctData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileService.readFromFile(Path.of("src/main/resources/fruits.csv"));
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_pathAsNull_notOk() {
        fileService.writeToFile("Input data", Path.of(null));
    }

    @Test
    public void writeToFile_correctData_ok() {
        assertTrue(fileService.writeToFile("Input data",
                Path.of("src/test/resources/TestReport.txt")));
    }
}
