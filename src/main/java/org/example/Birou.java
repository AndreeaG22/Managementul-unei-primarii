package org.example;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Birou<T extends Utilizator>{
    public PriorityQueue<CerereBirou<T>> cereriInAsteptare;
    public HashMap<FunctionarPublic<T>, String> functionari = new HashMap<>();
    public Birou() {
        cereriInAsteptare = new PriorityQueue<>(10, new ComparatorCereri<>());
    }
    public void adaugaFunctionar(FunctionarPublic<T> functionar, String fisier) {
        functionari.put(functionar, fisier);
    }
    public String afiseazaCereri(PriorityQueue<CerereBirou<T>> cereriInAsteptare) {
        PriorityQueue<CerereBirou<T>> cereriInAsteptare1 = new PriorityQueue<>(10, new ComparatorCereri<>());
        StringBuilder text = new StringBuilder();
        while(cereriInAsteptare.size() != 0) {
            CerereBirou<T> cerere = cereriInAsteptare.poll();
            cereriInAsteptare1.add(cerere);
            text.append(cerere.getCerere().getPrioritate()).append(" - ");
            text.append(cerere.getCerere().getDataString(cerere.getCerere().getData())).append(" - ").append(cerere.getCerere().getText()).append("\n");
        }
        cereriInAsteptare.addAll(cereriInAsteptare1);
        //remove last newline
        text = new StringBuilder(text.substring(0, text.length() - 1));
        return text.toString();
    }
    public void rezolvaCerere(Birou<T> birou, String numeFunctionar) {
        CerereBirou<T> cerereRezolvata = birou.cereriInAsteptare.poll();
        FunctionarPublic<T> functionar = new FunctionarPublic<>(numeFunctionar);
        assert cerereRezolvata != null;
        String data = cerereRezolvata.getCerere().getDataString(cerereRezolvata.getCerere().getData());
        T utilizator = cerereRezolvata.getUtilizator();
        if(utilizator.getClass().getSimpleName().equals("Persoana")) {
            Persoana persoana = (Persoana) utilizator;
            persoana.cereriInAsteptare.remove(cerereRezolvata.getCerere());
            persoana.cereriFinalizate.add(cerereRezolvata.getCerere());
        } else if (utilizator.getClass().getSimpleName().equals("Angajat")){
            Angajat angajat = (Angajat) utilizator;
            angajat.cereriInAsteptare.remove(cerereRezolvata.getCerere());
            angajat.cereriFinalizate.add(cerereRezolvata.getCerere());
        } else if (utilizator.getClass().getSimpleName().equals("Pensionar")){
            Pensionar pensionar = (Pensionar) utilizator;
            pensionar.cereriInAsteptare.remove(cerereRezolvata.getCerere());
            pensionar.cereriFinalizate.add(cerereRezolvata.getCerere());
        } else if (utilizator.getClass().getSimpleName().equals("Elev")){
            Elev elev = (Elev) utilizator;
            elev.cereriInAsteptare.remove(cerereRezolvata.getCerere());
            elev.cereriFinalizate.add(cerereRezolvata.getCerere());
        } else if (utilizator.getClass().getSimpleName().equals("EntitateJuridica")){
            EntitateJuridica entitateJuridica = (EntitateJuridica) utilizator;
            entitateJuridica.cereriInAsteptare.remove(cerereRezolvata.getCerere());
            entitateJuridica.cereriFinalizate.add(cerereRezolvata.getCerere());
        }

        String numeUtilizator = cerereRezolvata.getUtilizator().getNume();
        String fisier = functionar.gasesteFisier(numeFunctionar, this.functionari);
        String text = data + " - " + numeUtilizator;
        functionar.scrieInFisier(fisier, text);
    }

    public void retrageCerereDinCoada(PriorityQueue<CerereBirou<T>> coada, Date data) {
        PriorityQueue<CerereBirou<T>> coada1 = new PriorityQueue<>(10, new ComparatorCereri<>());
        while(coada.size() != 0) {
            CerereBirou<T> cerere = coada.poll();
            if(cerere.getCerere().getData().compareTo(data) != 0) {
                coada1.add(cerere);
            }
        }
        coada.addAll(coada1);
    }
}

class ComparatorCereri<T extends Utilizator> implements Comparator<CerereBirou<T>> {
    @Override
    public int compare(CerereBirou o1, CerereBirou o2) {
        int rez = o1.getCerere().getPrioritate().compareTo(o2.getCerere().getPrioritate());
        if(rez == 0) {
            rez = o1.getCerere().getData().compareTo(o2.getCerere().getData());
        } else if(rez < 0) {
            rez = 1;
        } else {
            rez = -1;
        }
        return rez;
    }
}