package com.keepllly.auth.domain.embedded;

import com.keeplly.utils.data.dto.embedded.Address;

public class BillingInformation {

    private String firstname;

    private String lastname;

    private Address address;

    public BillingInformation() {}

    public BillingInformation(String firstname, String lastname, Address address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public BillingInformation firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public BillingInformation lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BillingInformation address(Address address) {
        this.setAddress(address);
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BillingInformation other = (BillingInformation) obj;
        if (firstname == null) {
            if (other.firstname != null) return false;
        } else if (!firstname.equals(other.firstname)) return false;
        if (lastname == null) {
            if (other.lastname != null) return false;
        } else if (!lastname.equals(other.lastname)) return false;
        if (address == null) {
            if (other.address != null) return false;
        } else if (!address.equals(other.address)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "BillingInformation [firstname=" + firstname + ", lastname=" + lastname + ", address=" + address + "]";
    }
}
