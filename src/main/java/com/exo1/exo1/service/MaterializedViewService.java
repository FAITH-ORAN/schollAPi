package com.exo1.exo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaterializedViewService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MaterializedViewService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProjectTaskCountView() {
        String sql = "CREATE MATERIALIZED VIEW IF NOT EXISTS project_task_count AS " +
                "SELECT p.id AS project_id, p.name AS project_name, COUNT(t.id) AS task_count " +
                "FROM projects p " +
                "LEFT JOIN tasks t ON t.project_id = p.id " +
                "GROUP BY p.id, p.name";
        jdbcTemplate.execute(sql);
    }

    // refresh
    public void refreshProjectTaskCountView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW project_task_count");
    }
}
