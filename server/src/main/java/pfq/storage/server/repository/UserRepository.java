package pfq.storage.server.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import pfq.storage.server.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findOneByEmail(String email);

	Optional<User> findOneByEmail(User tmp);

}