package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String TEMP_DIRECTORY = "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator;
    private static final String EMPTY_FILE_NAME = TEMP_DIRECTORY + "emptyReportToRead.csv";
    private static final String FILE_NAME = TEMP_DIRECTORY + "reportToRead.csv";
    private static final String WRONG_FILE_NAME = TEMP_DIRECTORY + "reportToRead21.csv";

    private static final List<String> EMPTY_RESULT = new ArrayList<>();
    private static final List<String> EXPECTED_RESULT = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        EXPECTED_RESULT.add("type,fruit,quantity");
        EXPECTED_RESULT.add("b,banana,20");
        EXPECTED_RESULT.add("b,apple,100");
        EXPECTED_RESULT.add("s,banana,100");
        EXPECTED_RESULT.add("p,banana,115");
        EXPECTED_RESULT.add("r,apple,10");
        EXPECTED_RESULT.add("p,apple,20");
        EXPECTED_RESULT.add("p,banana,5");
        EXPECTED_RESULT.add("s,banana,50");
        Files.writeString(Path.of(EMPTY_FILE_NAME), "");
        Files.writeString(Path.of(FILE_NAME), """
                type,fruit,quantity
                b,banana,20
                b,apple,100
                s,banana,100
                p,banana,115
                r,apple,10
                p,apple,20
                p,banana,5
                s,banana,50""");
    }

    @AfterEach
    void reset() {
        EXPECTED_RESULT.clear();
    }

    @Test
    void readFromEmptyFile_OK() {
        FileReader fileWork = new FileReaderImpl(EMPTY_FILE_NAME);
        List<String> actualResult = fileWork.read();
        Assertions.assertEquals(EMPTY_RESULT,actualResult,
                "Test failed! You should returned empty List.");
    }

    @Test
    void readFromFile_OK() {
        FileReader fileWork = new FileReaderImpl(FILE_NAME);
        List<String> actualResult = fileWork.read();
        Assertions.assertEquals(EXPECTED_RESULT,actualResult,
                "Test failed! You should returned: "
                        + EXPECTED_RESULT + "but was: " + actualResult);
    }

    @Test
    void convertList_ThrowsExceptions_OK() {
        FileReader fileWork = new FileReaderImpl(WRONG_FILE_NAME);
        Assertions.assertThrows(RuntimeException.class, fileWork::read);
    }
}
