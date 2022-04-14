package core.basesyntax;

import core.basesyntax.service.FileService;
import core.basesyntax.service.implementions.FileServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static final String FILE_NAME_FROM = "src/test/java/resources/inputCorrect.csv";
    private static final String FILE_NAME_TO = "src/test/java/resources/reportPerfect.csv";
    private static final String FILE_NAME_WRONG = "src/test/java/resources/wrong.csv";
    private static FileService fileService;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readDataFromFile_isOk() {
        List<String> actualResult = fileService.readDataFromFile(FILE_NAME_FROM);
        List<String> expectedResult = List.of("type,fruit,quantity", "b,banana,20",
                            "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void readDataFromFile_NonExistentFile_NotOk() {
        fileService.readDataFromFile(FILE_NAME_WRONG);
    }

    @Test
    public void writeToFile_isOk() {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                                + "apple,90" + System.lineSeparator()
                                + "banana,152" + System.lineSeparator();
        fileService.writeToFile(FILE_NAME_TO, expectedReport);
        String actualReport = readFromFile(FILE_NAME_TO);
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_InvalidFile_NotOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                        + "apple,90" + System.lineSeparator()
                        + "banana,152" + System.lineSeparator();
        fileService.writeToFile("", report);
        fileService.writeToFile(FILE_NAME_WRONG, null);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + fileName, e);
        }
    }
}
