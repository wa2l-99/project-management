package com.pmt.project_management.task;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TaskHistoryResponse {

    private Integer taskId;
    private String taskName;
    private String projectName;
    private Integer lastModifiedById;
    private String lastModifiedByName;
    private LocalDateTime lastModifiedDate;
    private String modificationDescription;
}
