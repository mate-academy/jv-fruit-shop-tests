package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void standardInput_OK() {
        List<String> expected = new ArrayList<>();

        try (BufferedReader reader1 = new BufferedReader(
                new java.io.FileReader("src/test/resources/finalTestReport.csv"))) {
            reader1.readLine();
            String line;
            while ((line = reader1.readLine()) != null) {
                expected.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Can't find file by path: " + "src/test/resources/finalTestReport.csv", e);
        }

        Main.main(null);

        List<String> actual = new ArrayList<>();

        try (BufferedReader reader2 = new BufferedReader(
                new java.io.FileReader("src/main/resources/finalReport.csv"))) {
            reader2.readLine();
            String line;
            while ((line = reader2.readLine()) != null) {
                actual.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Can't find file by path: " + "src/main/resources/finalReport.csv", e);
        }

        assertEquals(expected, actual);
    }
}
