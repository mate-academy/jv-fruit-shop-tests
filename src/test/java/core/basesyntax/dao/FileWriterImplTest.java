package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String HEADER = "fruit,quantity";
    private static final String FOOTER = ",";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int COUNT_BANANA = 152;
    private static final int COUNT_APPLE = 90;
    private final StringBuilder stringBuilder = new StringBuilder();

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @BeforeEach
    void setUp() {
        stringBuilder.append(HEADER)
                .append(System.lineSeparator())
                .append(BANANA)
                .append(FOOTER)
                .append(COUNT_BANANA)
                .append(System.lineSeparator())
                .append(APPLE)
                .append(FOOTER)
                .append(COUNT_APPLE)
                .append(System.lineSeparator());
    }

    @Test
    void fileWrite_Ok() {
        StringBuilder lastString = new StringBuilder();
        String report = stringBuilder.toString();
        String fileName = "fileTest.txt";
        fileWriter.write(report, fileName);

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(fileName))) {
            String str;
            while ((str = br.readLine()) != null) {
                lastString.append(str)
                        .append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!lastString.isEmpty()) {
            lastString.setLength(lastString.length() - 2);
        }

        assertEquals(report, lastString.toString());
    }

    @Test
    void rights_ToWrite_NotOk() throws IOException {
        String report = stringBuilder.toString();
        String fileName = "fileNotExist.txt";
        File file = new File(fileName);
        file.setWritable(false);
        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(report, fileName);
        });
    }
}
