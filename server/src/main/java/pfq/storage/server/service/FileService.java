package pfq.storage.server.service;

import java.util.Map;

public interface FileService {

    public String addFolder(Map<String, Object> map);
    public String itemCopy(Map<String, Object> map);
    public String itemDelete(Map<String, Object> map);
    public String itemUpload(Map<String, Object> map);
    public String itemDownload(Map<String, Object> map);
    public String itemCompress(Map<String, Object> map);
    public String itemMoveFile(Map<String, Object> map);
    public String itemRenameFile(Map<String, Object> map);
    public String getListDirectory(Map<String, Object> map);
    
}
