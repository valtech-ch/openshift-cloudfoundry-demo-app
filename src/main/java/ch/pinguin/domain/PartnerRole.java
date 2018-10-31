package ch.pinguin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PartnerRole.
 */
@Document(collection = "partner_role")
public class PartnerRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("role_name")
    private String roleName;

    @DBRef
    @Field("partnerAccount")
    @JsonIgnoreProperties("roles")
    private PartnerAccount partnerAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public PartnerRole roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public PartnerAccount getPartnerAccount() {
        return partnerAccount;
    }

    public PartnerRole partnerAccount(PartnerAccount partnerAccount) {
        this.partnerAccount = partnerAccount;
        return this;
    }

    public void setPartnerAccount(PartnerAccount partnerAccount) {
        this.partnerAccount = partnerAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PartnerRole partnerRole = (PartnerRole) o;
        if (partnerRole.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partnerRole.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartnerRole{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            "}";
    }
}
