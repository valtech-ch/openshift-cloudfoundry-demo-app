package ch.pinguin.service;

import ch.pinguin.domain.PartnerRole;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PartnerRole.
 */
public interface PartnerRoleService {

    /**
     * Save a partnerRole.
     *
     * @param partnerRole the entity to save
     * @return the persisted entity
     */
    PartnerRole save(PartnerRole partnerRole);

    /**
     * Get all the partnerRoles.
     *
     * @return the list of entities
     */
    List<PartnerRole> findAll();


    /**
     * Get the "id" partnerRole.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PartnerRole> findOne(String id);

    /**
     * Delete the "id" partnerRole.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
