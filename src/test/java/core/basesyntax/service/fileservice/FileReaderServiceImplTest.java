package core.basesyntax.service.fileservice;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReader;
    private static String validFilePath;
    private static String invalidFilePath;
    private static List<String> expectedList;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
        expectedList = new ArrayList<>();
        validFilePath = "src/test/resources/testInput.csv";
        invalidFilePath = "notExistingPath";
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        String lines = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13";
        File file = new File(validFilePath);
        try {
            Files.writeString(file.toPath(), lines);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write data to file: " + validFilePath, e);
        }
    }

    @Test
    public void readFile_IsOk() {
        assertEquals(expectedList.toString(), fileReader.readFile(validFilePath).toString());
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_NotOk() {
        fileReader.readFile(invalidFilePath);
    }
}
