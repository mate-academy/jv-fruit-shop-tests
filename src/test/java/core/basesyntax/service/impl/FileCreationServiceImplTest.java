package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileCreationService;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class FileCreationServiceImplTest {
    private static final String newFileName = "src/test/resources/testReport.csv";
    private static final String existingFileName = "src/test/resources/report.csv";
    private FileCreationService fileCreationService;

    @Before
    public void setUp() {
        fileCreationService = new FileCreationServiceImpl();
    }

    @Test
    public void creatFile_validFileName_Ok() {
        fileCreationService.createFile(newFileName);
        assertTrue(Files.exists(Paths.get(newFileName)));
    }

    @Test(expected = RuntimeException.class)
    public void createFile_fileExist_NotOk() {
        fileCreationService.createFile(existingFileName);
    }

    @Test(expected = RuntimeException.class)
    public void createFile_NotValidFileName_NotOk() {
        String fileName = "";
        fileCreationService.createFile(fileName);
    }

}
