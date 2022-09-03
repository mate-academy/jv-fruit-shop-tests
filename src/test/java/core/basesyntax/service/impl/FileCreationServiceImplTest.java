package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileCreationService;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileCreationServiceImplTest {
    private static final String NEW_FILE_NAME = "src/test/resources/testReport.csv";
    private static final String EXISTING_FILE_NAME = "src/test/resources/report.csv";
    private static FileCreationService fileCreationService;

    @BeforeClass
    public static void beforeClass() {
        fileCreationService = new FileCreationServiceImpl();
    }

    @Test
    public void creatFile_FileNameIsValid_Ok() {
        fileCreationService.createFile(NEW_FILE_NAME);
        assertTrue(Files.exists(Paths.get(NEW_FILE_NAME)));
    }

    @Test(expected = RuntimeException.class)
    public void createFile_fileExist_NotOk() {
        fileCreationService.createFile(EXISTING_FILE_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void createFile_FileNameIsNotValid_NotOk() {
        String fileName = "";
        fileCreationService.createFile(fileName);
    }

}
