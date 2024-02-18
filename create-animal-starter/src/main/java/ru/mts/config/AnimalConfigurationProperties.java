package ru.mts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@ConfigurationProperties
public class AnimalConfigurationProperties {

    @Value("#{'${application.animal.cat.names}'.split(',')}")
    private List<String> catNames;
    @Value("#{'${application.animal.dog.names}'.split(',')}")
    private List<String> dogNames;
    @Value("#{'${application.animal.wolf.names}'.split(',')}")
    private List<String> wolfNames;
    @Value("#{'${application.animal.panda.names}'.split(',')}")
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