package core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class ReadServiceImplTest {
    private ReadServiceImpl fileReader;

    @Before
    public void setUp() {
        fileReader = new ReadServiceImpl();
    }

    @Test
    public void read_ValidFile_Ok() {
        String dataFromFile = fileReader.read("src/test/java/resources/valid_input.csv");
        assertNotNull(dataFromFile);
    }

    @Test
    public void read_FileRead_ExceptionThrown() {
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> fileReader.read("exceptionFile.csv"));
        assertEquals(runtimeException.getMessage(),
                "Can't read data from file exceptionFile.csv");
    }
}
