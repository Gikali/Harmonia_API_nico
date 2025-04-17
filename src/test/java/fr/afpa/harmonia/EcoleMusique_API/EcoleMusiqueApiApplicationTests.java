package fr.afpa.harmonia.EcoleMusique_API;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:mysql://localhost:3306/harmonia",
		"spring.datasource.username=root",
		"spring.datasource.password=root",
		"spring.jpa.hibernate.ddl-auto=update"
})
class EcoleMusiqueApiApplicationTests {

	@Test
	void contextLoads() {
		//Vide par défau
	}

}
