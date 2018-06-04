package gruporeque.sistemamuestreos;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import java.util.ArrayList;

public class ver_consolidado_tareas extends AppCompatActivity {

    private PieChart pieChart;
    private String[]arrayElemento = new String[]{"Productivas","Improductivas","Colaborativas"};
    private int[]valores = new int[]{15,25,20};
    private int[]colores = new int[]{Color.BLUE, Color.GREEN, Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_consolidado_tareas);

        pieChart = (PieChart)findViewById(R.id.pieTareas);
        createCharts();
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
        pieChart.setHoleRadius(10);
        pieChart.setTransparentCircleRadius(36);
        pieChart.setData(getPieData());

    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colores);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private PieData getPieData(){
        PieDataSet pieDataSet = (PieDataSet)getData(new PieDataSet(getPieEntries(),""));

        pieDataSet.setSliceSpace(2);
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
