package com.example.a402_24.day_03_register;

public class Report {
    private int rv_board_index;
    private int alert_index;
    private int message_id;
    private String rv_board_title;
    private String rv_board_content;
    private String rv_board_picture;
    private String alert_reason;
    private String message_title;
    private String message_content;
    private String message_picture;
    private String report_reason;
    private String report_member_id;
    private String reporter_member_id;
    private String report_date;

public Report(){

}

    public Report(int rv_board_index, String rv_board_title, String rv_board_content, String rv_board_picture, String report_reason, String report_member_id, String reporter_member_id, String report_date) {
        this.rv_board_index = rv_board_index;
        this.rv_board_title = rv_board_title;
        this.rv_board_content = rv_board_content;
        this.rv_board_picture = rv_board_picture;
        this.report_reason = report_reason;
        this.report_member_id = report_member_id;
        this.reporter_member_id = reporter_member_id;
        this.report_date = report_date;
    }
    public Report(int alert_index, String alert_reason, String report_reason, String report_member_id, String reporter_member_id, String report_date) {
        this.alert_index = alert_index;
        this.alert_reason = alert_reason;
        this.report_reason = report_reason;
        this.report_member_id = report_member_id;
        this.reporter_member_id = reporter_member_id;
        this.report_date = report_date;
    }


    public Report(int rv_board_index, int alert_index, int message_id, String rv_board_title, String rv_board_content, String rv_board_picture, String alert_reason, String message_title, String message_content, String message_picture, String report_reason, String report_member_id, String reporter_member_id, String report_date) {
        this.rv_board_index = rv_board_index;
        this.alert_index = alert_index;
        this.message_id = message_id;
        this.rv_board_title = rv_board_title;
        this.rv_board_content = rv_board_content;
        this.rv_board_picture = rv_board_picture;
        this.alert_reason = alert_reason;
        this.message_title = message_title;
        this.message_content = message_content;
        this.message_picture = message_picture;
        this.report_reason = report_reason;
        this.report_member_id = report_member_id;
        this.reporter_member_id = reporter_member_id;
        this.report_date = report_date;
    }

    public int getRv_board_index() {
        return rv_board_index;
    }
    public void setRv_board_index(int rv_board_index) {
        this.rv_board_index = rv_board_index;
    }
    public int getAlert_index() {
        return alert_index;
    }
    public void setAlert_index(int alert_index) {
        this.alert_index = alert_index;
    }
    public int getMessage_id() {
        return message_id;
    }
    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }
    public String getRv_board_title() {
        return rv_board_title;
    }
    public void setRv_board_title(String rv_board_title) {
        this.rv_board_title = rv_board_title;
    }
    public String getRv_board_content() {
        return rv_board_content;
    }
    public void setRv_board_content(String rv_board_content) {
        this.rv_board_content = rv_board_content;
    }
    public String getRv_board_picture() {
        return rv_board_picture;
    }
    public void setRv_board_picture(String rv_board_picture) {
        this.rv_board_picture = rv_board_picture;
    }
    public String getAlert_reason() {
        return alert_reason;
    }
    public void setAlert_reason(String alert_reason) {
        this.alert_reason = alert_reason;
    }
    public String getMessage_title() {
        return message_title;
    }
    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }
    public String getMessage_content() {
        return message_content;
    }
    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }
    public String getMessage_picture() {
        return message_picture;
    }
    public void setMessage_picture(String message_picture) {
        this.message_picture = message_picture;
    }
    public String getReport_reason() {
        return report_reason;
    }
    public void setReport_reason(String report_reason) {
        this.report_reason = report_reason;
    }
    public String getReport_member_id() {
        return report_member_id;
    }
    public void setReport_member_id(String report_member_id) {
        this.report_member_id = report_member_id;
    }
    public String getReporter_member_id() {
        return reporter_member_id;
    }
    public void setReporter_member_id(String reporter_member_id) {
        this.reporter_member_id = reporter_member_id;
    }
    public String getReport_date() {
        return report_date;
    }
    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }
}
