package ch.pinguin.service.impl;

import ch.pinguin.service.PartnerRoleService;
import ch.pinguin.domain.PartnerRole;
import ch.pinguin.repository.PartnerRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PartnerRole.
 */
@Service
public class PartnerRoleServiceImpl implements PartnerRoleService {

    private final Logger log = LoggerFactory.getLogger(PartnerRoleServiceImpl.class);

    private PartnerRoleRepository partnerRoleRepository;

    public PartnerRoleServiceImpl(PartnerRoleRepository partnerRoleRepository) {
        this.partnerRoleRepository = partnerRoleRepository;
    }

    /**
     * Save a partnerRole.
     *
     * @param partnerRole the entity to save
     * @return the persisted entity
     */
    @Override
    public PartnerRole save(PartnerRole partnerRole) {
        log.debug("Request to save PartnerRole : {}", partnerRole);
        return partnerRoleRepository.save(partnerRole);
    }

    /**
     * Get all the partnerRoles.
     *
     * @return the list of entities
     */
    @Override
    public List<PartnerRole> findAll() {
        log.debug("Request to get all PartnerRoles");
        return partnerRoleRepository.findAll();
    }


    /**
     * Get one partnerRole by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<PartnerRole> findOne(String id) {
        log.debug("Request to get PartnerRole : {}", id);
        return partnerRoleRepository.findById(id);
    }

    /**
     * Delete the partnerRole by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PartnerRole : {}", id);
        partnerRoleRepository.deleteById(id);
    }
}
