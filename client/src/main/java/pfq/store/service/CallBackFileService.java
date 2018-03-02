package pfq.store.service;

import java.util.Optional;

import pfq.store.components.PreviewPane;

public interface CallBackFileService {
	void updateListElementsCallBack(Optional<PreviewPane> pp,boolean isRemove);
}
