package com.polgg.polgg.repository.portfolio;

import com.polgg.polgg.domain.portfolio.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
