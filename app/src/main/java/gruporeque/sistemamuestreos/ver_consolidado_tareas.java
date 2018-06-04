package gruporeque.sistemamuestreos;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ver_consolidado_tareas extends AppCompatActivity {

    private int idOperacion;
    private PieChart pieChart;
    private String[]arrayElemento = new String[]{"Productivas","Improductivas","Colaborativas"};
    private int[]valores = new int[]{2,30,90};
    private int[]colores = new int[]{Color.BLUE, Color.GREEN, Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_consolidado_tareas);

        idOperacion = getIntent().getExtras().getInt("id");

        pieChart = (PieChart)findViewById(R.id.pieTareas);
        createCharts();
    }

    private void getOperacion() {
        String URL = ClaseGlobal.Consolidado_T_Select + "?Id="+Integer.toString(idOperacion);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("value");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        switch (jsonArray.getJSONObject(i).get("TIPO_TAREA").toString()){
                            case "TP":
                                valores[0] = Integer.parseInt(jsonArray.getJSONObject(i).get("SUMA_TAREAS").toString());
                                break;
                            case "TI":
                                valores[1] = Integer.parseInt(jsonArray.getJSONObject(i).get("SUMA_TAREAS").toString());
                                break;
                            case "TC":
                                valores[2] = Integer.parseInt(jsonArray.getJSONObject(i).get("SUMA_TAREAS").toString());
                                break;
                        }
                    }

                } catch (JSONException e) { e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorMessageDialog("No se puede conectar al servidor en estos momentos.");
            }
        }); queue.add(stringRequest);
    }
    private void errorMessageDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(message).setTitle("Error").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String repairString (String s){
        if (s.contains("_"))
            s = s.replaceAll("_", " ");
        return s;
    }

    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend (Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < arrayElemento.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = colores[i];
            entry.label = arrayElemento[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < valores.length; i++)
            entries.add(new PieEntry(valores[i]));
        return entries;
    }

    public void createCharts(){
        pieChart = (PieChart)getSameChart(pieChart, "Porcentaje de cada tipo de tarea",Color.GRAY,Color.WHITE,3000);
        pieChart.setHoleRadius(60);
        pieChart.setTransparentCircleRadius(2);
        pieChart.setData(getPieData());

    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colores);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        return dataSet;
    }

    private PieData getPieData(){
        PieDataSet pieDataSet = (PieDataSet)getData(new PieDataSet(getPieEntries(),""));

        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }


    /*
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(arrayElemento));
    }
    */
}
