package core.basesyntax;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {
    public static void writeDataToFile(String filePath, String data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new java.io.FileWriter(filePath, false))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }
    }

    public static void renewInputFile(String filePath) {
        FileWriter.writeDataToFile(filePath, "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50");
    }
}
