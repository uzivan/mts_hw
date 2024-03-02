package ru.mts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Configuration
@ConfigurationProperties("animal")
public class AnimalConfigurationProperties {

    private List<String> catNames;
    private List<String> dogNames;
    private List<String> wolfNames;
    private List<String> pandaNames;

    public List<String> getCatNames() {
        return Collections.unmodifiableList(catNames);
    }

    public List<String> getDogNames() {
        return Collections.unmodifiableList(dogNames);
    }

    public List<String> getWolfNames() {
        return Collections.unmodifiableList(wolfNames);
    }

    public List<String> getPandaNames() {
        return Collections.unmodifiableList(pandaNames);
    }

    public void setCatNames(List<String> catNames) {
        this.catNames = catNames;
    }

    public void setDogNames(List<String> dogNames) {
        this.dogNames = dogNames;
    }

    public void setWolfNames(List<String> wolfNames) {
        this.wolfNames = wolfNames;
    }

    public void setPandaNames(List<String> pandaNames) {
        this.pandaNames = pandaNames;
    }

}