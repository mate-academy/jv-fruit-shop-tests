package core.basesyntax.service.fileservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static final String INPUT_CSV = "src/test/resources/input.csv";
    private static final String OUTPUT_CSV = "src/test/resources/output.csv";
    private static final String INCORRECT_PATH = "src/test/java";
    private static FileService fileService;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readDataFromFile_correctPath_Ok() throws IOException {
        List<String> expected = Files
                .readAllLines(Path.of(INPUT_CSV));
        List<String> actual = fileService.readDataFromFile(INPUT_CSV);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromFile_incorrectPath_NotOk() {
        fileService.readDataFromFile(INCORRECT_PATH);
    }

    @Test
    public void writeDataToFile_Ok() {
        String data = "fruit,quantity\n"
                + "banana,150\n"
                + "apple,150";
        fileService.writeDataToFile(OUTPUT_CSV, data);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,150");
        expected.add("apple,150");
        List<String> actual = fileService.readDataFromFile(OUTPUT_CSV);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_incorrectPath_NotOk() {
        fileService.writeDataToFile(INCORRECT_PATH, "");
    }

    @AfterClass
    public static void deleteDataFromFile() throws IOException {
        Files.deleteIfExists(Path.of(OUTPUT_CSV));
        Files.deleteIfExists(Path.of(INPUT_CSV));
    }
}
