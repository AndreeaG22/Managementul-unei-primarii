package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public abstract class Utilizator {
    private final String nume;
    public Utilizator(String nume) {
        this.nume = nume;
    }

    String getNume() {
        return nume;
    }

    public String cerere(Utilizator utilizator) {
        return "Subsemnatul " + utilizator.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare: ";
    }
    public String afiseazaCereri(PriorityQueue<Cerere> cereriInAsteptare) {
        PriorityQueue<Cerere> cereriInAsteptare1 = new PriorityQueue<>(10, new Comp());
        StringBuilder text = new StringBuilder();
        while(cereriInAsteptare.size() != 0) {
            Cerere cerere = cereriInAsteptare.poll();
            cereriInAsteptare1.add(cerere);
            text.append(cerere.getDataString(cerere.getData())).append(" - ").append(cerere.getText()).append("\n");
        }
        cereriInAsteptare.addAll(cereriInAsteptare1);
        text = new StringBuilder(text.substring(0, text.length() - 1));
        return text.toString();
    }
    public String afiseazaCereriFinalizate(ArrayList<Cerere> cereriFinalizate) {
        StringBuilder text = new StringBuilder();
        for (Cerere cerere : cereriFinalizate) {
            text.append(cerere.getDataString(cerere.getData())).append(" - ").append(cerere.getText()).append("\n");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        return text.toString();
    }
    public abstract void retrageCerere(Utilizator utilizator, Date data);
    public abstract Cerere adaugaCerere(TipCerere tip, Date data, Integer prioritate, Utilizator utilizator, String output, String tipString);
}
