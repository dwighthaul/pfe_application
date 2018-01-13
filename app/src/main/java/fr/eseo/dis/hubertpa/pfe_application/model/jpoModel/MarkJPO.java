package fr.eseo.dis.hubertpa.pfe_application.model.jpoModel;

import org.w3c.dom.Text;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Morgan on 12/01/2018.
 */

public class MarkJPO {

    @Getter @Setter
    private int idNote;

    @Getter @Setter
    private int idProject;

    @Getter @Setter
    private int valueNote;
}
