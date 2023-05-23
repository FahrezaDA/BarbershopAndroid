package com.project.barbershop.listBooking;

public class Pesanan {
    private String id;
    private String pelayanan;
    private String tanggal;
    private String jam;
    private String status;


    public Pesanan(String id, String pelayanan, String tanggal, String jam, String status) {
        this.id = id;
        this.tanggal = tanggal;
        this.status = status;
        this.pelayanan = pelayanan;
        this.jam = jam;
    }

    public String getId() {
        return id;
    }


    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public String getPelayanan() {
        return pelayanan;
    }

    public String getJam() {
        return jam;
    }
}
