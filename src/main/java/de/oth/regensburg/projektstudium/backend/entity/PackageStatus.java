package de.oth.regensburg.projektstudium.backend.entity;

public enum PackageStatus {
    CREATED,
    IN_HANDOVER,
    IN_TRANSPORT,
    REATTEMPT_DELIVERY,
    DELIVERED,
    NOT_DELIVERABLE,

    COLLECTED,
    RECEIVED_BY_LC
}
