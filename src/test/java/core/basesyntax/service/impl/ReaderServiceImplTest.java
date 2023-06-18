package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String EMPTY_REPORT = "src/test/resources/empty_report.csv";
    private static final String NO_FRUIT_REPORT = "src/test/resources/no_fruit_report.csv";
    private static final String INPUT_REPORT = "src/test/resources/input_report_1.csv";
    private ReaderService readerService = new ReaderServiceImpl();

    @Test
    void getListFromEmptyFile_Ok() {
        List<String> actualResult = readerService.readFromFile(EMPTY_REPORT);
        List<String> expectedResult = new ArrayList<>();

        assertIterableEquals(expectedResult, actualResult, "Test failed,"
                + " wrong data read from file");
    }

    @Test
    void getListNoFruitFile_Ok() {
        List<String> actualResult = readerService.readFromFile(NO_FRUIT_REPORT);
        List<String> expectedResult = List.of("type;fruit;quantity");

        assertEquals(expectedResult, actualResult, "Test failed, wrong data read from file");
    }

    @Test
    void getListFromValidFile_Ok() {
        List<String> actualResult = readerService.readFromFile(INPUT_REPORT);
        List<String> expectedResult = List.of("type;fruit;quantity", "b;banana;50",
                "b;apple;100", "b;orange;120", "s;banana;100", "p;banana;13", "r;apple;10",
                "p;apple;20", "p;banana;12", "s;banana;50", "s;orange;20", "p;orange;60",
                "r;orange;15");

        assertEquals(expectedResult, actualResult, "Test failed, wrong data read from file");
    }

    @Test
    void pathToFileIsNullOrEmpty_notOk() {
        String filePathEmpty = "";
        String filePathNull = null;

        assertAll("Incorrect path to file",
                () -> assertThrows(RuntimeException.class, () -> readerService
                        .readFromFile(filePathEmpty)),
                () -> assertThrows(RuntimeException.class, () -> readerService
                        .readFromFile(filePathNull))
        );
    }

    @Test
    void incorrectPathOrFileName_notOk() {
        String wrongFilePath1 = "src/test/input_report_1.csv";
        String wrongFilePath2 = "src/test/resources/";
        String wrongFileName = "src/test/resources/inputreport1.csv";

        assertAll("Incorrect path or file name",
                () -> assertThrows(RuntimeException.class, () -> readerService
                        .readFromFile(wrongFilePath1)),
                () -> assertThrows(RuntimeException.class, () -> readerService
                        .readFromFile(wrongFilePath2)),
                () -> assertThrows(RuntimeException.class, () -> readerService
                        .readFromFile(wrongFileName))
        );
    }
}
