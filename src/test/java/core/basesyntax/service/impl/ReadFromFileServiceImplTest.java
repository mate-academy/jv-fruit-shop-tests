package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReadFromFileService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileServiceImplTest {
    public static final String PATH = "src/test/resources/dataFrom.csv";
    private static ReadFromFileService readFromFileService;

    @BeforeClass
    public static void setUp() {
        readFromFileService = new ReadFromFileServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        String actual = readFromFileService.readFromFile(PATH);
        String expected = "Text for ReadFromFileImpl class tests";
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_InvalidPath_NotOk() {
        assertThrows(RuntimeException.class, () -> readFromFileService.readFromFile("invalidPath"));
    }

    @Test
    public void readFromFile_NullPath_NotOk() {
        assertThrows(RuntimeException.class, () -> readFromFileService.readFromFile(null));
    }
}
