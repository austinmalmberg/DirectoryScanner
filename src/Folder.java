import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder> {

	private String name;
	private String directoryPath;
	private boolean bit32;
	
	private List<Computer> computers;
	
	public Folder(String name, String directoryPath) {
		this.name = name;
		this.directoryPath = directoryPath;
		bit32 = directoryPath.contains("(x86)");
		
		this.computers = new ArrayList<>();
	}
	
	public void addComputer(Computer computer) {
		computers.add(computer);
	}
	
	public void sortComputers() {
		Collections.sort(computers);
	}
	
	public String getName() { return name; }
	public String getDirectoryPath() { return directoryPath; }
	public String getAsFileName() { return bit32 ? name + " (x86)" : name; }
	
	public List<Computer> getComputers() { return computers; }
	public int numComputers() { return computers.size(); }
	
	@Override
	public String toString() {
		return directoryPath + name;
	}

	@Override
	public int compareTo(Folder o) {
		return this.name.compareTo(o.name);
	}
}
