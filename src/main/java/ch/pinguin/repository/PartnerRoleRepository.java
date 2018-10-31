package ch.pinguin.repository;

import ch.pinguin.domain.PartnerRole;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the PartnerRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerRoleRepository extends MongoRepository<PartnerRole, String> {

}
