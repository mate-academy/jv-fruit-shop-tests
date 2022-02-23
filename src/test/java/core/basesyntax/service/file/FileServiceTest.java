package core.basesyntax.service.file;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceTest {
    private static FileService fileService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileService = new FileService();
    }

    @Test(expected = RuntimeException.class)
    public void noFileToRead_Exception() {
        fileService.read("src/activities.csv");
    }

    @Test(expected = RuntimeException.class)
    public void noFileToWrite_Exception() {
        fileService.write("","wrongsrc/activities.csv");
    }

    @Test(expected = RuntimeException.class)
    public void nullDataToWrite_Exception() {
        fileService.write(null, "src/main/resources/report.csv");
    }
}
