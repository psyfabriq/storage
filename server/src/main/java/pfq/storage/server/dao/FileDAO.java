package pfq.storage.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;

import pfq.storage.server.model.File;
import pfq.storage.server.model.Folder;

public interface FileDAO {
	

	
	boolean addFile(File file);
    boolean editFile(File file);
    boolean deleteFile(File file);
    boolean checkHasFile(String filepath);
    boolean checkHasFileByID(String ID);
    Optional<File>  findFile(String filepath);
  //  Optional<File>  findFileByID(String id);
  //  Optional<File>  findFileByQueryOne(String query);
  //  List<File> findFileByQueryList(String query);
  //  List<File> findFileByQueryList(Query query);
    List<File> getAllFiles(String folderpath);
    
	boolean addFolder(Folder folder);
    boolean editFolder(Folder folder);
    boolean deleteFolder(Folder folder);
    Optional<Folder>  findFolder(String folderpath);
    Optional<Folder>  findFolderID(String ID);
    boolean checkHasFolder(String folderpath);
    boolean checkHasFolderByID(String ID);
    List<Folder> getAllFolders(Folder parrent);
    List<Folder> getAllFolders();
}
