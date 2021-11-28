package com.example.sr1615shrek.entity;

public enum EntityHierarchy {
    STATIC_PASSIVE(0),
    DYNAMIC(1),
    STATIC_ACTIVE(2);

    private final int rank;   // if less is better

    EntityHierarchy(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public boolean isMoreImportantThan(EntityHierarchy entityHierarchy) {
        return rank < entityHierarchy.getRank();
    }
}
