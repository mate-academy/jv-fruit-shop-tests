package core.basesyntax.service.impl;

import core.basesyntax.lib.WorkWithLocalFilesAndDirectory;
import core.basesyntax.service.ReaderService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static WorkWithLocalFilesAndDirectory filesAndDirectoryService;
    private static ReaderService readerService;
    private static final Path project = Path.of("").toAbsolutePath();
    private static final Path resources =
            Paths.get(project.toString(), "src", "test", "resources");
    private final Path fileName = resources.resolve("temporary-file.txt");

    @BeforeClass
    public static void beforeClass() {
        filesAndDirectoryService = new WorkWithLocalFilesAndDirectory();
        filesAndDirectoryService.createResourceDirectory(resources);
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_notEmptyFile_ok() {
        String textForTest = "Be great in act, as you have been in thought";
        filesAndDirectoryService.createTestFile(fileName, textForTest);
        List<String> expected = List.of(textForTest);
        List<String> actual = readerService.read(fileName);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void read_emptyFile_ok() {
        filesAndDirectoryService.createTestFile(fileName, "");
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.read(fileName);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void read_notExistedFile_notOk() {
        readerService.read(fileName);
    }

    @After
    public void tearDown() {
        filesAndDirectoryService.deleteTestFile(fileName);
    }
}
