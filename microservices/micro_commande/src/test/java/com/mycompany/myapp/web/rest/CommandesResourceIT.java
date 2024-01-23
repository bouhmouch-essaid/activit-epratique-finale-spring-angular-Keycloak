package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Commandes;
import com.mycompany.myapp.repository.CommandesRepository;
import com.mycompany.myapp.service.dto.CommandesDTO;
import com.mycompany.myapp.service.mapper.CommandesMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link CommandesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommandesResourceIT {

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final String DEFAULT_CUSTOMER_REF = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_REF = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_REF = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/commandes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CommandesRepository commandesRepository;

    @Autowired
    private CommandesMapper commandesMapper;

    @Autowired
    private MockMvc restCommandesMockMvc;

    private Commandes commandes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commandes createEntity() {
        Commandes commandes = new Commandes().quantity(DEFAULT_QUANTITY).customerRef(DEFAULT_CUSTOMER_REF).productRef(DEFAULT_PRODUCT_REF);
        return commandes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commandes createUpdatedEntity() {
        Commandes commandes = new Commandes().quantity(UPDATED_QUANTITY).customerRef(UPDATED_CUSTOMER_REF).productRef(UPDATED_PRODUCT_REF);
        return commandes;
    }

    @BeforeEach
    public void initTest() {
        commandesRepository.deleteAll();
        commandes = createEntity();
    }

    @Test
    void createCommandes() throws Exception {
        int databaseSizeBeforeCreate = commandesRepository.findAll().size();
        // Create the Commandes
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);
        restCommandesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeCreate + 1);
        Commandes testCommandes = commandesList.get(commandesList.size() - 1);
        assertThat(testCommandes.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCommandes.getCustomerRef()).isEqualTo(DEFAULT_CUSTOMER_REF);
        assertThat(testCommandes.getProductRef()).isEqualTo(DEFAULT_PRODUCT_REF);
    }

    @Test
    void createCommandesWithExistingId() throws Exception {
        // Create the Commandes with an existing ID
        commandes.setId("existing_id");
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);

        int databaseSizeBeforeCreate = commandesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCommandes() throws Exception {
        // Initialize the database
        commandesRepository.save(commandes);

        // Get all the commandesList
        restCommandesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandes.getId())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].customerRef").value(hasItem(DEFAULT_CUSTOMER_REF)))
            .andExpect(jsonPath("$.[*].productRef").value(hasItem(DEFAULT_PRODUCT_REF)));
    }

    @Test
    void getCommandes() throws Exception {
        // Initialize the database
        commandesRepository.save(commandes);

        // Get the commandes
        restCommandesMockMvc
            .perform(get(ENTITY_API_URL_ID, commandes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandes.getId()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.customerRef").value(DEFAULT_CUSTOMER_REF))
            .andExpect(jsonPath("$.productRef").value(DEFAULT_PRODUCT_REF));
    }

    @Test
    void getNonExistingCommandes() throws Exception {
        // Get the commandes
        restCommandesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCommandes() throws Exception {
        // Initialize the database
        commandesRepository.save(commandes);

        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();

        // Update the commandes
        Commandes updatedCommandes = commandesRepository.findById(commandes.getId()).orElseThrow();
        updatedCommandes.quantity(UPDATED_QUANTITY).customerRef(UPDATED_CUSTOMER_REF).productRef(UPDATED_PRODUCT_REF);
        CommandesDTO commandesDTO = commandesMapper.toDto(updatedCommandes);

        restCommandesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commandesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
        Commandes testCommandes = commandesList.get(commandesList.size() - 1);
        assertThat(testCommandes.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCommandes.getCustomerRef()).isEqualTo(UPDATED_CUSTOMER_REF);
        assertThat(testCommandes.getProductRef()).isEqualTo(UPDATED_PRODUCT_REF);
    }

    @Test
    void putNonExistingCommandes() throws Exception {
        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();
        commandes.setId(UUID.randomUUID().toString());

        // Create the Commandes
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commandesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCommandes() throws Exception {
        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();
        commandes.setId(UUID.randomUUID().toString());

        // Create the Commandes
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCommandes() throws Exception {
        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();
        commandes.setId(UUID.randomUUID().toString());

        // Create the Commandes
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCommandesWithPatch() throws Exception {
        // Initialize the database
        commandesRepository.save(commandes);

        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();

        // Update the commandes using partial update
        Commandes partialUpdatedCommandes = new Commandes();
        partialUpdatedCommandes.setId(commandes.getId());

        partialUpdatedCommandes.quantity(UPDATED_QUANTITY).customerRef(UPDATED_CUSTOMER_REF).productRef(UPDATED_PRODUCT_REF);

        restCommandesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandes))
            )
            .andExpect(status().isOk());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
        Commandes testCommandes = commandesList.get(commandesList.size() - 1);
        assertThat(testCommandes.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCommandes.getCustomerRef()).isEqualTo(UPDATED_CUSTOMER_REF);
        assertThat(testCommandes.getProductRef()).isEqualTo(UPDATED_PRODUCT_REF);
    }

    @Test
    void fullUpdateCommandesWithPatch() throws Exception {
        // Initialize the database
        commandesRepository.save(commandes);

        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();

        // Update the commandes using partial update
        Commandes partialUpdatedCommandes = new Commandes();
        partialUpdatedCommandes.setId(commandes.getId());

        partialUpdatedCommandes.quantity(UPDATED_QUANTITY).customerRef(UPDATED_CUSTOMER_REF).productRef(UPDATED_PRODUCT_REF);

        restCommandesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommandes.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommandes))
            )
            .andExpect(status().isOk());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
        Commandes testCommandes = commandesList.get(commandesList.size() - 1);
        assertThat(testCommandes.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCommandes.getCustomerRef()).isEqualTo(UPDATED_CUSTOMER_REF);
        assertThat(testCommandes.getProductRef()).isEqualTo(UPDATED_PRODUCT_REF);
    }

    @Test
    void patchNonExistingCommandes() throws Exception {
        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();
        commandes.setId(UUID.randomUUID().toString());

        // Create the Commandes
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commandesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCommandes() throws Exception {
        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();
        commandes.setId(UUID.randomUUID().toString());

        // Create the Commandes
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCommandes() throws Exception {
        int databaseSizeBeforeUpdate = commandesRepository.findAll().size();
        commandes.setId(UUID.randomUUID().toString());

        // Create the Commandes
        CommandesDTO commandesDTO = commandesMapper.toDto(commandes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommandesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commandesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commandes in the database
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCommandes() throws Exception {
        // Initialize the database
        commandesRepository.save(commandes);

        int databaseSizeBeforeDelete = commandesRepository.findAll().size();

        // Delete the commandes
        restCommandesMockMvc
            .perform(delete(ENTITY_API_URL_ID, commandes.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commandes> commandesList = commandesRepository.findAll();
        assertThat(commandesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
