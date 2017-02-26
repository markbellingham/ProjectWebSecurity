package project_web_sec;

public class IPAddress {
	
	private String ipAddress;
	private String date;
	
	public IPAddress(String ipAddress, String date) {
		super();
		this.ipAddress = ipAddress;
		this.date = date;
	}

	@Override
	public String toString() {
		return "IPAddress [ipAddress=" + ipAddress + ", date=" + date + "]";
	}

	public IPAddress() {
		super();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
