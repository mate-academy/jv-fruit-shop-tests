package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {

    @Test
    void readFromFile_validInput_Ok() {
        final String pathFromFile = "src/test/resources/daily_transactions_test.csv";
        final String line0 = "type,fruit,quantity";
        final String line1 = "b ,banana, 20";
        final String lineLast = "s,banana,50";

        FileServiceImpl fileService = new FileServiceImpl();
        List<String> lines = fileService.readFromFile(pathFromFile);
        assertEquals(line0, lines.get(0));
        assertEquals(line1, lines.get(1));
        assertEquals(lineLast, lines.get(lines.size() - 1));
    }

    @Test
    void readFromFile_invalidPath_notOk() {
        final String wrongPathFromFile = "src/test/resources/_daily_transactions_test.csv";
        final String nullPathFromFile = null;
        final String emptyPathFromFile = "";

        FileServiceImpl fileService = new FileServiceImpl();
        assertThrows(RuntimeException.class, () -> fileService.readFromFile(wrongPathFromFile),
                "RuntimeException expected");
        assertThrows(RuntimeException.class, () -> fileService.readFromFile(nullPathFromFile),
                "RuntimeException expected");
        assertThrows(RuntimeException.class, () -> fileService.readFromFile(emptyPathFromFile),
                "RuntimeException expected");
    }

    @Test
    void writeToFile_validInput_Ok() {
        final String pathToFile = "src/test/resources/report_test.csv";
        final String line0 = "fruit,quantity";
        final String line1 = "banana,150";
        final String lineLast = "apple,90";
        List<String> lines = List.of(line0, line1, lineLast);

        FileServiceImpl fileService = new FileServiceImpl();
        fileService.writeToFile(lines, pathToFile);
        List<String> linesFromFile = fileService.readFromFile(pathToFile);
        assertEquals(lines, linesFromFile);
    }

    @Test
    void writeToFile_invalidPath_notOk() {
        final String wrongPathToFile = "wrong_src/test/resources/report_test.csv";
        final String nullPathToFile = null;
        final String emptyPathToFile = "";
        List<String> lines = List.of("fruit,quantity");

        FileServiceImpl fileService = new FileServiceImpl();
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(lines, wrongPathToFile),
                "RuntimeException expected");
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(lines, nullPathToFile),
                "RuntimeException expected");
        assertThrows(RuntimeException.class,
                () -> fileService.writeToFile(lines, emptyPathToFile),
                "RuntimeException expected");
    }

    @Test
    void writeToFile_invalidLines_notOk() {
        final String pathToFile = "src/test/resources/report_test.csv";
        List<String> lines = null;

        FileServiceImpl fileService = new FileServiceImpl();
        assertThrows(RuntimeException.class, () -> fileService.writeToFile(lines, pathToFile),
                "RuntimeException expected");
    }
}
