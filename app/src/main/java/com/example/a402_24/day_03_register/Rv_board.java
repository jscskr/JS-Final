package com.example.a402_24.day_03_register;

public class Rv_board {
    private int rv_board_index;
    private String rv_board_post_date;

    // 해당 게시글 작성한 유저 아이디
    private String member_id;

    private String rv_board_title;
    private String rv_board_content;
    private String rv_board_location;
    private String rv_board_picture;
    private String member_profile;
    private int rv_board_comments;
    private int rv_board_recommend;
    private int rv_board_heart;
    private int rv_board_count;

    // 현재 로그인 한 유저가 해당 게시글에 좋아요 했는지 판별하기위해
    private String loginUser;

    public Rv_board(){

    }

    public Rv_board(int rv_board_index, String rv_board_post_date, String member_id, String rv_board_title, String rv_board_content, String rv_board_location, String rv_board_picture, String member_profile, int rv_board_comments, int rv_board_recommend, int rv_board_heart, int rv_board_count, String loginUser) {
        this.rv_board_index = rv_board_index;
        this.rv_board_post_date = rv_board_post_date;
        this.member_id = member_id;
        this.rv_board_title = rv_board_title;
        this.rv_board_content = rv_board_content;
        this.rv_board_location = rv_board_location;
        this.rv_board_picture = rv_board_picture;
        this.member_profile = member_profile;
        this.rv_board_comments = rv_board_comments;
        this.rv_board_recommend = rv_board_recommend;
        this.rv_board_heart = rv_board_heart;
        this.rv_board_count = rv_board_count;
        this.loginUser = loginUser;
    }

    public int getRv_board_index() {
        return rv_board_index;
    }

    public void setRv_board_index(int rv_board_index) {
        this.rv_board_index = rv_board_index;
    }

    public String getRv_board_post_date() {
        return rv_board_post_date;
    }

    public void setRv_board_post_date(String rv_board_post_date) {
        this.rv_board_post_date = rv_board_post_date;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
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

    public String getRv_board_location() {
        return rv_board_location;
    }

    public void setRv_board_location(String rv_board_location) {
        this.rv_board_location = rv_board_location;
    }

    public String getRv_board_picture() {
        return rv_board_picture;
    }

    public void setRv_board_picture(String rv_board_picture) {
        this.rv_board_picture = rv_board_picture;
    }

    public String getMember_profile() {
        return member_profile;
    }

    public void setMember_profile(String member_profile) {
        this.member_profile = member_profile;
    }

    public int getRv_board_comments() {
        return rv_board_comments;
    }

    public void setRv_board_comments(int rv_board_comments) {
        this.rv_board_comments = rv_board_comments;
    }

    public int getRv_board_recommend() {
        return rv_board_recommend;
    }

    public void setRv_board_recommend(int rv_board_recommend) {
        this.rv_board_recommend = rv_board_recommend;
    }

    public int getRv_board_heart() {
        return rv_board_heart;
    }

    public void setRv_board_heart(int rv_board_heart) {
        this.rv_board_heart = rv_board_heart;
    }

    public int getRv_board_count() {
        return rv_board_count;
    }

    public void setRv_board_count(int rv_board_count) {
        this.rv_board_count = rv_board_count;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }
}
