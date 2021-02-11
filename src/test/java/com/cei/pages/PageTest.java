package com.cei.pages;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PageTest {

	private Page page;
	@Before
	public void setUp() throws Exception {
		page = new Page();
	}

	@Test
	public void testNavigateURL() {
		System.out.println("Sample unit test");
	}

	@Test
	public void testTextPartialVerify() {
		assertEquals("testTextPartialVerify()======>", true, Page.textPartialVerify("test", "test"));
	}

}
