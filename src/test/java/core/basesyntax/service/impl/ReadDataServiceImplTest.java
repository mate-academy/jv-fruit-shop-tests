package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import core.basesyntax.service.ReadDataService;
import core.basesyntax.util.DataValidator;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadDataServiceImplTest {
    private static final String SOURCE_DATA_FILE_PATH =
            "src/test/resources/inputDataTest.csv";
    private static final String FILE_NAME = "inputDataTest.csv";
    private static final String EMPTY_FILE_NAME = "inputDataTestEmpty.csv";
    private static final String NON_EXISTED_FILE_PATH = "";
    private static final String EMPTY_DATA_TEST_FILE_PATH =
            "src/test/resources/inputDataTestEmpty.csv";

    private DataValidator dataValidatorMock;
    private ReadDataService readDataService;
    private List<String> testData;
    private File file;
    private BufferedReader bufferedReaderMock;
    private List<String> expectedData;

    @BeforeEach
    void setUp() {
        dataValidatorMock = mock(DataValidator.class);
        file = mock(File.class);
        bufferedReaderMock = mock(BufferedReader.class);
        readDataService = new ReadDataServiceImpl(dataValidatorMock);
        testData = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
    }

    @Test
    void readData_ValidFile_ok() throws Exception {
        when(bufferedReaderMock.readLine()).thenReturn("b", "apple", "100", null);
        when(file.getName()).thenReturn(FILE_NAME);
        whenNew(File.class).withArguments(SOURCE_DATA_FILE_PATH).thenReturn(file);
        doNothing().when(dataValidatorMock).validateIsSourceDataEmpty(anyList());
        List<String> result = readDataService.readData(SOURCE_DATA_FILE_PATH);
        List<String> trimmedTestData = trimListElements(testData);
        List<String> trimmedResult = trimListElements(result);
        assertEquals(trimmedTestData, trimmedResult);
        verify(dataValidatorMock).validateIsSourceDataEmpty(anyList());
    }

    @Test
    void readData_EmptyFile_notOk() throws Exception {
        expectedData = Collections.emptyList();
        when(bufferedReaderMock.readLine()).thenReturn(null);
        when(file.getName()).thenReturn(EMPTY_FILE_NAME);
        whenNew(File.class).withArguments(EMPTY_DATA_TEST_FILE_PATH).thenReturn(file);
        doNothing().when(dataValidatorMock).validateIsSourceDataEmpty(anyList());
        List<String> result = readDataService.readData(EMPTY_DATA_TEST_FILE_PATH);
        assertEquals(expectedData, result);
        verify(dataValidatorMock).validateIsSourceDataEmpty(anyList());
    }

    @Test
    void readData_InvalidFile_notOk() throws Exception {
        when(file.getName()).thenReturn(FILE_NAME);
        whenNew(File.class).withArguments(NON_EXISTED_FILE_PATH).thenReturn(file);
        whenNew(BufferedReader.class).withAnyArguments()
                .thenThrow(new IOException("File not found"));
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readDataService.readData(NON_EXISTED_FILE_PATH));
        assertEquals("Can't open the file", exception.getMessage());
    }

    private List<String> trimListElements(List<String> list) {
        return list.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
