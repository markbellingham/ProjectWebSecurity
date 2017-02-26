package project_web_sec;

public class Page {
	
	private String pageName;
	private String pageHash;
	
	public Page(String pageName, String pageHash) {
		super();
		this.pageName = pageName;
		this.pageHash = pageHash;
	}

	@Override
	public String toString() {
		return "Page [pageName=" + pageName + ", pageHash=" + pageHash + "]";
	}

	public Page() {
		super();
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageHash() {
		return pageHash;
	}

	public void setPageHash(String pageHash) {
		this.pageHash = pageHash;
	}
	
		
}
