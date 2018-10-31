package ch.pinguin.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.pinguin.domain.PartnerRole;
import ch.pinguin.service.PartnerRoleService;
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
 * REST controller for managing PartnerRole.
 */
@RestController
@RequestMapping("/api")
public class PartnerRoleResource {

    private final Logger log = LoggerFactory.getLogger(PartnerRoleResource.class);

    private static final String ENTITY_NAME = "partnerRole";

    private PartnerRoleService partnerRoleService;

    public PartnerRoleResource(PartnerRoleService partnerRoleService) {
        this.partnerRoleService = partnerRoleService;
    }

    /**
     * POST  /partner-roles : Create a new partnerRole.
     *
     * @param partnerRole the partnerRole to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partnerRole, or with status 400 (Bad Request) if the partnerRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partner-roles")
    @Timed
    public ResponseEntity<PartnerRole> createPartnerRole(@RequestBody PartnerRole partnerRole) throws URISyntaxException {
        log.debug("REST request to save PartnerRole : {}", partnerRole);
        if (partnerRole.getId() != null) {
            throw new BadRequestAlertException("A new partnerRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartnerRole result = partnerRoleService.save(partnerRole);
        return ResponseEntity.created(new URI("/api/partner-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partner-roles : Updates an existing partnerRole.
     *
     * @param partnerRole the partnerRole to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partnerRole,
     * or with status 400 (Bad Request) if the partnerRole is not valid,
     * or with status 500 (Internal Server Error) if the partnerRole couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partner-roles")
    @Timed
    public ResponseEntity<PartnerRole> updatePartnerRole(@RequestBody PartnerRole partnerRole) throws URISyntaxException {
        log.debug("REST request to update PartnerRole : {}", partnerRole);
        if (partnerRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartnerRole result = partnerRoleService.save(partnerRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partnerRole.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partner-roles : get all the partnerRoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partnerRoles in body
     */
    @GetMapping("/partner-roles")
    @Timed
    public List<PartnerRole> getAllPartnerRoles() {
        log.debug("REST request to get all PartnerRoles");
        return partnerRoleService.findAll();
    }

    /**
     * GET  /partner-roles/:id : get the "id" partnerRole.
     *
     * @param id the id of the partnerRole to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partnerRole, or with status 404 (Not Found)
     */
    @GetMapping("/partner-roles/{id}")
    @Timed
    public ResponseEntity<PartnerRole> getPartnerRole(@PathVariable String id) {
        log.debug("REST request to get PartnerRole : {}", id);
        Optional<PartnerRole> partnerRole = partnerRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partnerRole);
    }

    /**
     * DELETE  /partner-roles/:id : delete the "id" partnerRole.
     *
     * @param id the id of the partnerRole to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partner-roles/{id}")
    @Timed
    public ResponseEntity<Void> deletePartnerRole(@PathVariable String id) {
        log.debug("REST request to delete PartnerRole : {}", id);
        partnerRoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
