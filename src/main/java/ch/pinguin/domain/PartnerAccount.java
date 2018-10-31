package ch.pinguin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PartnerAccount.
 */
@Document(collection = "partner_account")
public class PartnerAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("account_name")
    private String accountName;

    @DBRef
    @Field("partner")
    @JsonIgnoreProperties("accounts")
    private Partner partner;

    @DBRef
    @Field("role")
    private Set<PartnerRole> roles = new HashSet<>();
    @DBRef
    @Field("partnerUser")
    @JsonIgnoreProperties("accounts")
    private PartnerUser partnerUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public PartnerAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Partner getPartner() {
        return partner;
    }

    public PartnerAccount partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Set<PartnerRole> getRoles() {
        return roles;
    }

    public PartnerAccount roles(Set<PartnerRole> partnerRoles) {
        this.roles = partnerRoles;
        return this;
    }

    public PartnerAccount addRole(PartnerRole partnerRole) {
        this.roles.add(partnerRole);
        partnerRole.setPartnerAccount(this);
        return this;
    }

    public PartnerAccount removeRole(PartnerRole partnerRole) {
        this.roles.remove(partnerRole);
        partnerRole.setPartnerAccount(null);
        return this;
    }

    public void setRoles(Set<PartnerRole> partnerRoles) {
        this.roles = partnerRoles;
    }

    public PartnerUser getPartnerUser() {
        return partnerUser;
    }

    public PartnerAccount partnerUser(PartnerUser partnerUser) {
        this.partnerUser = partnerUser;
        return this;
    }

    public void setPartnerUser(PartnerUser partnerUser) {
        this.partnerUser = partnerUser;
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
        PartnerAccount partnerAccount = (PartnerAccount) o;
        if (partnerAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partnerAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartnerAccount{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            "}";
    }
}
