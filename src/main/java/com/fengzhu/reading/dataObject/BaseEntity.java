package com.fengzhu.reading.dataObject;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.Instant;

@Data
@MappedSuperclass
public class BaseEntity {

    private long createTime;

    private long updateTime;

    @PrePersist
    public void onCreate() {
        if (this.createTime == 0L) {
            this.createTime = Instant.now().toEpochMilli();
            this.updateTime = Instant.now().toEpochMilli();
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updateTime = Instant.now().toEpochMilli();
    }

}
