import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Computer implements Comparable<Computer> {
	
	public static final int TIMEOUT_DURATION = 6;

	private String name;
	private String[] otherInfo;
	
	private boolean online;
	
	public Computer(String name, String[] otherInfo) {
		this.name = name;
		this.otherInfo = otherInfo;
		
		online = online();
	}
	
	private boolean online() {
		try {
			return InetAddress.getByName(name).isReachable(TIMEOUT_DURATION);
		} catch (UnknownHostException e) {
//			System.out.println(name + " offline or not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String getName() { return name; }
	public String getCPath() { return String.format("//%s/c$/", name); }
	
	public String[] getOtherInfo() { return otherInfo; }
	
	public boolean isOnline() { return online; }
	
	@Override
	public String toString() { return name; }

	@Override
	public int compareTo(Computer o) {
		return this.name.compareTo(o.name);
	}
}
