package org.example;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ManagementPrimarie {
    HashMap<Utilizator, String> utilizatori = new HashMap<>();
    static String caleOutput = "src/main/resources/output/";
    static String caleInput = "src/main/resources/input/";
    public Birou<Persoana> birouPersoana;
    public Birou<Angajat> birouAngajat;
    public Birou<Pensionar> birouPensionar;
    public Birou<Elev> birouElev;
    public Birou<EntitateJuridica> birouEntitateJuridica;
    
    public ManagementPrimarie() {
        this.birouPersoana = new Birou<>();
        this.birouAngajat = new Birou<>();
        this.birouPensionar = new Birou<>();
        this.birouElev = new Birou<>();
        this.birouEntitateJuridica = new Birou<>();
    }

    public static void main(String[] args) throws IOException, ParseException {
        ManagementPrimarie managementPrimarie = new ManagementPrimarie();
        String input = caleInput + args[0];
        String output = caleOutput + args[0];

        try {
            File file = new File(input);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts[0].equals("adauga_utilizator")) {
                    managementPrimarie.adaugaUtilizator(managementPrimarie, parts);
                }
                if(parts[0].equals("cerere_noua")) {
                    managementPrimarie.cerereNoua(managementPrimarie, parts, output);
                }
                if(parts[0].equals("afiseaza_cereri_in_asteptare")) {
                    managementPrimarie.afiseazaCereriInAsteptare(managementPrimarie, parts, output);
                }
                if(parts[0].equals("retrage_cerere")) {
                    managementPrimarie.retrageCerere(managementPrimarie, parts);
                }
                if(parts[0].equals("afiseaza_cereri")) {
                    managementPrimarie.afiseazaCereri(managementPrimarie, parts, output);
                }
                if(parts[0].equals("adauga_functionar")) {
                    managementPrimarie.adaugaFunctionar(managementPrimarie, parts);
                }
                if(parts[0].equals("rezolva_cerere")) {
                    managementPrimarie.rezolvaCerere(managementPrimarie, parts);
                }
                if(parts[0].equals("afiseaza_cereri_finalizate")) {
                    managementPrimarie.afiseazaCereriFinalizate(managementPrimarie, parts, output);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void adaugaUtilizator(ManagementPrimarie managementPrimarie, String[] parts) {
        switch (parts[1]) {
            case "persoana":
                Persoana persoana = new Persoana(parts[2]);
                managementPrimarie.utilizatori.put(persoana, "persoana");
                break;
            case "angajat":
                Angajat angajat = new Angajat(parts[2], parts[3]);
                managementPrimarie.utilizatori.put(angajat, "angajat");
                break;
            case "pensionar":
                Pensionar pensionar = new Pensionar(parts[2]);
                managementPrimarie.utilizatori.put(pensionar, "pensionar");
                break;
            case "elev":
                Elev elev = new Elev(parts[2], parts[3]);
                managementPrimarie.utilizatori.put(elev, "elev");
                break;
            case "entitate juridica":
                EntitateJuridica entitateJuridica = new EntitateJuridica(parts[2], parts[3]);
                managementPrimarie.utilizatori.put(entitateJuridica, "entitate juridica");
                break;
        }
    }
    public void cerereNoua(ManagementPrimarie managementPrimarie, String[] parts, String output) throws ParseException {
        Cerere cerere = new Cerere();
        String nume = parts[1];
        Utilizator utilizator = null;
        String tip = "";
        for (Utilizator key : managementPrimarie.utilizatori.keySet()) {
            if (key.getNume().equals(nume)) {
                tip = managementPrimarie.utilizatori.get(key);
                utilizator = key;
            }
        }
        TipCerere tipCerere = cerere.getTipCerere(parts[2]);
        DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date data = inputFormat.parse(parts[3]);
        Integer prioritate = Integer.parseInt(parts[4]);
        Cerere cerereNoua;
        switch (tip) {
            case "persoana":
                Persoana persoana = (Persoana) utilizator;
                cerereNoua = persoana.adaugaCerere(tipCerere, data, prioritate, persoana, output, parts[2]);
                if (cerereNoua != null) {
                    CerereBirou<Persoana> cererePersoana = new CerereBirou<>(cerereNoua, persoana);
                    managementPrimarie.birouPersoana.cereriInAsteptare.add(cererePersoana);
                }
                break;
            case "angajat":
                Angajat angajat = (Angajat) utilizator;
                cerereNoua = angajat.adaugaCerere(tipCerere, data, prioritate, angajat, output, parts[2]);
                if (cerereNoua != null) {
                    CerereBirou<Angajat> cerereAngajat = new CerereBirou<>(cerereNoua, angajat);
                    managementPrimarie.birouAngajat.cereriInAsteptare.add(cerereAngajat);
                }
                break;
            case "pensionar":
                Pensionar pensionar = (Pensionar) utilizator;
                cerereNoua = pensionar.adaugaCerere(tipCerere, data, prioritate, pensionar, output, parts[2]);
                if (cerereNoua != null) {
                    CerereBirou<Pensionar> cererePensionar = new CerereBirou<>(cerereNoua, pensionar);
                    managementPrimarie.birouPensionar.cereriInAsteptare.add(cererePensionar);
                }
                break;
            case "elev":
                Elev elev = (Elev) utilizator;
                cerereNoua = elev.adaugaCerere(tipCerere, data, prioritate, elev, output, parts[2]);
                if (cerereNoua != null) {
                    CerereBirou<Elev> cerereElev = new CerereBirou<>(cerereNoua, elev);
                    managementPrimarie.birouElev.cereriInAsteptare.add(cerereElev);
                }
                break;
            case "entitate juridica":
                EntitateJuridica entitateJuridica = (EntitateJuridica) utilizator;
                cerereNoua = entitateJuridica.adaugaCerere(tipCerere, data, prioritate, entitateJuridica, output, parts[2]);
                if (cerereNoua != null) {
                    CerereBirou<EntitateJuridica> cerereEntitateJuridica = new CerereBirou<>(cerereNoua, entitateJuridica);
                    managementPrimarie.birouEntitateJuridica.cereriInAsteptare.add(cerereEntitateJuridica);
                }
                break;
        }
    }
    public void afiseazaCereriInAsteptare(ManagementPrimarie managementPrimarie, String[] parts, String output){
        String nume = parts[1];
        Utilizator utilizator = null;
        String tip = "";
        for (Utilizator key : managementPrimarie.utilizatori.keySet()) {
            if (key.getNume().equals(nume)) {
                tip = managementPrimarie.utilizatori.get(key);
                utilizator = key;
                break;
            }
        }
        String text = null;
        String antet = null;
        switch (tip) {
            case "persoana":
                Persoana persoana = (Persoana) utilizator;
                text = persoana.afiseazaCereri(persoana.cereriInAsteptare);
                antet = persoana.getNume() + " - cereri in asteptare:";
                break;
            case "angajat":
                Angajat angajat = (Angajat) utilizator;
                text = angajat.afiseazaCereri(angajat.cereriInAsteptare);
                antet = angajat.getNume() + " - cereri in asteptare:";
                break;
            case "pensionar":
                Pensionar pensionar = (Pensionar) utilizator;
                text = pensionar.afiseazaCereri(pensionar.cereriInAsteptare);
                antet = pensionar.getNume() + " - cereri in asteptare:";
                break;
            case "elev":
                Elev elev = (Elev) utilizator;
                text = elev.afiseazaCereri(elev.cereriInAsteptare);
                antet = elev.getNume() + " - cereri in asteptare:";
                break;
            case "entitate juridica":
                EntitateJuridica entitateJuridica = (EntitateJuridica) utilizator;
                text = entitateJuridica.afiseazaCereri(entitateJuridica.cereriInAsteptare);
                antet = entitateJuridica.getNume() + " - cereri in asteptare:";
                break;
        }
        try (FileWriter fw = new FileWriter(output, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(antet);
            out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void retrageCerere(ManagementPrimarie managementPrimarie, String[] parts) throws ParseException {
        String nume = parts[1];
        Utilizator utilizator = null;
        String tip = "";
        for (Utilizator key : managementPrimarie.utilizatori.keySet()) {
            if (key.getNume().equals(nume)) {
                tip = managementPrimarie.utilizatori.get(key);
                utilizator = key;
                break;
            }
        }
        DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date data = inputFormat.parse(parts[2]);
        switch (tip) {
            case "persoana":
                Persoana persoana = (Persoana) utilizator;
                persoana.retrageCerere(persoana, data);
                PriorityQueue<CerereBirou<Persoana>> asteptarePersoana = managementPrimarie.birouPersoana.cereriInAsteptare;
                managementPrimarie.birouPersoana.retrageCerereDinCoada(asteptarePersoana, data);
                break;
            case "angajat":
                Angajat angajat = (Angajat) utilizator;
                angajat.retrageCerere(angajat, data);
                managementPrimarie.birouAngajat.retrageCerereDinCoada(managementPrimarie.birouAngajat.cereriInAsteptare, data);
                break;
            case "pensionar":
                Pensionar pensionar = (Pensionar) utilizator;
                pensionar.retrageCerere(pensionar, data);
                managementPrimarie.birouPensionar.retrageCerereDinCoada(managementPrimarie.birouPensionar.cereriInAsteptare, data);
                break;
            case "elev":
                Elev elev = (Elev) utilizator;
                elev.retrageCerere(elev, data);
                managementPrimarie.birouElev.retrageCerereDinCoada(managementPrimarie.birouElev.cereriInAsteptare, data);
                break;
            case "entitate juridica":
                EntitateJuridica entitateJuridica = (EntitateJuridica) utilizator;
                entitateJuridica.retrageCerere(entitateJuridica, data);
                managementPrimarie.birouEntitateJuridica.retrageCerereDinCoada(managementPrimarie.birouEntitateJuridica.cereriInAsteptare, data);
                break;
        }
    }
    public void afiseazaCereri(ManagementPrimarie managementPrimarie, String[] parts, String output) {
        String tip = parts[1];
        String text = null;
        String antet = null;
        switch (tip) {
            case "persoana":
                text = managementPrimarie.birouPersoana.afiseazaCereri(managementPrimarie.birouPersoana.cereriInAsteptare);
                antet = "persoana - cereri in birou:";
                break;
            case "angajat":
                text = managementPrimarie.birouAngajat.afiseazaCereri(managementPrimarie.birouAngajat.cereriInAsteptare);
                antet = "angajat - cereri in birou:";
                break;
            case "pensionar":
                text = managementPrimarie.birouPensionar.afiseazaCereri(managementPrimarie.birouPensionar.cereriInAsteptare);
                antet = "pensionar - cereri in birou:";
                break;
            case "elev":
                text = managementPrimarie.birouElev.afiseazaCereri(managementPrimarie.birouElev.cereriInAsteptare);
                antet = "elev - cereri in birou:";
                break;
            case "entitate juridica":
                text = managementPrimarie.birouEntitateJuridica.afiseazaCereri(managementPrimarie.birouEntitateJuridica.cereriInAsteptare);
                antet = "entitate juridica - cereri in birou:";
                break;
        }
        try (FileWriter fw = new FileWriter(output, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(antet);
            out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void adaugaFunctionar(ManagementPrimarie managementPrimarie, String[] parts) {
        String tip = parts[1];
        String fisier = caleOutput + "functionar_" + parts[2] + ".txt";
        switch (tip) {
            case "persoana":
                FunctionarPublic<Persoana> functionarPersoana = new FunctionarPublic<>(parts[2]);
                managementPrimarie.birouPersoana.adaugaFunctionar(functionarPersoana, fisier);
                break;
            case "angajat":
                FunctionarPublic<Angajat> functionarAngajat = new FunctionarPublic<>(parts[2]);
                managementPrimarie.birouAngajat.adaugaFunctionar(functionarAngajat, fisier);
                break;
            case "pensionar":
                FunctionarPublic<Pensionar> functionarPensionar = new FunctionarPublic<>(parts[2]);
                managementPrimarie.birouPensionar.adaugaFunctionar(functionarPensionar, fisier);
                break;
            case "elev":
                FunctionarPublic<Elev> functionarElev = new FunctionarPublic<>(parts[2]);
                managementPrimarie.birouElev.adaugaFunctionar(functionarElev, fisier);
                break;
            case "entitate juridica":
                FunctionarPublic<EntitateJuridica> functionarEntitateJuridica = new FunctionarPublic<>(parts[2]);
                managementPrimarie.birouEntitateJuridica.adaugaFunctionar(functionarEntitateJuridica, fisier);
                break;
        }
    }
    public void rezolvaCerere(ManagementPrimarie managementPrimarie, String[] parts) {
        String tip = parts[1];
        String numeFunctionar = parts[2];
        switch (tip) {
            case "persoana":
                managementPrimarie.birouPersoana.rezolvaCerere(managementPrimarie.birouPersoana, numeFunctionar);
                break;
            case "angajat":
                managementPrimarie.birouAngajat.rezolvaCerere(managementPrimarie.birouAngajat, numeFunctionar);
                break;
            case "pensionar":
                managementPrimarie.birouPensionar.rezolvaCerere(managementPrimarie.birouPensionar, numeFunctionar);
                break;
            case "elev":
                managementPrimarie.birouElev.rezolvaCerere(managementPrimarie.birouElev, numeFunctionar);
                break;
            case "entitate juridica":
                managementPrimarie.birouEntitateJuridica.rezolvaCerere(managementPrimarie.birouEntitateJuridica, numeFunctionar);
                break;
        }
    }
    public void afiseazaCereriFinalizate(ManagementPrimarie managementPrimarie, String[] parts, String output) {
        String nume = parts[1];
        Utilizator utilizator = null;
        String tip = "";
        for (Utilizator key : managementPrimarie.utilizatori.keySet()) {
            if (key.getNume().equals(nume)) {
                tip = managementPrimarie.utilizatori.get(key);
                utilizator = key;
                break;
            }
        }
        String text = null;
        String antet = null;
        switch (tip) {
            case "persoana":
                Persoana persoana = (Persoana) utilizator;
                text = persoana.afiseazaCereriFinalizate(persoana.cereriFinalizate);
                antet = persoana.getNume() + " - cereri in finalizate:";
                break;
            case "angajat":
                Angajat angajat = (Angajat) utilizator;
                text = angajat.afiseazaCereriFinalizate(angajat.cereriFinalizate);
                antet = angajat.getNume() + " - cereri in finalizate:";
                break;
            case "pensionar":
                Pensionar pensionar = (Pensionar) utilizator;
                text = pensionar.afiseazaCereriFinalizate(pensionar.cereriFinalizate);
                antet = pensionar.getNume() + " - cereri in finalizate:";
                break;
            case "elev":
                Elev elev = (Elev) utilizator;
                text = elev.afiseazaCereriFinalizate(elev.cereriFinalizate);
                antet = elev.getNume() + " - cereri in finalizate:";
                break;
            case "entitate juridica":
                EntitateJuridica entitateJuridica = (EntitateJuridica) utilizator;
                text = entitateJuridica.afiseazaCereriFinalizate(entitateJuridica.cereriFinalizate);
                antet = entitateJuridica.getNume() + " - cereri in finalizate:";
                break;
        }
        try (FileWriter fw = new FileWriter(output, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(antet);
            out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}