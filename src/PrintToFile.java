import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class PrintToFile {

	public PrintToFile() { }
	
	public void write(String writeLocation, List<String> lines) {
		try {
			Files.write(Paths.get(writeLocation), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch(IOException io) {
			System.out.println("Unable to write " + writeLocation);
			io.printStackTrace();
		}
	}
	
	public void write(String path, String fileName, List<String> lines) {
		write(path+fileName, lines);
	}
}
