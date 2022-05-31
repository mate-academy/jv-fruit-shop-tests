package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class FileWriterServiceImplTest {
    private static final String FILE_TO_PATH = "src/main/resources/report.csv";

    private static final String FILE_WRONG_TO_PATH = "src/resources/report.css";
    private final FileWriterService writerService = new FileWriterServiceImpl();

    @Test
    public void writeFile_ok() {
        StringBuilder stringBuilderExcepted = new StringBuilder();
        stringBuilderExcepted.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple, 252");
        String reportExcepted = stringBuilderExcepted.toString();
        writerService.writeToFile(FILE_TO_PATH, reportExcepted);

        StringBuilder stringBuilderActual = new StringBuilder();
        File file = new File(FILE_TO_PATH);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_TO_PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilderActual.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't find file");
        } catch (IOException e) {
            throw new RuntimeException("Can't read file");
        }
        String reportActual = stringBuilderActual
                .substring(0, stringBuilderActual.lastIndexOf(System.lineSeparator()));

        assertEquals(reportExcepted,reportActual);
    }

    @Test
    public void writeFileNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(FILE_TO_PATH, null));
    }

    @Test
    public void writeFileWrongPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(FILE_WRONG_TO_PATH, ""));
    }

    @Test
    public void writeFileNullPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null, ""));
    }
}
