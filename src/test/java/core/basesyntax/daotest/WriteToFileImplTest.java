package core.basesyntax.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.WriteToFile;
import core.basesyntax.dao.daoimpl.WriteToFileImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteToFileImplTest {
    public static final String EMPTY_FILE_PATH = "src/test/resources/";
    public static final String FILE_PATH_OK = "src/test/resources/output.csv";
    public static final String WRONG_FILE_PATH = "src/test/resouRRRces/blabla";
    private static WriteToFile writer;

    @BeforeAll
    static void beforeAll() {
        writer = new WriteToFileImpl();
    }

    @Test
    void writeToFile_Ok() throws IOException {
        writer.writeToFile(report(), FILE_PATH_OK);
        File file = new File(FILE_PATH_OK);
        String actual = String.join(System.lineSeparator(),
                Files.readAllLines(file.toPath()));
        assertEquals(report(), actual);
    }

    @Test
    void writeToFile_emptyReport_Ok() throws IOException {
        writer.writeToFile("fruit,quantity", FILE_PATH_OK);
        File file = new File(FILE_PATH_OK);
        String actual = String.join(System.lineSeparator(),
                Files.readAllLines(file.toPath()));
        assertEquals("fruit,quantity", actual);
    }

    @Test
    void writeToFile_NotOk() {
        writer.writeToFile(report(), FILE_PATH_OK);
        String actual = "";
        assertNotEquals(report(), actual);
    }

    @Test
    void writeToFile_WrongFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(report(), WRONG_FILE_PATH));
    }

    @Test
    void writeToFIle_nullFilePath_NotOk() {
        assertThrows(NullPointerException.class, () -> writer.writeToFile(report(), null));
    }

    @Test
    void writeToFIle_emptyFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> writer.writeToFile(report(), EMPTY_FILE_PATH));
    }

    private String report() {
        return "fruit,quantity"
                + System.lineSeparator()
                + "banana,50"
                + System.lineSeparator()
                + "apple,100";
    }
}
