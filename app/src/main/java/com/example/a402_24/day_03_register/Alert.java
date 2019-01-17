package com.example.a402_24.day_03_register;

public class Alert {

    private int alert_index;
    private int rv_board_index;
    private String member_id;
    private String member_profile;
    private String alert_reason;
    private String alert_date;

    public Alert(){

    }

    public Alert(int alert_index, int rv_board_index, String member_id, String member_profile, String alert_reason, String alert_date) {
        this.alert_index = alert_index;
        this.rv_board_index = rv_board_index;
        this.member_id = member_id;
        this.member_profile = member_profile;
        this.alert_reason = alert_reason;
        this.alert_date = alert_date;
    }

    public String getMember_profile() {
        return member_profile;
    }

    public void setMember_profile(String member_profile) {
        this.member_profile = member_profile;
    }

    public int getAlert_index() {
        return alert_index;
    }

    public void setAlert_index(int alert_index) {
        this.alert_index = alert_index;
    }

    public int getRv_board_index() {
        return rv_board_index;
    }

    public void setRv_board_index(int rv_board_index) {
        this.rv_board_index = rv_board_index;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getAlert_reason() {
        return alert_reason;
    }

    public void setAlert_reason(String alert_reason) {
        this.alert_reason = alert_reason;
    }

    public String getAlert_date() {
        return alert_date;
    }

    public void setAlert_date(String alert_date) {
        this.alert_date = alert_date;
    }
}
