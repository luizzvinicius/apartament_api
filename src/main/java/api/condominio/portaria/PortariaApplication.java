package api.condominio.portaria;

import api.condominio.portaria.enums.RoleEnum;
import api.condominio.portaria.models.User;
import api.condominio.portaria.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class PortariaApplication implements CommandLineRunner {
	public final UserRepository userRepository;
	public final PasswordEncoder encoder;

	public PortariaApplication(UserRepository userRepository, PasswordEncoder jwtEncoder) {
		this.userRepository = userRepository;
		this.encoder = jwtEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(PortariaApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		var email = "testeadm@gmail.com";
		var roleAdmin = RoleEnum.SINDICO;
		userRepository.findByEmail(email).ifPresentOrElse(
				user -> System.out.println("usuário admin já existe " + user.getAuthorities().stream().toList()),
				() -> userRepository.save(new User(
                        UUID.randomUUID(), "Luiz",
                        "12048798745", email,
                        encoder.encode("vinicius"), roleAdmin, LocalDateTime.now()))
		);
	}
}