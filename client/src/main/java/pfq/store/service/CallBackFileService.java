package pfq.store.service;

import java.util.Optional;

import pfq.store.components.PreviewPane;
import pfq.store.config.ContextStateApp;

public interface CallBackFileService {
	ContextStateApp getContext();
	void updateListElementsCallBack(Optional<PreviewPane> pp,boolean isRemove);
	void uploadedStatus(boolean result);
}
