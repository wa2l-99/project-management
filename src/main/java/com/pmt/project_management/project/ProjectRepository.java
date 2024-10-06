package com.pmt.project_management.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    boolean existsByName(String name);

    List<Project> findByOwnerId(Integer ownerId);
}
