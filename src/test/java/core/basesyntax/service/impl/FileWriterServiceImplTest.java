package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String CORRECT_FILE_PATH =
            "src/test/resources/fruit-shop-report.csv";
    private static final String NON_EXISTING_FILE_PATH =
            "src/test/resources/random.csv";
    private static final String REPORT =
            "fruit,quantity\nbanana,152\napple,90";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_filePathIsNull_notOk() {
        fileWriterService.writeToFile(null, REPORT);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_reportIsNull_notOk() {
        fileWriterService.writeToFile(CORRECT_FILE_PATH, null);
    }

    @Test
    public void writeToFile_noSuchFile_notOk() {
        Assert.assertFalse(new File(NON_EXISTING_FILE_PATH).exists());
    }

    @Test
    public void writeToFile_successfulWriting_ok() throws IOException {
        fileWriterService.writeToFile(CORRECT_FILE_PATH, REPORT);
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90");
        List<String> actual;
        actual = Files.readAllLines(Path.of(CORRECT_FILE_PATH));
        Assert.assertEquals(expected, actual);
    }
}
