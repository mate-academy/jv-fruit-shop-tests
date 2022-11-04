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
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
        expected = new ArrayList<>();
        validFilePath = "src/main/resources/testInput.csv";
        invalidFilePath = "notExistingPath";
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
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
    public void readFileIs_Ok() {
        assertEquals(expected.toString(), fileReader.readFile(validFilePath).toString());
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_NotOk() {
        fileReader.readFile(invalidFilePath);
    }
}
