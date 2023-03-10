package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class EntitateJuridica extends Utilizator {
    private final String reprezentant;
    public PriorityQueue<Cerere> cereriInAsteptare = new PriorityQueue<>(10, new Comp());
    ArrayList<Cerere> cereriFinalizate = new ArrayList<>();

    public EntitateJuridica(String nume, String reprezentant) {
        super(nume);
        this.reprezentant = reprezentant;
    }
    String getReprezentant() {
        return reprezentant;
    }

    String cerere(EntitateJuridica entitateJuridica, TipCerere tip) throws CrereNecorespunzatoare {
        if(tip != TipCerere.CREARE_ACT_CONSTITUTIV && tip != TipCerere.REINNOIRE_AUTORIZATIE)
            throw new CrereNecorespunzatoare("Cererea nu este de tipul cerut");
        return "Subsemnatul " + entitateJuridica.getReprezentant() + ", reprezentant legal al companiei " +
                entitateJuridica.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare: ";
    }
    @Override
    public Cerere adaugaCerere(TipCerere tip, Date data, Integer prioritate, Utilizator entitateJuridica, String output, String tipString) {
        try {
            String text = cerere((EntitateJuridica) entitateJuridica, tip);
            Cerere cerere = new Cerere(tip, text, data, prioritate);
            cereriInAsteptare.add(cerere);
            return cerere;
        } catch (CrereNecorespunzatoare exceptie) {
            try (FileWriter fw = new FileWriter(output, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tipString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    @Override
    public void retrageCerere(Utilizator entitateJuridica, Date data) {
        for (Cerere cerere1 : ((EntitateJuridica)entitateJuridica).cereriInAsteptare) {
            if (cerere1.getData().equals(data)) {
                cereriInAsteptare.remove(cerere1);
                break;
            }
        }

    }
}
