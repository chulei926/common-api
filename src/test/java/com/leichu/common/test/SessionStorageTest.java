package com.leichu.common.test;

import com.leichu.common.web.SessionStorage;
import org.junit.Test;

public class SessionStorageTest {

	@Test
	public void t1(){
		SessionStorage.getInstance().setAttribute("sid","name" ,"kitty");
		System.out.println();
	}

}
