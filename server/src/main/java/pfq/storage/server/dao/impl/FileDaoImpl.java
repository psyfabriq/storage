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
import org.springframework.stereotype.Repository;

import pfq.storage.server.dao.FileDAO;
import pfq.storage.server.model.File;
import pfq.storage.server.model.Folder;
import pfq.storage.server.model.User;
import pfq.storage.server.service.impl.QBuilder;
import pfq.storage.server.utils.PFQloger;

@Repository
public class FileDaoImpl implements FileDAO{
	
	private Logger logger = PFQloger.getLogger(FileDAO.class, Level.ALL);

	
	private File tmp_file;
	private Folder tmp_folder;

	@Autowired
	MongoOperations mongoOperation;
	
	@Autowired
	QBuilder qb;

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

	@Override
	public Optional<Folder> findFolder(String filepath) {
		String query = qb.getBuilder()
				  .append("{$or:[{path:")
				  .append(QBuilder.LowerCase(filepath))
				  .append("}]}").build();
		BasicQuery querycargo = new BasicQuery(query);
		tmp_folder = mongoOperation.findOne(querycargo, Folder.class);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkHasFolder(String folderpath) {
		return findFolder(folderpath).isPresent();
	}

	@Override
	public boolean checkHasFolderByID(String ID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Folder> getAllFolders(Folder parrent) {

			Query query = new Query();
			query.addCriteria(Criteria.where("parrent").is(parrent));
			
			return  mongoOperation.find(query, Folder.class);

	}
	
	@Override
	public List<Folder> getAllFolders() {
		
		String querys = qb.getBuilder().append("{parrent:null}").build();
		BasicQuery query = new BasicQuery(querys);

		return  mongoOperation.find(query, Folder.class);

	}

}
