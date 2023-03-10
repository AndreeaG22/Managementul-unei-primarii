package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Angajat extends Utilizator {
    private final String companie;
    public PriorityQueue<Cerere> cereriInAsteptare = new PriorityQueue<>(10, new Comp());
    ArrayList<Cerere> cereriFinalizate = new ArrayList<>();
    public Angajat(String nume, String companie) {
        super(nume);
        this.companie = companie;
    }

    String getCompanie() {
        return companie;
    }

    String cerere(Angajat angajat, TipCerere tip) throws CrereNecorespunzatoare {
        if(tip != TipCerere.INLOCUIRE_BULETIN  && tip != TipCerere.INLOCUIRE_CARNET_DE_SOFER && tip != TipCerere.INREGISTRARE_VENIT_SALARIAL)
            throw new CrereNecorespunzatoare("Cererea nu este de tipul cerut");
        return "Subsemnatul " + angajat.getNume() + ", angajat la compania " + angajat.getCompanie() +
                ", va rog sa-mi aprobati urmatoarea solicitare: ";
    }
    @Override
    public Cerere adaugaCerere(TipCerere tip, Date data, Integer prioritate, Utilizator angajat, String output, String tipString) {
        try {
            String text = cerere((Angajat) angajat, tip);
            Cerere cerere = new Cerere(tip, text, data, prioritate);
            cereriInAsteptare.add(cerere);
            return cerere;
        } catch (CrereNecorespunzatoare exceptie) {
            try (FileWriter fw = new FileWriter(output, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + tipString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    @Override
    public void retrageCerere(Utilizator angajat, Date data) {
        for (Cerere cerere1 : ((Angajat)angajat).cereriInAsteptare) {
            if (cerere1.getData().equals(data)) {
                cereriInAsteptare.remove(cerere1);
                break;
            }
        }

    }
}

