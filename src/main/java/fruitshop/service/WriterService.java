package fruitshop.service;

public interface WriterService {
    boolean writeToFile(byte[] data, String fileName);
}
