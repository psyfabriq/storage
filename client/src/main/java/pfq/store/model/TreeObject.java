package pfq.store.model;

public class TreeObject {
	String name;
	String path;
	String parrent;
	String id;

    public TreeObject(String name, String path, String parrent, String id) {
        this.name    = name;
        this.path    = path;
        this.parrent = parrent;
        this.id      = id;
    }
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public String getParrent() {
		return parrent;
	}
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
	    return this.name;
	}
}
