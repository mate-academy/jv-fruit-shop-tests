package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String DEFAULT_FILE_NAME = "src/test/resources/testData.csv";
    private static final String RESULT_FILE_NAME
            = "src/test/resources/testResult.csv";
    private static final String NOT_VALID_FILE_NAME = "notValid.csv";
    private static final List<String> DEFAULT_DATA = new ArrayList<>();
    private static final FileService FILE_SERVICE = new FileServiceImpl();
    private static final String VALID_RESULT_DATA = """
            banana,107
            apple,100
            orange,20""";
    private static final List<String> RESULT_LIST = new ArrayList<>();

    @Test
    void fileServiceReadValid_Ok() {
        DEFAULT_DATA.add("type,fruit,quantity,");
        DEFAULT_DATA.add("b,banana,20");
        DEFAULT_DATA.add("b,apple,100");
        DEFAULT_DATA.add("b,orange,20");
        DEFAULT_DATA.add("s,banana,100");
        DEFAULT_DATA.add("p,banana,13");
        assertEquals(FILE_SERVICE.read(DEFAULT_FILE_NAME), DEFAULT_DATA);
    }

    @Test
    void fileServiceReadNotValidFileName_notOk() {
        assertThrows(RuntimeException.class, () -> FILE_SERVICE.read(NOT_VALID_FILE_NAME));
    }

    @Test
    void fileServiceWriteValid_Ok() {
        RESULT_LIST.addAll(List.of(VALID_RESULT_DATA.split("\n")));
        FILE_SERVICE.write(VALID_RESULT_DATA, RESULT_FILE_NAME);
        assertEquals(RESULT_LIST, FILE_SERVICE.read(RESULT_FILE_NAME));
    }

    @Test
    void fileServiceWriteNotValidFileName_notOk() {
        assertThrows(RuntimeException.class, () -> FILE_SERVICE
                .write(NOT_VALID_FILE_NAME, VALID_RESULT_DATA));
    }
}
