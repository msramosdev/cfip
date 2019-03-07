﻿package open.digytal;

import java.util.Objects;

import javax.swing.UIManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.Login;

@SpringBootApplication
public class SpringBootApp {
	static ConfigurableApplicationContext contexto;

	public static void main(String[] args) {
		try {
			String lf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lf);
			init(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void init(String[] args) {
		DesktopApp.exibirSplash();
		String FILE_URL = Objects.toString(System.getProperty("db.url"), "file:/opendigytal/cfip/database/cfipdb");
		System.out.println("Iniciando o HSQLDB em " + FILE_URL);
		final String[] dbArg = { "--database.0", FILE_URL, "--dbname.0", "cfipdb", "--port", "5454" };
		org.hsqldb.server.Server.main(dbArg);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootApp.class);
		builder.headless(false);
		contexto = builder.run(args);
		Login login = SpringBootApp.getBean(Login.class);
		login.setVisible(true);
	}

	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
