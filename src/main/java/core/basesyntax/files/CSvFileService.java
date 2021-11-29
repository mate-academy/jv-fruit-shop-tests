package core.basesyntax.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSvFileService implements FileService {
    @Override
    public String readData(String filePath) {
        StringBuilder sourceDataInString = new StringBuilder();
        File fileToRead = new File(filePath);
        int data;
        try {
            if (!fileToRead.exists()) {
                throw new FileNotFoundException();
            }
            FileReader fileReader = new FileReader(fileToRead);
            data = fileReader.read();
            while (data != -1) {
                sourceDataInString.append((char) data);
                data = fileReader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException("No such file " + e);
        }
        return sourceDataInString.toString();
    }

    @Override
    public void writeData(String dataToWrite, String filePath) {
        File fileToWrite = new File(filePath);
        try {
            if (!fileToWrite.exists()) {
                throw new FileNotFoundException();
            }
            FileWriter writer = new FileWriter(fileToWrite);
            writer.append(dataToWrite);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException " + e);
        } catch (IOException e) {
            throw new RuntimeException("IOException " + e);
        }
    }
}
