package fr.eseo.dis.hubertpa.pfe_application.requestApi;

/**
 * Created by paulhubert on 20/12/17.
 */

public class WebServiceConnexion {

	private final String URL = "https://192.168.4.10/www/pfe/webservice.php";

	Gson gson;

	public WebServiceConnexion() {
		gson = new Gson();
	}




}
