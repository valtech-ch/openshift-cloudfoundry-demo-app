package ch.pinguin.service;

import ch.pinguin.domain.PartnerUser;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PartnerUser.
 */
public interface PartnerUserService {

    /**
     * Save a partnerUser.
     *
     * @param partnerUser the entity to save
     * @return the persisted entity
     */
    PartnerUser save(PartnerUser partnerUser);

    /**
     * Get all the partnerUsers.
     *
     * @return the list of entities
     */
    List<PartnerUser> findAll();


    /**
     * Get the "id" partnerUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PartnerUser> findOne(String id);

    /**
     * Delete the "id" partnerUser.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
