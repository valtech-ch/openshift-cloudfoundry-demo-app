package ch.pinguin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ch.pinguin.domain.enumeration.PartnerType;

/**
 * A Partner.
 */
@Document(collection = "partner")
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("partner_name")
    private String partnerName;

    @NotNull
    @Field("partner_type")
    private PartnerType partnerType;

    @DBRef
    @Field("account")
    private Set<PartnerAccount> accounts = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public Partner partnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public PartnerType getPartnerType() {
        return partnerType;
    }

    public Partner partnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
        return this;
    }

    public void setPartnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
    }

    public Set<PartnerAccount> getAccounts() {
        return accounts;
    }

    public Partner accounts(Set<PartnerAccount> partnerAccounts) {
        this.accounts = partnerAccounts;
        return this;
    }

    public Partner addAccount(PartnerAccount partnerAccount) {
        this.accounts.add(partnerAccount);
        partnerAccount.setPartner(this);
        return this;
    }

    public Partner removeAccount(PartnerAccount partnerAccount) {
        this.accounts.remove(partnerAccount);
        partnerAccount.setPartner(null);
        return this;
    }

    public void setAccounts(Set<PartnerAccount> partnerAccounts) {
        this.accounts = partnerAccounts;
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
        Partner partner = (Partner) o;
        if (partner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + getId() +
            ", partnerName='" + getPartnerName() + "'" +
            ", partnerType='" + getPartnerType() + "'" +
            "}";
    }
}
