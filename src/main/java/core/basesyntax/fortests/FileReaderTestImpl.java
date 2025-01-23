package core.basesyntax.fortests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderTestImpl implements FileReaderTest {
    @Override
    public String readFile(String nameOfFile) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(nameOfFile))) {
            String value = reader.readLine();
            while (value != null) {
                stringBuilder.append(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
