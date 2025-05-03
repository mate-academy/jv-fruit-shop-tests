package core.basesyntax.service.impl;

import core.basesyntax.service.CreateFileService;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateFileServiceImplTest {
    private static final String CREATED_FILE = "src/test/resources/newFile.csv";
    private static CreateFileService createFileService;

    @BeforeClass
    public static void beforeClass() {
        createFileService = new CreateFileServiceImpl();
    }

    @Test
    public void createFile_OK() {
        createFileService.createFile(CREATED_FILE);
        Assert.assertTrue(Files.exists(Path.of(CREATED_FILE)));
    }

    @Test(expected = NullPointerException.class)
    public void createFile_NotOK() {
        createFileService.createFile(null);
    }
}
