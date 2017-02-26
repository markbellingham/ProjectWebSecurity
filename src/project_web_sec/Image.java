package project_web_sec;

public class Image {
	
	private String fileName;
	private String fileSize;
	
	public Image(String fileName, String fileSize) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "Image [fileName=" + fileName + ", fileSize=" + fileSize + "]";
	}

	public Image() {
		super();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
		
}
