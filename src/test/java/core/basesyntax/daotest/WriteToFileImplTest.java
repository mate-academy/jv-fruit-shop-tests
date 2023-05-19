package core.basesyntax.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.WriteToFile;
import core.basesyntax.dao.daoimpl.WriteToFileImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

public class WriteToFileImplTest {
    public static final String EMPTY_FILE_PATH = "src/test/resources/";
    public static final String FILE_PATH_OK = "src/test/resources/output.csv";
    public static final String WRONG_FILE_PATH = "src/test/resouRRRces/blabla";
    private WriteToFile writer;

    @Test
    void writeToFile_Ok() {
        writer = new WriteToFileImpl();
        writer.writeToFile(report(), FILE_PATH_OK);

        File file = new File(FILE_PATH_OK);
        String actual;
        try {
            actual = String.join(System.lineSeparator(),
                    Files.readAllLines(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file. " + e);
        }
        assertEquals(report(), actual);
    }

    @Test
    void writeToFile_emptyReport_Ok() {
        writer = new WriteToFileImpl();
        writer.writeToFile("fruit,quantity", FILE_PATH_OK);
        File file = new File(FILE_PATH_OK);
        String actual;
        try {
            actual = String.join(System.lineSeparator(),
                    Files.readAllLines(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Can`t write to file. " + e);
        }
        assertEquals("fruit,quantity", actual);
    }

    @Test
    void writeToFile_NotOk() {
        writer = new WriteToFileImpl();
        writer.writeToFile(report(), FILE_PATH_OK);
        String actual = "";
        assertNotEquals(report(), actual);
    }

    @Test
    void writeToFile_WrongFilePath_NotOk() {
        writer = new WriteToFileImpl();
        assertThrows(RuntimeException.class, () -> writer.writeToFile(report(), WRONG_FILE_PATH));
    }

    @Test
    void writeToFIle_nullFilePath_NotOk() {
        writer = new WriteToFileImpl();
        assertThrows(NullPointerException.class, () -> writer.writeToFile(report(), null));
    }

    @Test
    void writeToFIle_emptyFilePath_NotOk() {
        writer = new WriteToFileImpl();
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
