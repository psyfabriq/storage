package pfq.storage.server.service;

import javax.servlet.http.HttpServletRequest;

import pfq.storage.server.model.CurrentUser;

public interface SystemInfoService {
    public CurrentUser getCurrentUser(HttpServletRequest request);
    public String getCurrentUserFolder();
    public String getLocalPath();
    public String getCurrentUserID();
    public String getWorkFolder();
    public boolean access();

}
