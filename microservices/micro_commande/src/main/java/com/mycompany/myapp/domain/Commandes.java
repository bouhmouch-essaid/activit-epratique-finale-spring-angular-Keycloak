package com.mycompany.myapp.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Commandes.
 */
@Document(collection = "commandes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commandes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("quantity")
    private Long quantity;

    @Field("customer_ref")
    private String customerRef;

    @Field("product_ref")
    private String productRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Commandes id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public Commandes quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCustomerRef() {
        return this.customerRef;
    }

    public Commandes customerRef(String customerRef) {
        this.setCustomerRef(customerRef);
        return this;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getProductRef() {
        return this.productRef;
    }

    public Commandes productRef(String productRef) {
        this.setProductRef(productRef);
        return this;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commandes)) {
            return false;
        }
        return getId() != null && getId().equals(((Commandes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commandes{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", customerRef='" + getCustomerRef() + "'" +
            ", productRef='" + getProductRef() + "'" +
            "}";
    }
}
