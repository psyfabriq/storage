package pfq.storage.server.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import pfq.storage.server.dao.FileDAO;
import pfq.storage.server.model.FileMO;
import pfq.storage.server.model.Folder;
import pfq.storage.server.model.User;
import pfq.storage.server.service.SystemInfoService;
import pfq.storage.server.service.impl.QBuilder;
import pfq.storage.server.utils.PFQloger;

@Repository
public class FileDaoImpl implements FileDAO{
	
	private Logger logger = PFQloger.getLogger(FileDAO.class, Level.ALL);

	
	private FileMO tmp_file;
	private Folder tmp_folder;
	ObjectMapper mapper = new ObjectMapper();


	@Autowired
	MongoOperations mongoOperation;
	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	QBuilder qb;

	
	@Override
	public boolean addFile(FileMO file) {
		boolean result = !checkHasFile(file.getPath());
		if(result){
			mongoOperation.save(file);
			return true;
		}else {return false;}
	}

	@Override
	public boolean editFile(FileMO file) {
		
	    Query query = new Query();
		query.addCriteria(Criteria.where("path").is(file.getPath()));
		query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
		
		return mongoOperation.updateFirst(query,file.getUpdate(),FileMO.class).isUpdateOfExisting();
	}

	@Override
	public boolean deleteFile(FileMO file) {
		boolean result = checkHasFile(file.getPath());
		if(result){
			mongoOperation.remove(file);
			return true;
		}else {return false;}
	}

	@Override
	public boolean checkHasFile(String filepath) {
		return findFile(filepath).isPresent();
	}

	@Override
	public boolean checkHasFileByID(String ID) {
		return findFileID(ID).isPresent();
	}

	@Override
	public Optional<FileMO> findFile(String filepath) {
	    Query query = new Query();
		query.addCriteria(Criteria.where("path").is(filepath));
		query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
		
		tmp_file = mongoOperation.findOne(query, FileMO.class);
		
	  return Optional.ofNullable(tmp_file);
	}
	
	@Override
	public Optional<FileMO> findFileID(String ID) {
		    Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(ID));
			query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
			
			tmp_file = mongoOperation.findOne(query, FileMO.class);
			
		return Optional.ofNullable(tmp_file);
	}

	@Override
	public List<FileMO> getAllFiles(Folder parrent) {
		Query query = new Query();
		query.addCriteria(Criteria.where("parrent").is(parrent));
		query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
		
		return  mongoOperation.find(query, FileMO.class);
	}
	
	@Override
	public List<FileMO> getAllFiles() {
		Query query = new Query();
		query.addCriteria(Criteria.where("parrent").is(null));
		query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
		
		return  mongoOperation.find(query, FileMO.class);
	}

	@Override
	public Optional<Folder> findFolder(String filepath) {
	    Query query = new Query();
		query.addCriteria(Criteria.where("path").is(filepath));
		query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
		
		tmp_folder = mongoOperation.findOne(query, Folder.class);
		
	return Optional.ofNullable(tmp_folder);
	}
	
	@Override
	public Optional<Folder> findFolderID(String ID) {
		    Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(ID));
			query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
			
			tmp_folder = mongoOperation.findOne(query, Folder.class);
			
		return Optional.ofNullable(tmp_folder);
	}

	@Override
	public boolean addFolder(Folder folder) {
		boolean result = !checkHasFolder(folder.getPath());
		if(result){
			mongoOperation.save(folder);
			return true;
		}else {return false;}

	}

	@Override
	public boolean editFolder(Folder folder) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFolder(Folder folder) {
		boolean result = checkHasFolder(folder.getPath());
		if(result){
			mongoOperation.remove(folder);
			return true;
		}else {return false;}
	}

	@Override
	public boolean checkHasFolder(String folderpath) {
		return findFolder(folderpath).isPresent();
	}

	@Override
	public boolean checkHasFolderByID(String ID) {
		return findFolderID(ID).isPresent();
	}

	@Override
	public List<Folder> getAllFolders(Folder parrent) {

			Query query = new Query();
			query.addCriteria(Criteria.where("parrent").is(parrent));
			query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
			
			return  mongoOperation.find(query, Folder.class);

	}
	
	@Override
	public List<Folder> getAllFolders() {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("parrent").is(null));
		query.addCriteria(Criteria.where("userid").is(systemInfoService.getCurrentUserID()));
		
		return  mongoOperation.find(query, Folder.class);

	}

}
