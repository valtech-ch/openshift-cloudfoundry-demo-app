package ch.pinguin.web.rest;

import ch.pinguin.PinguinApp;

import ch.pinguin.domain.PartnerUser;
import ch.pinguin.repository.PartnerUserRepository;
import ch.pinguin.service.PartnerUserService;
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
 * Test class for the PartnerUserResource REST controller.
 *
 * @see PartnerUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PinguinApp.class)
public class PartnerUserResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    @Autowired
    private PartnerUserRepository partnerUserRepository;
    
    @Autowired
    private PartnerUserService partnerUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPartnerUserMockMvc;

    private PartnerUser partnerUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerUserResource partnerUserResource = new PartnerUserResource(partnerUserService);
        this.restPartnerUserMockMvc = MockMvcBuilders.standaloneSetup(partnerUserResource)
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
    public static PartnerUser createEntity() {
        PartnerUser partnerUser = new PartnerUser()
            .userName(DEFAULT_USER_NAME);
        return partnerUser;
    }

    @Before
    public void initTest() {
        partnerUserRepository.deleteAll();
        partnerUser = createEntity();
    }

    @Test
    public void createPartnerUser() throws Exception {
        int databaseSizeBeforeCreate = partnerUserRepository.findAll().size();

        // Create the PartnerUser
        restPartnerUserMockMvc.perform(post("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerUser)))
            .andExpect(status().isCreated());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerUser testPartnerUser = partnerUserList.get(partnerUserList.size() - 1);
        assertThat(testPartnerUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    public void createPartnerUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerUserRepository.findAll().size();

        // Create the PartnerUser with an existing ID
        partnerUser.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerUserMockMvc.perform(post("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerUser)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPartnerUsers() throws Exception {
        // Initialize the database
        partnerUserRepository.save(partnerUser);

        // Get all the partnerUserList
        restPartnerUserMockMvc.perform(get("/api/partner-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerUser.getId())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())));
    }
    
    @Test
    public void getPartnerUser() throws Exception {
        // Initialize the database
        partnerUserRepository.save(partnerUser);

        // Get the partnerUser
        restPartnerUserMockMvc.perform(get("/api/partner-users/{id}", partnerUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerUser.getId()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()));
    }

    @Test
    public void getNonExistingPartnerUser() throws Exception {
        // Get the partnerUser
        restPartnerUserMockMvc.perform(get("/api/partner-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePartnerUser() throws Exception {
        // Initialize the database
        partnerUserService.save(partnerUser);

        int databaseSizeBeforeUpdate = partnerUserRepository.findAll().size();

        // Update the partnerUser
        PartnerUser updatedPartnerUser = partnerUserRepository.findById(partnerUser.getId()).get();
        updatedPartnerUser
            .userName(UPDATED_USER_NAME);

        restPartnerUserMockMvc.perform(put("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartnerUser)))
            .andExpect(status().isOk());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeUpdate);
        PartnerUser testPartnerUser = partnerUserList.get(partnerUserList.size() - 1);
        assertThat(testPartnerUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    public void updateNonExistingPartnerUser() throws Exception {
        int databaseSizeBeforeUpdate = partnerUserRepository.findAll().size();

        // Create the PartnerUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerUserMockMvc.perform(put("/api/partner-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerUser)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerUser in the database
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePartnerUser() throws Exception {
        // Initialize the database
        partnerUserService.save(partnerUser);

        int databaseSizeBeforeDelete = partnerUserRepository.findAll().size();

        // Get the partnerUser
        restPartnerUserMockMvc.perform(delete("/api/partner-users/{id}", partnerUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerUser> partnerUserList = partnerUserRepository.findAll();
        assertThat(partnerUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerUser.class);
        PartnerUser partnerUser1 = new PartnerUser();
        partnerUser1.setId("id1");
        PartnerUser partnerUser2 = new PartnerUser();
        partnerUser2.setId(partnerUser1.getId());
        assertThat(partnerUser1).isEqualTo(partnerUser2);
        partnerUser2.setId("id2");
        assertThat(partnerUser1).isNotEqualTo(partnerUser2);
        partnerUser1.setId(null);
        assertThat(partnerUser1).isNotEqualTo(partnerUser2);
    }
}
