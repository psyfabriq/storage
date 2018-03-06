package pfq.storage.server.service;

import java.io.File;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import pfq.storage.server.model.FileMO;

public interface FileService {

    public String addFolder(Map<String, Object> map);
    public String itemCopy(Map<String, Object> map);
    public String itemDelete(Map<String, Object> map);
    public String itemUpload(Map<String, Object> map,MultipartFile file);
    public Optional<File> itemDownload(Map<String, Object> map);
    public String itemDownloadProp(Map<String, Object> map);
    public String itemCompress(Map<String, Object> map);
    public String itemMoveFile(Map<String, Object> map);
    public String itemRenameFile(Map<String, Object> map);
    public String getListDirectory(Map<String, Object> map);
    
}
