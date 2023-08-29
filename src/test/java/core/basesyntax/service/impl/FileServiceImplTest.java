package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static FileService fileService;
    private static final String REPORT_TITLE = "fruit,quantity";
    private static final String DAY_ACTIVITY_CORRECT_FILENAME
            = "src/test/resources/correctSourceFile.csv";
    private static final String DAY_REPORT_CORRECT_FILENAME
            = "src/test/resources/correctReportFile.csv";
    private static final String INCORRECT_FILENAME
            = "src/wrongPackage/incorrectFileName";
    private static final List<String> correctList = new ArrayList<>();
    private static String correctContent;

    @BeforeAll
    static void initVariables() {
        fileService = new FileServiceImpl();

        correctList.add("type,fruit,quantity");
        correctList.add("b,banana,20");
        correctList.add("b,apple,100");
        correctList.add("s,banana,100");
        correctList.add("p,banana,13");
        correctList.add("r,apple,10");
        correctList.add("p,apple,20");
        correctList.add("p,banana,5");
        correctList.add("s,banana,50");

        correctContent = REPORT_TITLE
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
    }

    @Test
    void readFromFile_correctFileName_ok() {
        List<String> actualList = fileService.readFromFile(DAY_ACTIVITY_CORRECT_FILENAME);
        assertEquals(correctList, actualList);
    }

    @Test
    void readFromFile_incorrectFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.readFromFile(INCORRECT_FILENAME));
    }

    @Test
    void readFromFile_emptyFileName_notOk() {
        String emptyFileName = "";
        assertThrows(RuntimeException.class,
                () -> fileService.readFromFile(emptyFileName));
    }

    @Test
    void writeToFile_correctFileName_ok() {
        fileService.writeToFile(DAY_REPORT_CORRECT_FILENAME, correctContent);
    }

    @Test
    void writeToFile_incorrectFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(INCORRECT_FILENAME, correctContent));
    }

    @Test
    void writeToFile_nullFileName_notOk() {
        String nullFileName = null;
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(nullFileName, correctContent));
    }

    @Test
    void writeToFile_nullContent_notOk() {
        String nullContent = null;
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(DAY_REPORT_CORRECT_FILENAME, nullContent));
    }

}
