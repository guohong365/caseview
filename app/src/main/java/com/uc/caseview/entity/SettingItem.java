package com.uc.caseview.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by guoho on 2017/6/12.
 */
@Entity(nameInDb = "T_SETTINGS")
public class SettingItem {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Unique
    private String name;
    private String value;
    @Generated(hash = 1771667620)
    public SettingItem(Long id, @NotNull String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
    @Generated(hash = 909877566)
    public SettingItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
