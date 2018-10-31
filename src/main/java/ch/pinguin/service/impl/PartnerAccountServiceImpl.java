package ch.pinguin.service.impl;

import ch.pinguin.service.PartnerAccountService;
import ch.pinguin.domain.PartnerAccount;
import ch.pinguin.repository.PartnerAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PartnerAccount.
 */
@Service
public class PartnerAccountServiceImpl implements PartnerAccountService {

    private final Logger log = LoggerFactory.getLogger(PartnerAccountServiceImpl.class);

    private PartnerAccountRepository partnerAccountRepository;

    public PartnerAccountServiceImpl(PartnerAccountRepository partnerAccountRepository) {
        this.partnerAccountRepository = partnerAccountRepository;
    }

    /**
     * Save a partnerAccount.
     *
     * @param partnerAccount the entity to save
     * @return the persisted entity
     */
    @Override
    public PartnerAccount save(PartnerAccount partnerAccount) {
        log.debug("Request to save PartnerAccount : {}", partnerAccount);
        return partnerAccountRepository.save(partnerAccount);
    }

    /**
     * Get all the partnerAccounts.
     *
     * @return the list of entities
     */
    @Override
    public List<PartnerAccount> findAll() {
        log.debug("Request to get all PartnerAccounts");
        return partnerAccountRepository.findAll();
    }


    /**
     * Get one partnerAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<PartnerAccount> findOne(String id) {
        log.debug("Request to get PartnerAccount : {}", id);
        return partnerAccountRepository.findById(id);
    }

    /**
     * Delete the partnerAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PartnerAccount : {}", id);
        partnerAccountRepository.deleteById(id);
    }
}
