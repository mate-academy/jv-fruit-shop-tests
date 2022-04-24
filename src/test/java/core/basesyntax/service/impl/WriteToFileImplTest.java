package core.basesyntax.service.impl;

import core.basesyntax.service.WriteToFileService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileImplTest {
    private static final String FILE_PATH = "src/test/java/core"
            + "/basesyntax/valid_fruitReport_file.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90"
            + System.lineSeparator();
    private static WriteToFileService writeToFileService;
    private static List<String> actual;
    private List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        writeToFileService = new WriteToFileImpl();
    }

    @Test
    public void writeToFile_validPathFile_Ok() {
        writeToFileService.writeToFile(FILE_PATH, REPORT);
        try {
            actual = Files.readAllLines(Path.of(String.valueOf(new File(FILE_PATH))));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullFilePath_notOk() {
        writeToFileService.writeToFile(null, REPORT);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullReport_notOk() {
        writeToFileService.writeToFile(FILE_PATH, null);
    }
}
