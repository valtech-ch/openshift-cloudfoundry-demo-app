package ch.pinguin.repository;

import ch.pinguin.domain.PartnerAccount;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the PartnerAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerAccountRepository extends MongoRepository<PartnerAccount, String> {

}
