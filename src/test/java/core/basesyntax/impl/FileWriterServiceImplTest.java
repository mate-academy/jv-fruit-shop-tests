package core.basesyntax.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String outFileName = "src/test/java/resources/testFile.csv";
    private static final String comparedFileName = "src/test/java/resources/comparedFile.csv";
    private static FileWriterServiceImpl fileWriterService;
    private static File file;
    private static File comparedFile;
    private final String report = "fruits,quantity\r\nbanana,152\r\n";

    @BeforeClass
    public static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
        file = new File(outFileName);
        comparedFile = new File(comparedFileName);
    }

    @Test
    public void writeToFile_correctPath_Ok() throws IOException {
        fileWriterService.writeToFile(report, outFileName);
        byte[] f1 = Files.readAllBytes(file.toPath());
        byte[] f2 = Files.readAllBytes(comparedFile.toPath());
        Assert.assertTrue(file.exists());
        Assert.assertArrayEquals(f1, f2);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_fileNull_notOk() {
        fileWriterService.writeToFile(report, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_reportNull_notOk() {
        fileWriterService.writeToFile(null, outFileName);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_fileEmpty_notOk() {
        fileWriterService.writeToFile(report, "");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_reportEmpty_notOk() {
        fileWriterService.writeToFile(report, "");
    }
}
