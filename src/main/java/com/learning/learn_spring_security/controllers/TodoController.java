package com.learning.learn_spring_security.controllers;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private static final List<Todo> TODO_LIST = List.of(new Todo("chandra", "learn spring"),
			new Todo("chandra", "learn java"));

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/todos")
	public List<Todo> fetchTodos() {
		return TODO_LIST;
	}

	@GetMapping(path = "/users/{username}/todos")
	public Todo retrieveTodosForUser(@PathVariable String username) {
		System.out.println(username);
		return TODO_LIST.get(0);
	}

	@PostMapping("/users/{username}/todos")
	public void createTodosForUser(@PathVariable String username, @RequestBody Todo todo) {
		logger.info("create {} for {}", todo, username);
	}

}

record Todo(String username, String description) {

}
