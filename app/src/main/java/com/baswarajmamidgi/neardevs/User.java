package com.baswarajmamidgi.neardevs;

/**
 * Created by baswarajmamidgi on 17/11/17.
 */

public class User {
    String id;
    String Name;
    String email;
    String mobile;
    String occupation;
    String domain;
    String address;

    public User(String name, String email, String mobile, String occupation, String domain, String address){
        this.Name=name;
        this.email=email;

        this.mobile=mobile;
        this.occupation=occupation;
        this.domain=domain;
        this.address=address;
    }

    public User(String id,String name, String address) {
        this.id=id;
        this.Name=name;
        this.address=address;
    }


    public String getEmail() {
        return email;
    }

    public String getUserName() {return Name;}

    public String getId(){return id; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
