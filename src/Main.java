import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static final String COMPUTERS_FILENAME = "DCT_Computers.csv";
	public static final String[] DIRECTORIES_TO_SEARCH =
			{
				"Program Files/",
				"Program Files (x86)/"
			};
	
	private static List<Computer> allComputers;
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		FolderMapper mapper = new FolderMapper();
		
		initAllComputers();
		
		printOfflineComputersToFile("OfflineComputers.txt", allComputers.stream().filter(computer -> !computer.isOnline()).map(Computer::getName).collect(Collectors.toList()));
		
		System.out.print("Beginning directory search...");
		Stream<Computer> onlineComputers = allComputers.stream().filter(Computer::isOnline);
		onlineComputers.forEach(computer -> {

			for(String dir : DIRECTORIES_TO_SEARCH) {				
				String path = computer.getCPath() + dir;
				File programFiles = new File(path);
				
				// ensure program files exists
				if(programFiles.exists()) {
					
					// get all directories in program files
					String[] directoryFolders = new File(path).list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return new File(dir, name).isDirectory();
						}
					});
					
					// add them to map
					Stream.of(directoryFolders).forEach(folderName -> mapper.add(dir, folderName, computer));
				}
			}
		});
		System.out.println("done.");
		
		mapper.sort();
		mapper.printFoldersToFile("directories/");
		mapper.printSummaryToFile("SummaryStatistics.txt");
		
		System.out.printf("Completed in %d seconds.%n", (System.currentTimeMillis() - start) / 1000);
	}

	private static void initAllComputers() {
		System.out.print("Preparing computers for search...");
		
		try {
			allComputers = Files.lines(Paths.get(COMPUTERS_FILENAME))
					.skip(1)
					.map(line -> line.split(","))
					.map(info -> new Computer(info[0], Arrays.copyOfRange(info, 1, info.length)))
					.sorted()
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("done.");
	}
	
	private static void printOfflineComputersToFile(String writeLocation, List<String> offline) {
		if (offline.isEmpty()) return;
		
		System.out.print(offline.size() + " computers offline or not found.  ");
		
		PrintToFile printer = new PrintToFile();
		printer.write(writeLocation, offline);
		
		System.out.println("OfflineComputers.txt created.");
	}
}
