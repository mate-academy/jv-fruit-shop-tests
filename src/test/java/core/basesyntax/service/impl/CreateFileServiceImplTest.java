package core.basesyntax.service.impl;

import core.basesyntax.service.CreateFileService;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateFileServiceImplTest {
    private static final String CREATED_FILE = "src/test/resources/newFile.csv";
    private CreateFileService createFileService;

    @Before
    public void setUp() {
        createFileService = new CreateFileServiceImpl();
    }

    @Test
    public void create_File_OK() {
        createFileService.createFile(CREATED_FILE);
        Assert.assertTrue(Files.exists(Path.of(CREATED_FILE)));
    }

    @Test(expected = NullPointerException.class)
    public void create_File_NotOK() {
        createFileService.createFile(null);
    }
}
