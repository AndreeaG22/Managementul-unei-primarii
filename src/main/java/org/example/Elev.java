package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class Elev extends Utilizator {
    private final String scoala;
    public PriorityQueue<Cerere> cereriInAsteptare = new PriorityQueue<>(10, new Comp());
    ArrayList<Cerere> cereriFinalizate = new ArrayList<>();

    public Elev(String nume, String scoala) {
        super(nume);
        this.scoala = scoala;
    }
    String getScoala() {
        return scoala;
    }

    String cerere(Elev elev, TipCerere tip) throws CrereNecorespunzatoare {
        if(tip != TipCerere.INLOCUIRE_BULETIN  && tip != TipCerere.INLOCUIRE_CARNET_DE_ELEV)
            throw new CrereNecorespunzatoare("Cererea nu este de tipul cerut");
        return "Subsemnatul " + elev.getNume() + ", elev la scoala " + elev.getScoala() +
                ", va rog sa-mi aprobati urmatoarea solicitare: ";
    }
    @Override
    public Cerere adaugaCerere(TipCerere tip, Date data, Integer prioritate, Utilizator elev, String output, String tipString) {
        try {
            String text = cerere((Elev)elev, tip);
            Cerere cerere = new Cerere(tip, text, data, prioritate);
            cereriInAsteptare.add(cerere);
            return cerere;
        }catch (CrereNecorespunzatoare exceptie) {
            try (FileWriter fw = new FileWriter(output, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tipString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    @Override
    public void retrageCerere(Utilizator elev, Date data) {
        for (Cerere cerere1 : ((Elev)elev).cereriInAsteptare) {
            if (cerere1.getData().equals(data)) {
                cereriInAsteptare.remove(cerere1);
                break;
            }
        }

    }
}
