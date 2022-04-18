package com.learning.blogSecurity;

import com.learning.blogSecurity.model.Users;
import com.learning.blogSecurity.repository.UsersRepository;
import com.learning.blogSecurity.util.EncryptDecryptUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.crypto.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Optional;

@SpringBootTest
class BlogSecurityApplicationTests {

	private EncryptDecryptUtils encryptDecryptUtils = new EncryptDecryptUtils();

	@Autowired
	private UsersRepository usersRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testSaveNewProduct() {
		Users user = new Users("john", "cena", "john@gmail.com", "9876543210");
		usersRepository.save(user);

		Optional<Users> user1 = usersRepository.findById((long) 17);
		Assertions.assertEquals("john", user1.get().getFirstName());
	}

	@Test
	void encrypt() {
		String request = "{\n" +
				"        \"firstName\": \"ddd\",\n" +
				"        \"lastName\": \"fjfjd\",\n" +
				"        \"emailId\": \"micheal@gmail.com\",\n" +
				"        \"phoneNumber\": \"9876543210\"\n" +
				"}";

		String encryptedData = Base64.getEncoder().encodeToString(request.getBytes());
        System.out.println("Encode " + encryptedData);

		String input = "W3tpZD05LCBmaXJzdE5hbWU9J01pY2hlYWwnLCBsYXN0TmFtZT0nU2NvZmllbGQnLCBlbWFpbElkPSdtaWNoZWFsQGdtYWlsLmNvbScsIHBob25lTnVtYmVyPSc5ODc2NTQzMjEwJywgcG9zdHM9W119LCB7aWQ9MTAsIGZpcnN0TmFtZT0nTGluY29sbicsIGxhc3ROYW1lPSdCdXJyJywgZW1haWxJZD0nYnVycm93c0BnbWFpbC5jb20nLCBwaG9uZU51bWJlcj0nOTg3NjU0MzIyMicsIHBvc3RzPVtjb20ubGVhcm5pbmcuYmxvZ1NlY3VyaXR5Lm1vZGVsLlBvc3RzQDNkYjFmOGMzXX0sIHtpZD0xMSwgZmlyc3ROYW1lPSdkaGhkJywgbGFzdE5hbWU9J2RqamQnLCBlbWFpbElkPSduZG5kJywgcGhvbmVOdW1iZXI9Jzk4NzY1NDMyMTAnLCBwb3N0cz1bXX1d";

		byte[] decryptedData = Base64.getDecoder().decode(input);
		System.out.println("Decode " + new String(decryptedData));
	}

	@Test
	void encryptData() throws CertificateException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		String request = "{\n" +
				"    \"firstName\": \"John\",\n" +
				"    \"lastName\": \"Cena\",\n" +
				"    \"emailId\": \"john@gmail.com\",\n" +
				"    \"phoneNumber\": \"9876543210\"\n" +
				"}";

		encryptDecryptUtils.encrypt(request);
	}

	@Test
	void decryptData() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		String response = "usbRikJgXaXH1CXXyNxmbsJEJcXdmdkzp9lxNqICUhlL9kmAzxfx0Z/E1XmC/1Fn7Lpvr5LU6cdxcgq5Y7Jsetk8jnqZeC9qmB8Irz/q8V/bpMwHOrRhW3ZZgYKY7lYgSYvtn2U6Ak4SV+rxBojV+BYiFjG/LRYyWbTHrKpmab0D5Rc3B0WzbVvyEdZEfqNPxx19/WG6amlPq8qn1C4BhA/Y+d9JD75p/Urha3gzKMvuOaxMXl8qcaYAqMtuJ/BoBQ/zYse7VyvTM7D7XzN1w6rpun8TF2ufRr+pLVqmDEHARUjRhXRquIPHLToKc3JQrqptVneTUkCJRv1r+VS34IRWGp4hcJCmyEwLxSyTfX/IqBPlsprOTddexaGF0JiFVU/YDLnAFYL4TQIDpUPKyvjqi0nbCfvqayWI40BADGdz9DSEFyqIq5y08h17xj/o8kIh8GTtXblIOrycuybMGUaNbzZpCwGM/uaBGN9G11xlKCSxAj6dK4jksntzTFBTG7IA6B/qiU3gxtdk5Aef2wzAzdsly26fETIeF/kHNAERS1nOupJTYz6dabRjQxJ/Zvg8RvYnPhmLUB1R0cNdGvsqlxjtJ2E3yYDmUKFPRBzvIgoS+3WKoM33Rp8BX6fxgxiRwDe1PDKMQM2v/khE5j4TfjnxE7b044CU1d0FtqI=AwmHTlG4fdEimaxh2fxYlmDGSoi/HI0t6GbilCYVBE6NaaVu5xCSz0mvjlZuNr4YByUEJ44kvZAv+IQHy5uPLSUMFgseK+8E77lDCu5jSUXA4E1DpH2D7xZFvx2NhKc4l821Uda5jtO1TTjllqzaj2mmPUEBlflsmQNOH33uGVj1JUlmnkH911691PTkLUuTCbqPgTWecsT/KmPo5HRtcL4AaMptVWL7ACV5dVJ3Ob/LDjWys1YpDEh+YGiZiD2OByQ/IYhnYLFw5rEfj+EXvLxNLZSYL6CkGiRMH1bzbxHa115LB21fdKfWqqqHBtL8ZiFJS2TFFvAiZrTgKo0HiDUmjKaSSithLCnLWG1PEdu+Gpxj6pSXdtADMosCLnr2JBTIRjM+Mr96XZoo7UoRL6iFXM0W8zOD1KFzbV/vAj3h4QzXgIYxTxW8RY7iGvnPRaTUKUxDwrlEgmR7Ike8ZHSf034H0y4Sg9w+nSMD9jY=";
		encryptDecryptUtils.decrypt(response);
	}
}