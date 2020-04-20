package com.ghy.param;

import java.util.List;
import java.util.Map;

public class MyInput {

    private int id;
    private String name;
    private Map<String , Object> job;
    private List<Map<String, Object>> skill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getJob() {
        return job;
    }

    public void setJob(Map<String, Object> job) {
        this.job = job;
    }

    public List<Map<String, Object>> getSkill() {
        return skill;
    }

    public void setSkill(List<Map<String, Object>> skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", job:" + job +
                ", skill:" + skill +
                '}';
    }
}
