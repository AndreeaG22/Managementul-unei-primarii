package org.example;

public class CerereBirou<T extends Utilizator> {
    private final Cerere cerere;
    private final T utilizator;

    public CerereBirou(Cerere cerere, T utilizator) {
        this.cerere = cerere;
        this.utilizator = utilizator;
    }

    Cerere getCerere() {
        return cerere;
    }
    T getUtilizator() {
        return this.utilizator;
    }
}
