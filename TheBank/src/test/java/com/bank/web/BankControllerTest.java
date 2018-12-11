package com.bank.web;

import static org.junit.Assert.*;
import org.junit.Test;



public class BankControllerTest {

	@Test
	public void testisCorrectLogin() {
		BankController t = new BankController();
		int output = t.isCorrectLogin("admin");
		assertEquals(1,output );
		}
	
	@Test
	public void testisCorrectLoginPassword() {
		BankController t = new BankController();
		int output = t.isCorrectLoginPassword("admin","admin");
		assertEquals(1,output );
		}
	

	
	
}