package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileServiceImplTest {
    private static final String EMPTY_INPUT = "inputEmpty.csv";
    private static final String WRONG_FORMAT_INPUT = "inputWrongFormat.csv";
    private static final String GOOD_INPUT = "inputOk.csv";
    private static final String GOOD_RESULT = "resultOk.csv";

    private static FileServiceImpl service;

    @BeforeAll
    public static void setUp() {
        service = new FileServiceImpl();
    }

    @Test
    void read_ok() {
        List<String> expectedRows = new ArrayList<>();
        expectedRows.add("b,banana,20");
        expectedRows.add("b,apple,100");
        expectedRows.add("s,banana,100");

        List<String> fileRows = service.readFile(GOOD_INPUT);
        assertEquals(expectedRows, fileRows);
    }

    @Test
    void read_wrongNameFile_notOk() {
        assertThrows(NullPointerException.class, () -> service.readFile("blabla"));
    }

    @Test
    void read_wrongDataFormat_ok() {
        List<String> expectedRows = new ArrayList<>();
        expectedRows.add("b,banana,20");
        expectedRows.add("b,apple,100");
        expectedRows.add("s--banana--100");
        expectedRows.add("p,banana,13");

        List<String> fileRows = service.readFile(WRONG_FORMAT_INPUT);
        assertEquals(expectedRows, fileRows);
    }

    @Test
    void read_emptyFile_ok() {
        List<String> rows = service.readFile(EMPTY_INPUT);
        assertEquals(0, rows.size());
    }

    @Test
    void write_emptyStorage_ok() throws IOException {
        Storage.fruits.clear();
        File tempFile = File.createTempFile("emptyResult", ".csv");
        service.writeToFile(tempFile.getName());
        assertEquals(0, Files.size(tempFile.toPath()));
        tempFile.deleteOnExit();
    }

    @Test
    void write_ok() throws IOException {
        Storage.fruits.clear();
        Storage.fruits.add(new Fruit("banana", 152));
        Storage.fruits.add(new Fruit("apple", 90));

        File tempFile = File.createTempFile("testResult", ".csv");
        service.writeToFile(tempFile.getName());
        Path pathTest = Paths.get(tempFile.getName());

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(GOOD_RESULT);

        byte[] fileTestBytes = Files.readAllBytes(pathTest);
        byte[] fileGoodBytes = inputStream.readAllBytes();
        assertArrayEquals(fileGoodBytes, fileTestBytes);
        tempFile.deleteOnExit();
    }
}
