package core.basesyntax.service.implemantation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileServiceImplTest {
    private FileService fileService;

    @Before
    public void setUp() {
        fileService = new FileServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_fileNotExists_notOk() {
        fileService.readFromFile(Path.of("src/main/resources/FakeURL.csv"));
    }

    @Test
    public void readFromFile_correctData_ok() throws IOException {
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
        String testFileData = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50";
        Files.writeString(Path.of("src/test/resources/fruitsTests.csv"), testFileData);
        List<String> actual = fileService
                .readFromFile(Path.of("src/test/resources/fruitsTests.csv"));
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_pathAsNull_notOk() {
        fileService.writeToFile("Input data", Path.of(null));
    }

    @Test
    public void writeToFile_correctData_ok() throws IOException {
        String expected = "Input data";
        fileService.writeToFile(expected, Path.of("src/test/resources/TestReport.txt"));
        String actual = Files.readString(Path.of("src/test/resources/TestReport.txt"));
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("src/test/resources/TestReport.txt"));
        Files.deleteIfExists(Path.of("src/test/resources/fruitsTests.csv"));
    }
}
