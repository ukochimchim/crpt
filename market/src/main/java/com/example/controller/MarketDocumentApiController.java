package com.example.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Document;
import com.example.dto.Header;

@RestController
@RequestMapping(value = "/market")
public class MarketDocumentApiController {

	@Autowired
	private Validator validator;

	@PostMapping(value = "/saveDoc", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Document> saveDoc(@RequestBody Document newDocument) {
		Set<ConstraintViolation<Document>> errors = validator.validate(newDocument);
		if (errors.isEmpty()) {
			newDocument.setResponseHeader(new Header.Builder().setMessage("Document saved").build());
			return ResponseEntity.ok(newDocument);
		} else {
			newDocument.setResponseHeader(new Header.Builder().setValidationErrors(errors).build());
			return ResponseEntity.badRequest().body(newDocument);
		}
	}

}
