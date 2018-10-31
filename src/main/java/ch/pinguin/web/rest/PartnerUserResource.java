package ch.pinguin.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.pinguin.domain.PartnerUser;
import ch.pinguin.service.PartnerUserService;
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
 * REST controller for managing PartnerUser.
 */
@RestController
@RequestMapping("/api")
public class PartnerUserResource {

    private final Logger log = LoggerFactory.getLogger(PartnerUserResource.class);

    private static final String ENTITY_NAME = "partnerUser";

    private PartnerUserService partnerUserService;

    public PartnerUserResource(PartnerUserService partnerUserService) {
        this.partnerUserService = partnerUserService;
    }

    /**
     * POST  /partner-users : Create a new partnerUser.
     *
     * @param partnerUser the partnerUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partnerUser, or with status 400 (Bad Request) if the partnerUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partner-users")
    @Timed
    public ResponseEntity<PartnerUser> createPartnerUser(@RequestBody PartnerUser partnerUser) throws URISyntaxException {
        log.debug("REST request to save PartnerUser : {}", partnerUser);
        if (partnerUser.getId() != null) {
            throw new BadRequestAlertException("A new partnerUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerUser result = partnerUserService.save(partnerUser);
        return ResponseEntity.created(new URI("/api/partner-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partner-users : Updates an existing partnerUser.
     *
     * @param partnerUser the partnerUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partnerUser,
     * or with status 400 (Bad Request) if the partnerUser is not valid,
     * or with status 500 (Internal Server Error) if the partnerUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partner-users")
    @Timed
    public ResponseEntity<PartnerUser> updatePartnerUser(@RequestBody PartnerUser partnerUser) throws URISyntaxException {
        log.debug("REST request to update PartnerUser : {}", partnerUser);
        if (partnerUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerUser result = partnerUserService.save(partnerUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partnerUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partner-users : get all the partnerUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partnerUsers in body
     */
    @GetMapping("/partner-users")
    @Timed
    public List<PartnerUser> getAllPartnerUsers() {
        log.debug("REST request to get all PartnerUsers");
        return partnerUserService.findAll();
    }

    /**
     * GET  /partner-users/:id : get the "id" partnerUser.
     *
     * @param id the id of the partnerUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partnerUser, or with status 404 (Not Found)
     */
    @GetMapping("/partner-users/{id}")
    @Timed
    public ResponseEntity<PartnerUser> getPartnerUser(@PathVariable String id) {
        log.debug("REST request to get PartnerUser : {}", id);
        Optional<PartnerUser> partnerUser = partnerUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerUser);
    }

    /**
     * DELETE  /partner-users/:id : delete the "id" partnerUser.
     *
     * @param id the id of the partnerUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partner-users/{id}")
    @Timed
    public ResponseEntity<Void> deletePartnerUser(@PathVariable String id) {
        log.debug("REST request to delete PartnerUser : {}", id);
        partnerUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
