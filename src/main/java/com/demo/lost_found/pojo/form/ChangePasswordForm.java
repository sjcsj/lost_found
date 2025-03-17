package com.demo.lost_found.pojo.form;

import lombok.Data;

@Data
public class ChangePasswordForm {

    private Integer id;
    private String username;
    private String password;
    private String newPassword;
}
