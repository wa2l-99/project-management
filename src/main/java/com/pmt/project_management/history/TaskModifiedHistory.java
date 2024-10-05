package com.pmt.project_management.history;


import com.pmt.project_management.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.pmt.project_management.user.User;
import com.pmt.project_management.task.Task;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TaskModifiedHistory extends BaseEntity {

    //user relationship
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //Task relationship
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String description;
}
