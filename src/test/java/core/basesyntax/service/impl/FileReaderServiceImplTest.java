package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderServiceImpl fileReaderService;
    private static final String wrongFilePath = "src/main/";
    private static final String filePath = "src/main/res/input.csv";

    @Before
    public void initialize() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFile_WrongFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFile(wrongFilePath));
    }

    @Test
    public void readFile_Null_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFile(null));
    }
}