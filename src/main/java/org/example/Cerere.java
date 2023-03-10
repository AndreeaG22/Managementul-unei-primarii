package org.example;

import java.text.SimpleDateFormat;
import java.util.*;

enum TipCerere {
    INLOCUIRE_BULETIN,
    INREGISTRARE_VENIT_SALARIAL,
    INLOCUIRE_CARNET_DE_SOFER,
    INLOCUIRE_CARNET_DE_ELEV,
    CREARE_ACT_CONSTITUTIV,
    REINNOIRE_AUTORIZATIE,
    INREGISTRARE_CUPOANE_DE_PENSIE
}

public class Cerere {
    private TipCerere tipCerere;
    private String text;
    private Date data;
    private Integer prioritate;

    public Cerere(TipCerere tipCerere, String text, Date data, Integer prioritate) {
        this.tipCerere = tipCerere;
        setText(text);
        this.data = data;
        this.prioritate = prioritate;
    }
    public Cerere(){}
    void setText(String text) {
        switch(this.tipCerere) {
            case INLOCUIRE_BULETIN:
                text = text + "inlocuire buletin";
                break;
            case INREGISTRARE_VENIT_SALARIAL:
                text = text + "inregistrare venit salarial";
                break;
            case INLOCUIRE_CARNET_DE_SOFER:
                text = text + "inlocuire carnet de sofer";
                break;
            case INLOCUIRE_CARNET_DE_ELEV:
                text = text + "inlocuire carnet de elev";
                break;
            case CREARE_ACT_CONSTITUTIV:
                text = text + "creare act constitutiv";
                break;
            case REINNOIRE_AUTORIZATIE:
                text = text + "reinnoire autorizatie";
                break;
            case INREGISTRARE_CUPOANE_DE_PENSIE:
                text = text + "inregistrare cupoane de pensie";
                break;
        }
        this.text = text;
    }
    public String getDataString(Date data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        return formatter.format(data);
    }
    public Date getData() {
        return this.data;
    }
    public Integer getPrioritate() {
        return this.prioritate;
    }
    public String getText() {
        return this.text;
    }

    public TipCerere getTipCerere(String tip) {
        switch (tip) {
            case "inlocuire buletin":
                return TipCerere.INLOCUIRE_BULETIN;
            case "inregistrare venit salarial":
                return TipCerere.INREGISTRARE_VENIT_SALARIAL;
            case "inlocuire carnet de sofer":
                return TipCerere.INLOCUIRE_CARNET_DE_SOFER;
            case "inlocuire carnet de elev":
                return TipCerere.INLOCUIRE_CARNET_DE_ELEV;
            case "creare act constitutiv":
                return TipCerere.CREARE_ACT_CONSTITUTIV;
            case "reinnoire autorizatie":
                return TipCerere.REINNOIRE_AUTORIZATIE;
            case "inregistrare cupoane de pensie":
                return TipCerere.INREGISTRARE_CUPOANE_DE_PENSIE;
        }
        return null;
    }

}
class Comp implements Comparator<Cerere> {
    @Override
    public int compare(Cerere o1, Cerere o2) {
        return o1.getData().compareTo(o2.getData());
    }
}