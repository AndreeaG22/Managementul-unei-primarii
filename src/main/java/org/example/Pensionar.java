package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class Pensionar extends Utilizator{
    public PriorityQueue<Cerere> cereriInAsteptare = new PriorityQueue<>(10, new Comp());
    ArrayList<Cerere> cereriFinalizate = new ArrayList<>();
    public Pensionar(String nume) {
        super(nume);
    }
    String cerere(Pensionar pensionar, TipCerere tip) throws CrereNecorespunzatoare {
        if(tip != TipCerere.INLOCUIRE_BULETIN  && tip != TipCerere.INLOCUIRE_CARNET_DE_SOFER && tip != TipCerere.INREGISTRARE_CUPOANE_DE_PENSIE)
            throw new CrereNecorespunzatoare("Cererea nu este de tipul cerut");
        return super.cerere(pensionar);
    }
    @Override
    public Cerere adaugaCerere(TipCerere tip, Date data, Integer prioritate, Utilizator pensionar, String output, String tipString) {
        try {
            String text = cerere((Pensionar) pensionar, tip);
            Cerere cerere = new Cerere(tip, text, data, prioritate);
            cereriInAsteptare.add(cerere);
            return cerere;
        } catch (CrereNecorespunzatoare exceptie) {
            try (FileWriter fw = new FileWriter(output, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("Utilizatorul de tip pensionar nu poate inainta o cerere de tip " + tipString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    @Override
    public void retrageCerere(Utilizator pensionar, Date data) {
        for (Cerere cerere1 : ((Pensionar)pensionar).cereriInAsteptare) {
            if (cerere1.getData().equals(data)) {
                cereriInAsteptare.remove(cerere1);
                break;
            }
        }

    }
}
