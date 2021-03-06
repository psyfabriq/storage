package pfq.storage.server.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Repository;

import pfq.storage.server.dao.UserDAO;
import pfq.storage.server.model.Role;
import pfq.storage.server.model.User;
import pfq.storage.server.service.impl.QBuilder;
import pfq.storage.server.utils.AppUtil;
import pfq.storage.server.utils.PFQloger;

@Repository
public class UserDaoImpl implements UserDAO {

	private Logger logger = PFQloger.getLogger(UserDAO.class, Level.ALL);

	private User tmp;

	@Autowired
	MongoOperations mongoOperation;
	
	@Autowired
	QBuilder qb;


	@Override
	public boolean addUser(User user) {
		logger.debug("addUser");
		boolean result = !checkHasUserByID(user.getId()) ? !checkHasUser(user.getFoldercode()) : false;
		if (result) {
			mongoOperation.save(user);
		} else {
			AppUtil.setError("User has on server !!!");
		}
		return result;
	}

	@Override
	public boolean editUser(User user) {
		logger.debug("editUser");
		mongoOperation.save(user);
		return true;
	}

	@Override
	public boolean deleteUser(User user) {
		logger.debug("deleteUser");
		boolean result = false;
		
		String query = qb.getBuilder()
		  .append("{$or:[{_id:'")
		  .append(user.getId())
		  .append("'},{login:")
		  .append(QBuilder.LowerCase(user.getLogin()))
		  .append("}]}").build();

		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		if (tmp != null) {
			result = true;
			mongoOperation.remove(tmp);
		}

		return result;
	}

	@Override
	public boolean checkHasUser(String login) {
		logger.debug("checkHasUser");
        Boolean result = false;
		String query = qb.getBuilder()
				  .append("{$or:[{login:")
				  .append(QBuilder.LowerCase(login))
				  .append("},{email:")
				  .append(QBuilder.LowerCase(login))
				  .append("}]}").build();
		
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		result = tmp!=null?true:false; 
	    return result;
	}
	
	@Override
	public boolean checkHasUser(String login, String email) {
		logger.debug("checkHasUser");
        Boolean result = false;
		String query = qb.getBuilder()
				  .append("{$or:[{login:")
				  .append(QBuilder.LowerCase(login))
				  .append("},{email:")
				  .append(QBuilder.LowerCase(email))
				  .append("}]}").build();
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		result = tmp!=null?true:false; 
	    return result;
	}
	
	@Override
	public boolean checkHasUser(String login, String email, String neid) {
		logger.debug("checkHasUser");
        Boolean result = false;
		String query = qb.getBuilder()
				  .append("{$or:[{login:")
				  .append(QBuilder.LowerCase(login))
				  .append("},{email:")
				  .append(QBuilder.LowerCase(email))
				  .append("}],_id:")
				  .append(QBuilder.Besides(neid))
				  .append("}")
				  .build();
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		result = tmp!=null?true:false; 
	    return result;
	} 

	@Override
	public boolean checkHasUserByID(String ID) {
		logger.debug("checkHasUserByID");
        Boolean result = false;
        String query = qb.getBuilder()
				  .append("{$or:[{_id:'")
				  .append(ID)
				  .append("'}]}").build();
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		
		result = tmp!=null?true:false; 
	    return result;
	}

	@Override
	public Optional<User> findUser(String login) {
		logger.debug("findUser");
		String query = qb.getBuilder()
				  .append("{$or:[{login:")
				  .append(QBuilder.LowerCase(login))
				  .append("},{email:")
				  .append(QBuilder.LowerCase(login))
				  .append("}]}").build();
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		return Optional.ofNullable(tmp);
	}
	
	@Override
	public Optional<User> findUser(String login, String email) {
		logger.debug("findUser");
		String query = qb.getBuilder()
				  .append("{$or:[{login:")
				  .append(QBuilder.LowerCase(login))
				  .append("},{email:")
				  .append(QBuilder.LowerCase(email))
				  .append("}]}").build();
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		return Optional.ofNullable(tmp);
	}
	
	

	@Override
	public Optional<User> findUserByID(String id) {
		logger.debug("findUserByID");
        String query = qb.getBuilder()
				  .append("{$or:[{_id:'")
				  .append(QBuilder.LowerCase(id))
				  .append("'}]}").build();
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		return Optional.ofNullable(tmp);
	}

	/*
	@Override
	public Optional<User> findUserByQueryOne(String query) {
		logger.debug("findUserByQueryOne");
		BasicQuery querycargo = new BasicQuery(query);
		tmp = mongoOperation.findOne(querycargo, User.class);
		return Optional.of(tmp);
	}

	@Override
	public List<User> findUserByQueryList(String query) {
		logger.debug("findUserByQueryList");
		BasicQuery querycargo = new BasicQuery(query);
		List<User> ltmp = mongoOperation.find(querycargo, User.class);
		return ltmp;
	}

	@Override
	public List<User> findUserByQueryList(Query query) {
		logger.debug("findUserByQueryList");
		List<User> ltmp = mongoOperation.find(query, User.class);
		return ltmp;
	}
*/
	@Override
	public List<Role> getListRole(String login) {
		logger.debug("getListRole");
		return findUser(login).get().getUserRoles();
	}

	@Override
	public List<Role> getListRoleByID(String id) {
		logger.debug("getListRoleByID");
		return findUserByID(id).get().getUserRoles();
	}

	@Override
	public List<User> getAllUser() {
		logger.debug("getAllUser");
		return mongoOperation.findAll(User.class);
	}

	/*
	@Override
	public Optional<User> findUserByEmail(String email) {
		
		BasicQuery querycargo = new BasicQuery("{$or:[{email:'" + email + "'}]}");
		tmp = mongoOperation.findOne(querycargo, User.class);
		return Optional.of(tmp);
	}
	*/

}
