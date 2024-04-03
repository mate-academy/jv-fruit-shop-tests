package core.basesyntax.service.impl;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Writer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    public static final Writer WRITER = new WriterImpl();
    public static final String FINAL_FILE_PASS = "src/test/resources/FinalFileName.csv";

    @Test
    void write_correctData_ok() {
        String dataToWrite = "fruit,quantity\nbanana,10\napple,20";
        List<String> correctDataFromFile = Arrays.asList("fruit,quantity", "banana,10", "apple,20");
        WRITER.write(dataToWrite, FINAL_FILE_PASS);
        List<String> actual = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FINAL_FILE_PASS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actual.add(line);
            }
        } catch (IOException e) {
            System.out.printf("Can't read from file %s", FINAL_FILE_PASS);
        }
        if (correctDataFromFile.size() != actual.size()) {
            fail();
        }
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(correctDataFromFile.get(i),actual.get(i));
        }

    }

    @Test
    void write_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> WRITER.write(null, FINAL_FILE_PASS));
    }

    @Test
    void write_incorrectFileName_notOk() {
        String dataToWrite = "fruit,quantity\nbanana,10\napple,20";
        assertThrows(RuntimeException.class,
                () -> WRITER.write(dataToWrite, "src/test/resources/incorrect\nincorrect"));
    }

    @AfterEach
    void tearDown() {
        File fileToDelete = new File(FINAL_FILE_PASS);
        fileToDelete.delete();
    }
}
