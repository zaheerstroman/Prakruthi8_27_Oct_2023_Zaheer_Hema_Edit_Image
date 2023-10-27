package com.prakruthi.ui.ui.profile.myaddress;

public class Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate {

    //    private int id;
    private String name;
    private String city;
    private String state;

    private String id;

    private String country;
    private String address;
    private String postal_code;
    private boolean is_default;

    public Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate(String name, String city, String state, String id, String country, String address, String postal_code, boolean is_default) {
//    public Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate(int id, String name, String city, String state, String country, String address, String postal_code, boolean is_default) {
//        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.id = id;
        this.country = country;
        this.address = address;
        this.postal_code = postal_code;
        this.is_default = is_default;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

//    public int getIs_default() {
//        return is_default;
//    }
//
//    public void setIs_default(int is_default) {
//        this.is_default = is_default;
//    }


    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

}
