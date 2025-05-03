package core.basesyntax.service.impl;

import core.basesyntax.lib.WorkWithLocalFilesAndDirectory;
import core.basesyntax.service.WriterService;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WorkWithLocalFilesAndDirectory filesAndDirectoryService;
    private static WriterService writerService;
    private static final Path project = Path.of("").toAbsolutePath();
    private static final Path resources =
            Paths.get(project.toString(), "src", "test", "resources");
    private final Path fileNameNormal = resources.resolve("temporary-file.txt");
    private final Path fileNameFailed =
            Paths.get(resources.toString(), "bad").resolve("temporary-file.txt");

    @BeforeClass
    public static void beforeClass() {
        filesAndDirectoryService = new WorkWithLocalFilesAndDirectory();
        filesAndDirectoryService.createResourceDirectory(resources);
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_notEmptyFile_ok() {
        String expected = "Be great in act, as you have been in thought";
        writerService.write(fileNameNormal, expected);
        String actual = filesAndDirectoryService.readFromFile(fileNameNormal);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void write_emptyFile_ok() {
        String expected = "";
        writerService.write(fileNameNormal, expected);
        String actual = filesAndDirectoryService.readFromFile(fileNameNormal);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void write_notExistedFile_notOk() {
        writerService.write(fileNameFailed, "");
    }

    @After
    public void tearDown() {
        filesAndDirectoryService.deleteTestFile(fileNameNormal);
    }
}
