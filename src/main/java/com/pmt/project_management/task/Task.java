package com.pmt.project_management.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmt.project_management.common.BaseEntity;
import com.pmt.project_management.history.TaskModifiedHistory;
import com.pmt.project_management.project.Project;
import com.pmt.project_management.user.User;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Task extends BaseEntity {


    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private EPriority priority;

    @Column(nullable = false)
    private EStatus status;

    // Relation ManyToOne pour associer une tâche à un projet
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    // Assignation d'une tâche à un membre spécifique
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;  // Membre à qui la tâche est assignée

    @OneToMany(mappedBy = "task")
    private List<TaskModifiedHistory> histories;
}
