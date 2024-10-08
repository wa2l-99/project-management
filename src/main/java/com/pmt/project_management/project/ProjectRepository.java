package com.pmt.project_management.project;

import com.pmt.project_management.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    boolean existsByName(String name);

    List<Project> findByMembersContaining(User user);
}
