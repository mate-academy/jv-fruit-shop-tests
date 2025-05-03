package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileServiceImplTest {
    private static final String FROM_FILE_PATH = "src/test/resources/test.csv";
    private static final String TO_FILE_PATH = "src/test/resources/Report.csv";
    private static final String INCORRECT_FILE_PATH = "...";
    private static final String CORRECT_REPORT = "fruit,quantity\nbanana,152\napple,90";
    private static FileService fileService;

    @BeforeAll
    public static void setUp() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readFromFile_CorrectPath_Ok() {
        File newFile = new File(FROM_FILE_PATH);
        List<String> expected;
        try {
            expected = Files.readAllLines(newFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path "
                    + FROM_FILE_PATH, e);
        }
        List<String> actual = fileService.readFromFile(FROM_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_IncorrectPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fileService.readFromFile(INCORRECT_FILE_PATH);
        });
    }

    @Test
    public void writeDataToFile_CorrectData_Ok() {
        fileService.writeDataToFile(CORRECT_REPORT, TO_FILE_PATH);
        String actual = fileService.readFromFile(TO_FILE_PATH).stream()
                .reduce("", (sum, string) -> sum + string + System.lineSeparator())
                .trim();
        assertEquals(CORRECT_REPORT, actual);
    }
}
