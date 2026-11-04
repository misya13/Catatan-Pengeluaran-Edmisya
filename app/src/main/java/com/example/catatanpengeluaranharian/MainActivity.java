package com.example.catatanpengeluaranharian;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNama, etJumlah;
    Button btnSimpan;
    TextView tvTotal, tvDaftar;

    PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.etNama);
        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);
        tvTotal = findViewById(R.id.tvTotal);
        tvDaftar = findViewById(R.id.tvDaftar);

        prefsManager = new PrefsManager(this);

        btnSimpan.setOnClickListener(v -> {

            String nama = etNama.getText().toString().trim();
            String jumlahText = etJumlah.getText().toString().trim();

            if (nama.isEmpty() || jumlahText.isEmpty()) {
                Toast.makeText(this,
                        "Isi semua data terlebih dahulu!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                long jumlah = Long.parseLong(jumlahText);

                if (jumlah <= 0) {
                    Toast.makeText(this,
                            "Jumlah harus lebih dari 0",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                prefsManager.simpanPengeluaran(nama, jumlah);

                etNama.setText("");
                etJumlah.setText("");

                tampilkanData();

                Toast.makeText(this,
                        "Pengeluaran berhasil disimpan",
                        Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "Jumlah tidak valid",
                        Toast.LENGTH_SHORT).show();
            }
        });

        tampilkanData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefsManager != null) {
            tampilkanData();
        }
    }

    private void tampilkanData() {

        String data = prefsManager.getDaftarPengeluaran();

        if (data.isEmpty()) {
            tvDaftar.setText("Belum ada pengeluaran");
        } else {
            tvDaftar.setText(data);
        }

        long total = prefsManager.getTotal();

        tvTotal.setText(
                "Total Pengeluaran: Rp " +
                        String.format(java.util.Locale.getDefault(), "%,d", total)
        );
    }
}