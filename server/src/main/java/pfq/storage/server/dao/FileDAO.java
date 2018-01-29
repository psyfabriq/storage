package pfq.storage.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;

import pfq.storage.server.model.File;

public interface FileDAO {
	boolean addFile(File file);
    boolean editFile(File file);
    boolean deleteFile(File file);
    boolean checkHasFile(String filepath);
    boolean checkHasFileByID(String ID);
    Optional<File>  findFile(String filepath);
    Optional<File>  findFileByID(String id);
    Optional<File>  findFileByQueryOne(String query);
    List<File> findFileByQueryList(String query);
    List<File> findFileByQueryList(Query query);
    List<File> getAllFiles(String folderpath);
}
