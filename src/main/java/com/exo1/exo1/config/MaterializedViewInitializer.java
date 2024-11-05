package com.exo1.exo1.config;

import com.exo1.exo1.service.MaterializedViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MaterializedViewInitializer implements CommandLineRunner {

    private final MaterializedViewService materializedViewService;

    @Autowired
    public MaterializedViewInitializer(MaterializedViewService materializedViewService) {
        this.materializedViewService = materializedViewService;
    }

    @Override
    public void run(String... args) throws Exception {
        materializedViewService.createProjectTaskCountView();
    }

}
