package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static FileService fileService;
    private static String testFileName;

    @BeforeAll
    static void beforeAll() {
        fileService = new FileServiceImpl();
        testFileName = "src/test/resources/test.csv";
    }

    @Test
    void writeAndRead6() {
        String testData = "b,apple,10";
        byte[] testDataBytes = testData.getBytes();
        fileService.write(testData, testFileName);
        List<String> readData = fileService.read(testFileName);
        assertEquals(Arrays.asList(testData),readData);
    }

    @Test
    public void testWriteRuntimeException() {
        String nonExistingDirectory = "non-existing-directory/test.txt";
        assertThrows(RuntimeException.class, () -> fileService.write("Data", nonExistingDirectory));
    }

    @Test
    public void testReadRuntimeException() {
        String nonExistingFile = "non-existing-file.txt";
        assertThrows(RuntimeException.class, () -> fileService.read(nonExistingFile));
    }
}
