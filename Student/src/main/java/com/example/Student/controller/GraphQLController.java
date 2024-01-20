package com.example.Student.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.execution.instrumentation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/graphql")
public class GraphQLController {

    private final GraphQL graphQL;

    @Autowired
    public GraphQLController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @PostMapping
    public Map<String, Object> graphql(@RequestBody Map<String, Object> request) {
        String query = (String) request.get("query");
        ExecutionResult executionResult = graphQL.execute(query);

        if (!executionResult.getErrors().isEmpty()) {
            List<String> errors = executionResult.getErrors().stream()
                    .map(error -> error.getMessage()) // Utilisation d'une lambda à la place de la méthode de référence
                    .collect(Collectors.toList());
            return Map.of("errors", errors);
        }

        return executionResult.toSpecification();
    }
}

