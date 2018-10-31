package ch.pinguin.repository;

import ch.pinguin.domain.PartnerUser;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the PartnerUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerUserRepository extends MongoRepository<PartnerUser, String> {

}
