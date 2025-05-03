package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INPUT_FILE_PATH_OK
            = "src/main/java/core/basesyntax/resuorse/existing_file.csv";
    private static final String INPUT_FILE_PATH_NOTOK
            = "src/main/java/core/basesyntax/resuorse/non_existing_file.csv";
    private static final String INPUT_FILE_DATA_NOTOK
            = "src/main/java/core/basesyntax/resuorse/corrupted_file.csv";
    private static final String INPUT_FILE_PATH_EMPTY = "";
    private static final String INPUT_FILE_PATH_NULL = null;
    private static final String FILE_DATA_OK = "type,fruit,quantity"
            + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100";
    private static final String FILE_DATA_NOTOK = "b,banana,20\b,apple,100";
    private static final String FILE_DATA_EMPTY = "";
    private final ReaderServiceImpl readerService = new ReaderServiceImpl();

    @BeforeEach
    public void setUp() throws IOException {
        Files.write(Path.of(INPUT_FILE_PATH_OK), FILE_DATA_OK.getBytes());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(INPUT_FILE_PATH_OK));
        Files.deleteIfExists(Path.of(INPUT_FILE_PATH_NOTOK));
        Files.deleteIfExists(Path.of(INPUT_FILE_DATA_NOTOK));
    }

    @Test
    public void testReadFromFile_Input_File_Path_Ok() {
        List<String> actualData = readerService.readFromFile(INPUT_FILE_PATH_OK);
        assertNotNull(actualData);
    }

    @Test
    public void testReadFromFile_Input_File_Data_Ok() {
        String[] expectedDataArray = FILE_DATA_OK.split(System.lineSeparator());
        List<String> expectedData = Arrays.asList(expectedDataArray);
        List<String> actualData = readerService.readFromFile(INPUT_FILE_PATH_OK);
        assertEquals(expectedData, actualData);
    }

    @Test
    public void testReadFromFile_Input_File_Path_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(INPUT_FILE_PATH_NOTOK));
    }

    @Test
    public void testReadFromFile_Input_File_Path_Empty() {
        assertThrows(RuntimeException.class, () -> readerService
                .readFromFile(INPUT_FILE_PATH_EMPTY));
    }

    @Test
    public void testReadFromFile_Input_File_Path_Null() {
        assertThrows(RuntimeException.class, () -> readerService
                .readFromFile(INPUT_FILE_PATH_NULL));
    }

    @Test
    public void testReadFromFile_Input_File_Data_NotOk() throws IOException {
        Files.write(Path.of(INPUT_FILE_DATA_NOTOK), FILE_DATA_NOTOK.getBytes());
        List<String> expectedData = List.of(FILE_DATA_OK);
        List<String> actualData = readerService.readFromFile(INPUT_FILE_DATA_NOTOK);
        assertNotEquals(expectedData, actualData);
    }

    @Test
    public void testReadFromFile_Input_File_Data_Empty() throws IOException {
        Files.write(Path.of(INPUT_FILE_DATA_NOTOK), FILE_DATA_EMPTY.getBytes());
        List<String> expectedData = List.of(FILE_DATA_OK);
        List<String> actualData = readerService.readFromFile(INPUT_FILE_DATA_NOTOK);
        assertNotEquals(expectedData, actualData);
    }
}
