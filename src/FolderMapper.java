import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FolderMapper {

	private Map<String, Folder> map;
	private PrintToFile printer;
	
	public FolderMapper() {
		map = new HashMap<>();
		printer = new PrintToFile();
	}
	
	public void sort() {
		map.values().stream().forEach(Folder::sortComputers);
	}
	
	public void add(String directory, String name, Computer computer) {
		if(name.contains(".tmp")) return;		// ignore temporary files
		
		String genericPath = directory + name;
		
		if(!map.containsKey(genericPath))
			map.put(genericPath, new Folder(name, directory));
		
		map.get(genericPath).addComputer(computer);
	}
	
	public void printFoldersToFile(String writeDirectory) {
		System.out.print("Writing folders to file...");
		
		map.values().forEach(folder ->
			printer.write(
					writeDirectory,
					folder.getAsFileName() + ".txt",
					folder.getComputers().stream()
							.map(Computer::getName)
							.collect(Collectors.toList()))
		);
		
		System.out.println("done.");
	}
	
	public void printSummaryToFile(String writePath) {
		System.out.print("Writing summary statistics to file...");
		
		printer.write(writePath, map.values().stream().sorted()
				.map(folder -> String.format("%4d    %s", folder.numComputers(), folder.getAsFileName()))
				.collect(Collectors.toList()));
		
		System.out.println("done.");
	}
}
