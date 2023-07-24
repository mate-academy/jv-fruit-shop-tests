import java.nio.file.Path;
import java.nio.file.Paths;
public class PathFile {
    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Відносний шлях до поточної директорії: " + currentDirectory);
    }
}
