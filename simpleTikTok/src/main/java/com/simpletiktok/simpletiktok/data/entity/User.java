package com.simpletiktok.simpletiktok.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ricetea
 * @since 2024-06-06
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String password;

    private String avator;

    private String sex;

    private Integer age;

    private Integer follow;

    private Integer beliked;

    private Integer folllower;

    private String introduce;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }
    public Integer getBeliked() {
        return beliked;
    }

    public void setBeliked(Integer beliked) {
        this.beliked = beliked;
    }
    public Integer getFolllower() {
        return folllower;
    }

    public void setFolllower(Integer folllower) {
        this.folllower = folllower;
    }
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username=" + username +
            ", password=" + password +
            ", avator=" + avator +
            ", sex=" + sex +
            ", age=" + age +
            ", follow=" + follow +
            ", beliked=" + beliked +
            ", folllower=" + folllower +
            ", introduce=" + introduce +
        "}";
    }
}
