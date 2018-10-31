package ch.pinguin.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.pinguin.domain.PartnerAccount;
import ch.pinguin.service.PartnerAccountService;
import ch.pinguin.web.rest.errors.BadRequestAlertException;
import ch.pinguin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PartnerAccount.
 */
@RestController
@RequestMapping("/api")
public class PartnerAccountResource {

    private final Logger log = LoggerFactory.getLogger(PartnerAccountResource.class);

    private static final String ENTITY_NAME = "partnerAccount";

    private PartnerAccountService partnerAccountService;

    public PartnerAccountResource(PartnerAccountService partnerAccountService) {
        this.partnerAccountService = partnerAccountService;
    }

    /**
     * POST  /partner-accounts : Create a new partnerAccount.
     *
     * @param partnerAccount the partnerAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partnerAccount, or with status 400 (Bad Request) if the partnerAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partner-accounts")
    @Timed
    public ResponseEntity<PartnerAccount> createPartnerAccount(@RequestBody PartnerAccount partnerAccount) throws URISyntaxException {
        log.debug("REST request to save PartnerAccount : {}", partnerAccount);
        if (partnerAccount.getId() != null) {
            throw new BadRequestAlertException("A new partnerAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerAccount result = partnerAccountService.save(partnerAccount);
        return ResponseEntity.created(new URI("/api/partner-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partner-accounts : Updates an existing partnerAccount.
     *
     * @param partnerAccount the partnerAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partnerAccount,
     * or with status 400 (Bad Request) if the partnerAccount is not valid,
     * or with status 500 (Internal Server Error) if the partnerAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partner-accounts")
    @Timed
    public ResponseEntity<PartnerAccount> updatePartnerAccount(@RequestBody PartnerAccount partnerAccount) throws URISyntaxException {
        log.debug("REST request to update PartnerAccount : {}", partnerAccount);
        if (partnerAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerAccount result = partnerAccountService.save(partnerAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partnerAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partner-accounts : get all the partnerAccounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partnerAccounts in body
     */
    @GetMapping("/partner-accounts")
    @Timed
    public List<PartnerAccount> getAllPartnerAccounts() {
        log.debug("REST request to get all PartnerAccounts");
        return partnerAccountService.findAll();
    }

    /**
     * GET  /partner-accounts/:id : get the "id" partnerAccount.
     *
     * @param id the id of the partnerAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partnerAccount, or with status 404 (Not Found)
     */
    @GetMapping("/partner-accounts/{id}")
    @Timed
    public ResponseEntity<PartnerAccount> getPartnerAccount(@PathVariable String id) {
        log.debug("REST request to get PartnerAccount : {}", id);
        Optional<PartnerAccount> partnerAccount = partnerAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerAccount);
    }

    /**
     * DELETE  /partner-accounts/:id : delete the "id" partnerAccount.
     *
     * @param id the id of the partnerAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partner-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deletePartnerAccount(@PathVariable String id) {
        log.debug("REST request to delete PartnerAccount : {}", id);
        partnerAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
