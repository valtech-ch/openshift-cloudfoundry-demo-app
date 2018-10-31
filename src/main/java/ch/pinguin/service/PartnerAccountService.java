package ch.pinguin.service;

import ch.pinguin.domain.PartnerAccount;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PartnerAccount.
 */
public interface PartnerAccountService {

    /**
     * Save a partnerAccount.
     *
     * @param partnerAccount the entity to save
     * @return the persisted entity
     */
    PartnerAccount save(PartnerAccount partnerAccount);

    /**
     * Get all the partnerAccounts.
     *
     * @return the list of entities
     */
    List<PartnerAccount> findAll();


    /**
     * Get the "id" partnerAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PartnerAccount> findOne(String id);

    /**
     * Delete the "id" partnerAccount.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
