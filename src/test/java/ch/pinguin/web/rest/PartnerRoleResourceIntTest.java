package ch.pinguin.web.rest;

import ch.pinguin.PinguinApp;

import ch.pinguin.domain.PartnerRole;
import ch.pinguin.repository.PartnerRoleRepository;
import ch.pinguin.service.PartnerRoleService;
import ch.pinguin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static ch.pinguin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PartnerRoleResource REST controller.
 *
 * @see PartnerRoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PinguinApp.class)
public class PartnerRoleResourceIntTest {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    @Autowired
    private PartnerRoleRepository partnerRoleRepository;
    
    @Autowired
    private PartnerRoleService partnerRoleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPartnerRoleMockMvc;

    private PartnerRole partnerRole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerRoleResource partnerRoleResource = new PartnerRoleResource(partnerRoleService);
        this.restPartnerRoleMockMvc = MockMvcBuilders.standaloneSetup(partnerRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerRole createEntity() {
        PartnerRole partnerRole = new PartnerRole()
            .roleName(DEFAULT_ROLE_NAME);
        return partnerRole;
    }

    @Before
    public void initTest() {
        partnerRoleRepository.deleteAll();
        partnerRole = createEntity();
    }

    @Test
    public void createPartnerRole() throws Exception {
        int databaseSizeBeforeCreate = partnerRoleRepository.findAll().size();

        // Create the PartnerRole
        restPartnerRoleMockMvc.perform(post("/api/partner-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerRole)))
            .andExpect(status().isCreated());

        // Validate the PartnerRole in the database
        List<PartnerRole> partnerRoleList = partnerRoleRepository.findAll();
        assertThat(partnerRoleList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerRole testPartnerRole = partnerRoleList.get(partnerRoleList.size() - 1);
        assertThat(testPartnerRole.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
    }

    @Test
    public void createPartnerRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerRoleRepository.findAll().size();

        // Create the PartnerRole with an existing ID
        partnerRole.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerRoleMockMvc.perform(post("/api/partner-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerRole)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerRole in the database
        List<PartnerRole> partnerRoleList = partnerRoleRepository.findAll();
        assertThat(partnerRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPartnerRoles() throws Exception {
        // Initialize the database
        partnerRoleRepository.save(partnerRole);

        // Get all the partnerRoleList
        restPartnerRoleMockMvc.perform(get("/api/partner-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerRole.getId())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())));
    }
    
    @Test
    public void getPartnerRole() throws Exception {
        // Initialize the database
        partnerRoleRepository.save(partnerRole);

        // Get the partnerRole
        restPartnerRoleMockMvc.perform(get("/api/partner-roles/{id}", partnerRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerRole.getId()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()));
    }

    @Test
    public void getNonExistingPartnerRole() throws Exception {
        // Get the partnerRole
        restPartnerRoleMockMvc.perform(get("/api/partner-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePartnerRole() throws Exception {
        // Initialize the database
        partnerRoleService.save(partnerRole);

        int databaseSizeBeforeUpdate = partnerRoleRepository.findAll().size();

        // Update the partnerRole
        PartnerRole updatedPartnerRole = partnerRoleRepository.findById(partnerRole.getId()).get();
        updatedPartnerRole
            .roleName(UPDATED_ROLE_NAME);

        restPartnerRoleMockMvc.perform(put("/api/partner-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartnerRole)))
            .andExpect(status().isOk());

        // Validate the PartnerRole in the database
        List<PartnerRole> partnerRoleList = partnerRoleRepository.findAll();
        assertThat(partnerRoleList).hasSize(databaseSizeBeforeUpdate);
        PartnerRole testPartnerRole = partnerRoleList.get(partnerRoleList.size() - 1);
        assertThat(testPartnerRole.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
    }

    @Test
    public void updateNonExistingPartnerRole() throws Exception {
        int databaseSizeBeforeUpdate = partnerRoleRepository.findAll().size();

        // Create the PartnerRole

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerRoleMockMvc.perform(put("/api/partner-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerRole)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerRole in the database
        List<PartnerRole> partnerRoleList = partnerRoleRepository.findAll();
        assertThat(partnerRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePartnerRole() throws Exception {
        // Initialize the database
        partnerRoleService.save(partnerRole);

        int databaseSizeBeforeDelete = partnerRoleRepository.findAll().size();

        // Get the partnerRole
        restPartnerRoleMockMvc.perform(delete("/api/partner-roles/{id}", partnerRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerRole> partnerRoleList = partnerRoleRepository.findAll();
        assertThat(partnerRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerRole.class);
        PartnerRole partnerRole1 = new PartnerRole();
        partnerRole1.setId("id1");
        PartnerRole partnerRole2 = new PartnerRole();
        partnerRole2.setId(partnerRole1.getId());
        assertThat(partnerRole1).isEqualTo(partnerRole2);
        partnerRole2.setId("id2");
        assertThat(partnerRole1).isNotEqualTo(partnerRole2);
        partnerRole1.setId(null);
        assertThat(partnerRole1).isNotEqualTo(partnerRole2);
    }
}
