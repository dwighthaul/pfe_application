package fr.eseo.dis.hubertpa.pfe_application.model.jpoModel;

import org.w3c.dom.Text;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Morgan on 12/01/2018.
 */

public class JuryJPO {

    @Getter @Setter
    private int idJury;

    @Getter @Setter
    private int idProject;

    @Getter @Setter
    private Text date;
}
