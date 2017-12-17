package com.platform.config;

import com.platform.util.DateUtil;
import com.platform.util.FeatureConfigUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wang.na on 2017/3/6.
 */
@ConfigurationProperties
@Validated
public class FeatureConfig {
    @Valid
    private Map<String, Item> features;

    public FeatureConfig() {
        features = new HashMap<>();
    }

    @PostConstruct
    public void init() throws ParseException {
        for (Map.Entry<String, Item> entry : this.features.entrySet()) {
            String[] values = entry.getValue().date.split("-");
            entry.getValue().setName(entry.getKey());
            entry.getValue().setTime(DateUtil.getCalendar(entry.getValue().date, "yyyy-MM-dd"));
            entry.getValue().setIsActive(!isAfterToday(entry.getValue().time));
        }

        FeatureConfigUtil.setFeatureConfig(this);
    }

    public Map<String, Item> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Item> features) {
        this.features = features;
    }

    public List<Item> getSortedItems() {
        return this.features.values().stream().sorted((Comparator.comparing(Item::getTime).reversed())).collect(Collectors.toList());
    }

    public boolean isActive(String key) {
        if (features.containsKey(key)) {
            return features.get(key).getIsActive();
        } else {
            return false;
        }
    }

    public void setActive(String key, boolean isActive) {
        if (features.containsKey(key)) {
            features.get(key).setIsActive(isActive);
        }
    }

    public void changeResult(String key) {
        if (features.containsKey(key)) {
            boolean isActive = features.get(key).getIsActive();
            features.get(key).setIsActive(!isActive);
        }
    }

    //是否是过去
    private boolean isAfterToday(Calendar date) {
        return getDateValue(date) > getDateValue(Calendar.getInstance());
    }

    private int getDateValue(Calendar date) {
        return date.get(Calendar.YEAR) * 10000 + date.get(Calendar.MONTH) * 100 + date.get(Calendar.DATE);
    }

    public static class Item {
        private String name;
        @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
        private String date;
        private Calendar time;
        @NotEmpty
        private String description;
        private boolean isActive;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Calendar getTime() {
            return time;
        }

        public void setTime(Calendar time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }
    }
}