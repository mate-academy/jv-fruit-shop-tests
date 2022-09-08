package core.basesyntax.services.impl;

import core.basesyntax.services.FileWriterService;
import java.io.File;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static final String FILE_PATH
            = "src/test/java/core/basesyntax/resources/file_writer.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void fileWriter_Ok() {
        fileWriterService.writeToFile(FILE_PATH, "Data from report");
        Assert.assertTrue("File file_writer not created", new File(FILE_PATH).exists());
    }
}
