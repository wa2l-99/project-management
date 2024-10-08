package com.pmt.project_management.project;


import com.pmt.project_management.task.Task;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {

    private Integer id;
    private String name;
    private String description;
    private LocalDate startDate;
    private String owner;
    private Set<ProjectMemberResponse> members;
    private List<Task> tasks;
}
