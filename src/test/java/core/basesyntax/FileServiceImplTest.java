package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.Fruit;
import core.basesyntax.basesyntax.service.FileServiceImpl;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileServiceImplTest {
    private static final String EMPTY_INPUT = "emptyInputData.csv";
    private static final String WRONG_INPUT_FORMAT = "wrongInputDataFormat.csv";
    private static final String OK_INPUT = "inputDataOk.csv";
    private static final String OK_RESULT = "report.csv";

    private static FileServiceImpl service;

    @BeforeAll
    public static void setUp() {
        service = new FileServiceImpl();
    }

    @Test
    void read_Ok() {
        List<String> expectedRows = new ArrayList<>();
        expectedRows.add("s,apple,100");
        expectedRows.add("b,apple,100");
        expectedRows.add("b,banana,20");

        List<String> fileRows = service.readFile(OK_INPUT);
        assertEquals(expectedRows, fileRows);
    }

    @Test
    void read_fileIsEmpty_Ok() {
        List<String> rows = service.readFile(EMPTY_INPUT);
        assertEquals(1, rows.size());
    }

    @Test
    void read_wrongInputDataFormat_Ok() {
        List<String> expectedRows = new ArrayList<>();
        expectedRows.add("s,apple,100");
        expectedRows.add("b/apple/100");
        expectedRows.add("b   banana   20");
        expectedRows.add("p----apple----20");

        List<String> fileRows = service.readFile(WRONG_INPUT_FORMAT);
        assertEquals(expectedRows, fileRows);
    }

    @Test
    void read_wrongFileName_notOk() {
        assertThrows(RuntimeException.class, () -> service.readFile("fdgjhdj"));
    }

    @Test
    void write_emptyStorage_ok() throws IOException {
        Storage.getFruits().clear();
        File tempFile = File.createTempFile("emptyResult", ".csv");
        service.writeToFile(tempFile.getName());
        assertEquals(0, Files.size(tempFile.toPath()));
        tempFile.deleteOnExit();
    }

    @Test
    void write_ok() throws IOException {
        Storage.getFruits().clear();
        Storage.addOrUpdateFruit("banana", new Fruit("banana", 152));
        Storage.addOrUpdateFruit("apple", new Fruit("apple", 90));

        File tempFile = File.createTempFile("testResult", ".csv");
        String tempFilePath = tempFile.getAbsolutePath();
        service.writeToFile(tempFilePath);
        Path pathTest = Paths.get(tempFilePath);

        String generatedContent = new String(Files.readAllBytes(pathTest), StandardCharsets.UTF_8)
                .trim();
        System.out.println("Generated file content:\n" + generatedContent);

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(OK_RESULT);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + OK_RESULT);
        }
        String expectedContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)
                .trim();

        System.out.println("Expected file content:\n" + expectedContent);

        assertEquals(expectedContent, generatedContent);

        tempFile.delete();
    }
}
