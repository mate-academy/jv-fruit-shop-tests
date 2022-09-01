package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileCreationService;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class FileCreationServiceImplTest {
    private FileCreationService fileCreationService;
    private String fileName;

    @Before
    public void setUp() {
        fileCreationService = new FileCreationServiceImpl();
    }

    @Test
    public void creatFile_validFileName_Ok() {
        String fileName = "src/test/resources/testReport.csv";
        fileCreationService.createFile(fileName);
        assertTrue(Files.exists(Paths.get(fileName)));
    }

    @Test(expected = RuntimeException.class)
    public void createFile_fileExist_NotOk() {
        String fileName = "src/test/resources/report.csv";
        fileCreationService.createFile(fileName);
    }

    @Test(expected = RuntimeException.class)
    public void createFile_NotValidFileName_NotOk() {
        String fileName = "";
        fileCreationService.createFile(fileName);
    }

}
