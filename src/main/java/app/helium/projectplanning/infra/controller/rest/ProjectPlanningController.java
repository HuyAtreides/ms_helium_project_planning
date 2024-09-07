package app.helium.projectplanning.infra.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectPlanningController {

    @GetMapping
    public String helloWorld() {
        return "Hello Project Planning";
    }
}
