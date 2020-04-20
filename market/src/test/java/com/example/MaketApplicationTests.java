package com.example;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dto.Document;
import com.example.dto.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MaketApplication.class})
class MaketApplicationTests {
	
	private static final String SAVE_DOC_PATH = "/market/saveDoc";
	private static final String SUCCESS_ANSWER = "Document saved";
	
	@Autowired
	private TestRestTemplate restTemplate;

	
	private Product createProduct(String code,String name) {
		Product product = new Product();
		product.setCode(code);
		product.setName(name);
		return product;
	}
	
	private Document createDocument() {
		Document document = new Document();
		document.setCustomer("123534251");
		document.setSeller("648563524");
		List<Product> products = new ArrayList<Product>();
		products.add(createProduct("2364758363546", "milk"));
		products.add(createProduct("3656352437590", "water"));
		document.setProducts(products);
		return document;
	}
	
    @Test
    public void shoudReturnBadRequest() {
    	Document document = createDocument();
    	document.setCustomer("1233");
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Document> httpEntity = new HttpEntity<Document>(document, headers);
    	ResponseEntity<Document> response =  restTemplate.postForEntity(SAVE_DOC_PATH, httpEntity, Document.class);
    	
    	Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
	
    
    @Test
    public void shoudReturnSuccess() {
    	Document document = createDocument();
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<Document> httpEntity = new HttpEntity<Document>(document, headers);
    	ResponseEntity<Document> response =  restTemplate.postForEntity(SAVE_DOC_PATH, httpEntity, Document.class);
    	
    	Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    	response.getBody().getResponseHeader().getMessage();
    	Assert.assertNotNull(response.getBody());
    	Assert.assertNotNull(response.getBody().getResponseHeader());
    	Assert.assertEquals(SUCCESS_ANSWER, response.getBody().getResponseHeader().getMessage());
    }
    

}
