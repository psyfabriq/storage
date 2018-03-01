package pfq.store.service;

import java.util.Optional;

import pfq.store.filters.PreviewPane;

public interface CallBackFileService {
	void updateListElementsCallBack(Optional<PreviewPane> pp,boolean isRemove);
}
