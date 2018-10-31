package ch.pinguin.web.rest;

import ch.pinguin.PinguinApp;

import ch.pinguin.domain.PartnerAccount;
import ch.pinguin.repository.PartnerAccountRepository;
import ch.pinguin.service.PartnerAccountService;
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
 * Test class for the PartnerAccountResource REST controller.
 *
 * @see PartnerAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PinguinApp.class)
public class PartnerAccountResourceIntTest {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    @Autowired
    private PartnerAccountRepository partnerAccountRepository;
    
    @Autowired
    private PartnerAccountService partnerAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPartnerAccountMockMvc;

    private PartnerAccount partnerAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerAccountResource partnerAccountResource = new PartnerAccountResource(partnerAccountService);
        this.restPartnerAccountMockMvc = MockMvcBuilders.standaloneSetup(partnerAccountResource)
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
    public static PartnerAccount createEntity() {
        PartnerAccount partnerAccount = new PartnerAccount()
            .accountName(DEFAULT_ACCOUNT_NAME);
        return partnerAccount;
    }

    @Before
    public void initTest() {
        partnerAccountRepository.deleteAll();
        partnerAccount = createEntity();
    }

    @Test
    public void createPartnerAccount() throws Exception {
        int databaseSizeBeforeCreate = partnerAccountRepository.findAll().size();

        // Create the PartnerAccount
        restPartnerAccountMockMvc.perform(post("/api/partner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerAccount)))
            .andExpect(status().isCreated());

        // Validate the PartnerAccount in the database
        List<PartnerAccount> partnerAccountList = partnerAccountRepository.findAll();
        assertThat(partnerAccountList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerAccount testPartnerAccount = partnerAccountList.get(partnerAccountList.size() - 1);
        assertThat(testPartnerAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
    }

    @Test
    public void createPartnerAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerAccountRepository.findAll().size();

        // Create the PartnerAccount with an existing ID
        partnerAccount.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerAccountMockMvc.perform(post("/api/partner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerAccount)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerAccount in the database
        List<PartnerAccount> partnerAccountList = partnerAccountRepository.findAll();
        assertThat(partnerAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPartnerAccounts() throws Exception {
        // Initialize the database
        partnerAccountRepository.save(partnerAccount);

        // Get all the partnerAccountList
        restPartnerAccountMockMvc.perform(get("/api/partner-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerAccount.getId())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())));
    }
    
    @Test
    public void getPartnerAccount() throws Exception {
        // Initialize the database
        partnerAccountRepository.save(partnerAccount);

        // Get the partnerAccount
        restPartnerAccountMockMvc.perform(get("/api/partner-accounts/{id}", partnerAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerAccount.getId()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()));
    }

    @Test
    public void getNonExistingPartnerAccount() throws Exception {
        // Get the partnerAccount
        restPartnerAccountMockMvc.perform(get("/api/partner-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePartnerAccount() throws Exception {
        // Initialize the database
        partnerAccountService.save(partnerAccount);

        int databaseSizeBeforeUpdate = partnerAccountRepository.findAll().size();

        // Update the partnerAccount
        PartnerAccount updatedPartnerAccount = partnerAccountRepository.findById(partnerAccount.getId()).get();
        updatedPartnerAccount
            .accountName(UPDATED_ACCOUNT_NAME);

        restPartnerAccountMockMvc.perform(put("/api/partner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartnerAccount)))
            .andExpect(status().isOk());

        // Validate the PartnerAccount in the database
        List<PartnerAccount> partnerAccountList = partnerAccountRepository.findAll();
        assertThat(partnerAccountList).hasSize(databaseSizeBeforeUpdate);
        PartnerAccount testPartnerAccount = partnerAccountList.get(partnerAccountList.size() - 1);
        assertThat(testPartnerAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
    }

    @Test
    public void updateNonExistingPartnerAccount() throws Exception {
        int databaseSizeBeforeUpdate = partnerAccountRepository.findAll().size();

        // Create the PartnerAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerAccountMockMvc.perform(put("/api/partner-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerAccount)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerAccount in the database
        List<PartnerAccount> partnerAccountList = partnerAccountRepository.findAll();
        assertThat(partnerAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePartnerAccount() throws Exception {
        // Initialize the database
        partnerAccountService.save(partnerAccount);

        int databaseSizeBeforeDelete = partnerAccountRepository.findAll().size();

        // Get the partnerAccount
        restPartnerAccountMockMvc.perform(delete("/api/partner-accounts/{id}", partnerAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PartnerAccount> partnerAccountList = partnerAccountRepository.findAll();
        assertThat(partnerAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerAccount.class);
        PartnerAccount partnerAccount1 = new PartnerAccount();
        partnerAccount1.setId("id1");
        PartnerAccount partnerAccount2 = new PartnerAccount();
        partnerAccount2.setId(partnerAccount1.getId());
        assertThat(partnerAccount1).isEqualTo(partnerAccount2);
        partnerAccount2.setId("id2");
        assertThat(partnerAccount1).isNotEqualTo(partnerAccount2);
        partnerAccount1.setId(null);
        assertThat(partnerAccount1).isNotEqualTo(partnerAccount2);
    }
}
