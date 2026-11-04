package com.example.catatanpengeluaranharian;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONObject;

public class PrefsManager {

    private SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getSharedPreferences(
                "CatatanPengeluaran",
                Context.MODE_PRIVATE
        );
    }

    public void simpanPengeluaran(String nama, long jumlah) {
        try {
            JSONArray data = getDataArray();

            JSONObject item = new JSONObject();
            item.put("nama", nama);
            item.put("jumlah", jumlah);

            data.put(item);

            prefs.edit()
                    .putString("data_pengeluaran", data.toString())
                    .apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray getDataArray() {
        String json = prefs.getString("data_pengeluaran", "[]");

        try {
            return new JSONArray(json);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    public String getDaftarPengeluaran() {
        JSONArray data = getDataArray();
        StringBuilder hasil = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            JSONObject item = data.optJSONObject(i);

            if (item != null) {
                String nama = item.optString("nama");
                long jumlah = item.optLong("jumlah");

                hasil.append(i + 1)
                        .append(". ")
                        .append(nama)
                        .append(" - Rp ")
                        .append(String.format("%,d", jumlah))
                        .append("\n");
            }
        }

        return hasil.toString();
    }

    public long getTotal() {
        JSONArray data = getDataArray();
        long total = 0;

        for (int i = 0; i < data.length(); i++) {
            JSONObject item = data.optJSONObject(i);

            if (item != null) {
                total += item.optLong("jumlah");
            }
        }

        return total;
    }
}