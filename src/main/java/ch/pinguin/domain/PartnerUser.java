package ch.pinguin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PartnerUser.
 */
@Document(collection = "partner_user")
public class PartnerUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("user_name")
    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public PartnerUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<PartnerAccount> getAccounts() {
        return accounts;
    }

    public PartnerUser accounts(Set<PartnerAccount> partnerAccounts) {
        this.accounts = partnerAccounts;
        return this;
    }

    public PartnerUser addAccount(PartnerAccount partnerAccount) {
        this.accounts.add(partnerAccount);
        partnerAccount.setPartnerUser(this);
        return this;
    }

    public PartnerUser removeAccount(PartnerAccount partnerAccount) {
        this.accounts.remove(partnerAccount);
        partnerAccount.setPartnerUser(null);
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
        PartnerUser partnerUser = (PartnerUser) o;
        if (partnerUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partnerUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartnerUser{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
