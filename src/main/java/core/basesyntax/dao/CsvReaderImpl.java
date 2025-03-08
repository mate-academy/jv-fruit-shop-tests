package core.basesyntax.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReaderImpl implements CustomFileReader {

    @Override
    public String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        boolean isFileEmpty = true;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                isFileEmpty = false;
                sb.append(line).append(System.lineSeparator());
            }
            if (isFileEmpty) {
                throw new RuntimeException(filePath + " is empty");
            }
        } catch (IOException e) {
            throw new RuntimeException(filePath + " doesn't exist ", e);
        }
        return sb.toString();
    }
}
