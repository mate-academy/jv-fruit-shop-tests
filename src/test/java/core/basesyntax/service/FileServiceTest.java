package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileServiceImpl;
import org.junit.Test;

public class FileServiceTest {
    
    private static final String DEFAULT_FILE_FROM_NAME = "src/test/resources/defaultFile";
    private static final String EMPTY_FILE_FROM_NAME = "src/test/resources/emptyFile";
    private static final String NONEXISTENT_FILE_NAME = "nonexistent";
    private static final String FILE_TO_WRITE_NAME = "src/test/resources/fileTo";
    
    @Test
    public void fileService_readFromFile_Ok() {
        FileService fileService = new FileServiceImpl();
        String expected = "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50" + System.lineSeparator();
        String actual = fileService.readFromFile(DEFAULT_FILE_FROM_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void fileService_readFromFile_emptyValue_Ok() {
        FileService fileService = new FileServiceImpl();
        String actual = fileService.readFromFile(EMPTY_FILE_FROM_NAME);
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    public void fileService_readFromFile_nonexistentFile_notOk() {
        FileService fileService = new FileServiceImpl();
        assertThrows(RuntimeException.class, () -> fileService.readFromFile(NONEXISTENT_FILE_NAME));
    }

    @Test
    public void fileService_writeToFile_Ok() {
        FileService fileService = new FileServiceImpl();
        String data = "test123" + System.lineSeparator();
        String expected = data;
        fileService.writeToFile(FILE_TO_WRITE_NAME, data);
        String actual = fileService.readFromFile(FILE_TO_WRITE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void fileService_writeToFile_emptyData_Ok() {
        FileService fileService = new FileServiceImpl();
        String data = "";
        String expected = data;
        fileService.writeToFile(FILE_TO_WRITE_NAME, data);
        String actual = fileService.readFromFile(FILE_TO_WRITE_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void fileService_writeToFile_nullValue_notOk() {
        FileService fileService = new FileServiceImpl();
        String data = null;
        assertThrows(NullPointerException.class,
                () -> fileService.writeToFile(FILE_TO_WRITE_NAME, data));
    }
}
