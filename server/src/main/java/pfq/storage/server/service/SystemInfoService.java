package pfq.storage.server.service;

import javax.servlet.http.HttpServletRequest;

import pfq.storage.server.model.CurrentUser;

public interface SystemInfoService {
    public CurrentUser getCurrentUser(HttpServletRequest request);

}
