package org.ranestar.test;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptTest {

	public static void main(String[] args) {
		String pass = "hello";
		System.out.println("그냥 문자열 : "+pass);
		
		String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt(12));
		String hashPass1 = BCrypt.hashpw(pass, BCrypt.gensalt(12));
		System.out.println("암호화된 String : "+hashPass);
		System.out.println("암호화된 String1 : "+hashPass1);
		
		String pass2= "hello2";
		System.out.println(BCrypt.checkpw(pass, hashPass));
		System.out.println(BCrypt.checkpw(pass, hashPass1));
		System.out.println(BCrypt.checkpw(pass2, hashPass));
		System.out.println(hashPass == hashPass1);
	}
}
