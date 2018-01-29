package pfq.storage.server.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import pfq.storage.server.dao.FileDAO;
import pfq.storage.server.model.File;

@Repository
public class FileDaoImpl implements FileDAO{

	@Override
	public boolean addFile(File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editFile(File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFile(File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkHasFile(String filepath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkHasFileByID(String ID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<File> findFile(String filepath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<File> findFileByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<File> findFileByQueryOne(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> findFileByQueryList(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> findFileByQueryList(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> getAllFiles(String folderpath) {
		// TODO Auto-generated method stub
		return null;
	}

}
