package fr.eseo.dis.hubertpa.pfe_application.model.jpoModel;

import org.w3c.dom.Text;

import lombok.Getter;
import lombok.Setter;

public class UserJPO {
    @Getter @Setter
    private long idUser;

    @Getter @Setter
    private String forename;

    @Getter @Setter
    private String surname;
}
