package ch.pinguin.service.impl;

import ch.pinguin.service.PartnerUserService;
import ch.pinguin.domain.PartnerUser;
import ch.pinguin.repository.PartnerUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PartnerUser.
 */
@Service
public class PartnerUserServiceImpl implements PartnerUserService {

    private final Logger log = LoggerFactory.getLogger(PartnerUserServiceImpl.class);

    private PartnerUserRepository partnerUserRepository;

    public PartnerUserServiceImpl(PartnerUserRepository partnerUserRepository) {
        this.partnerUserRepository = partnerUserRepository;
    }

    /**
     * Save a partnerUser.
     *
     * @param partnerUser the entity to save
     * @return the persisted entity
     */
    @Override
    public PartnerUser save(PartnerUser partnerUser) {
        log.debug("Request to save PartnerUser : {}", partnerUser);
        return partnerUserRepository.save(partnerUser);
    }

    /**
     * Get all the partnerUsers.
     *
     * @return the list of entities
     */
    @Override
    public List<PartnerUser> findAll() {
        log.debug("Request to get all PartnerUsers");
        return partnerUserRepository.findAll();
    }


    /**
     * Get one partnerUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<PartnerUser> findOne(String id) {
        log.debug("Request to get PartnerUser : {}", id);
        return partnerUserRepository.findById(id);
    }

    /**
     * Delete the partnerUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PartnerUser : {}", id);
        partnerUserRepository.deleteById(id);
    }
}
