package com.example.a402_24.day_03_register;

import java.util.Date;

public class Member {

    private String member_id;
    private String member_password;
    private String member_gender;
    private String member_name;
    private String member_birthday;
    private String member_profile_pic;
    private String member_register_date;
    private String member_Zip_code;
    private String member_Street_name_address;
    private String member_Detailed_Address;

    public Member(String member_id, String member_password, String member_gender, String member_name, String member_birthday, String member_profile_pic, String member_register_date, String member_Zip_code, String member_Street_name_address, String member_Detailed_Address) {
        this.member_id = member_id;
        this.member_password = member_password;
        this.member_gender = member_gender;
        this.member_name = member_name;
        this.member_birthday = member_birthday;
        this.member_profile_pic = member_profile_pic;
        this.member_register_date = member_register_date;
        this.member_Zip_code = member_Zip_code;
        this.member_Street_name_address = member_Street_name_address;
        this.member_Detailed_Address = member_Detailed_Address;
    }

    public Member(){

    }

  

    public String getMember_register_date() {
        return member_register_date;
    }

    public void setMember_register_date(String member_register_date) {
        this.member_register_date = member_register_date;
    }

    public String getMember_profile_pic() {
        return member_profile_pic;
    }

    public String getMember_Zip_code() {
        return member_Zip_code;
    }

    public void setMember_Zip_code(String member_Zip_code) {
        this.member_Zip_code = member_Zip_code;
    }

    public String getMember_Street_name_address() {
        return member_Street_name_address;
    }

    public void setMember_Street_name_address(String member_Street_name_address) {
        this.member_Street_name_address = member_Street_name_address;
    }

    public String getMember_Detailed_Address() {
        return member_Detailed_Address;
    }

    public void setMember_Detailed_Address(String member_Detailed_Address) {
        this.member_Detailed_Address = member_Detailed_Address;
    }

    public void setMember_profile_pic(String member_profile_pic) {
        this.member_profile_pic = member_profile_pic;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_password() {
        return member_password;
    }

    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public String getMember_gender() {
        return member_gender;
    }

    public void setMember_gender(String member_gender) {
        this.member_gender = member_gender;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_birthday() {
        return member_birthday;
    }

    public void setMember_birthday(String member_birthday) {
        this.member_birthday = member_birthday;
    }
}
