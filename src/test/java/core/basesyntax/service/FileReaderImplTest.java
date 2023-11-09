package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE_NAME = "test1.csv";
    private static final String NONEXISTENT_FILE_NAME = "NONEXISTENT_FILE_NAME";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void testReadFile_ValidFile_Ok() {
        //Arrange
        List<String> assertList = new ArrayList<>();
        assertList.add("fruit,quantity");
        assertList.add("banana,152");
        assertList.add("apple,90");

        //Act
        List<String> resultList = fileReader.readFile(VALID_FILE_NAME);

        //Assert
        assertEquals(assertList, resultList);
    }

    @Test
    void testReadFile_NonExistentFile_NotOk() {
        //Assert
        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> fileReader
                .readFile(NONEXISTENT_FILE_NAME));

        assertEquals("Cant read file: " + NONEXISTENT_FILE_NAME, exception.getMessage());
    }
}
