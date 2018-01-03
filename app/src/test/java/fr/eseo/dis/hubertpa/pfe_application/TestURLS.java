package fr.eseo.dis.hubertpa.pfe_application;

import org.junit.Test;

import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.StyleProject;

import static org.junit.Assert.*;

/**
 * Created by paulhubert on 30/12/17.
 */

public class TestURLS {

	private String userName = WebServiceConnexion.DEFAULT_LOGIN;
	private String userPass = WebServiceConnexion.DEFAULT_PASSWORD;
	private String token = "01234";
	private int idProject = 1;
	private int idStudent = 2;
	private int idJury = 3;
	private int noteValue = 13;

	private int seed = 100;

	private StyleProject style = StyleProject.FULL;


	@Test
	public void displayURL() {
		String s = WebServiceConnexion.getLOGON(userName, userPass);
		String defaultUrl = WebServiceConnexion.URL + "LOGON" + "&user=" + userName + "&pass=" + userPass;
		assertEquals(s, defaultUrl);
	}


	@Test
	public void testURLgetLOGON() throws Exception {
		//"https://192.168.4.10/www/pfe/webservice.php?q=LOGON&user=jpo&pass=w872o32HkYAO"
		String s = WebServiceConnexion.getLOGON(userName, userPass);
		String defaultUrl = "https://192.168.4.10/www/pfe/webservice.php?q=LOGON&user=jpo&pass=w872o32HkYAO";
		assertEquals(s, defaultUrl);
	}

	@Test
	public void testURLgetLIPRJ() throws Exception {
		//"https://192.168.4.10/www/pfe/webservice.php?q=LIPRJ&user=jpo&token=01234";
		String s = WebServiceConnexion.getLIPRJ(userName, token);
		String defaultUrl = "https://192.168.4.10/www/pfe/webservice.php?q=LIPRJ&user=jpo&token=01234";
		assertEquals(s, defaultUrl);
	}

	@Test
	public void testURLgetMYPRJ() throws Exception {
		//"https://192.168.4.10/www/pfe/webservice.php?q=MYPRJ&user=jpo&token=01234"
		String s = WebServiceConnexion.getMYPRJ(userName, token);
		String defaultUrl = "https://192.168.4.10/www/pfe/webservice.php?q=MYPRJ&user=jpo&token=01234";
		assertEquals(s, defaultUrl);
	}


}
