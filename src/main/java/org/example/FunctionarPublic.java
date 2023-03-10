package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class FunctionarPublic<T>{
    private final String nume;
    public FunctionarPublic(String nume) {
        this.nume = nume;
    }
    String getNume() {
        return nume;
    }
    String gasesteFisier(String nume, HashMap<FunctionarPublic<T>, String> functionari) {
        String fisier = "";
        for(FunctionarPublic<T> functionar : functionari.keySet()) {
            if(functionar.getNume().equals(nume)) {
                fisier = functionari.get(functionar);
            }
        }
        return fisier;
    }

    public void scrieInFisier(String fisier, String text) {
        try (FileWriter fw = new FileWriter(fisier, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
