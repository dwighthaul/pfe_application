package fr.eseo.dis.hubertpa.pfe_application.model.jpoModel;

import lombok.Getter;
import lombok.Setter;


public class MemoPosterJPO {

    @Getter @Setter
    private int idMemo;

    @Getter @Setter
    private int idProject;

    @Getter @Setter
    private String text;
}
