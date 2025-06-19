package com.nep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nep.po.AqiFinish;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NepmAqiStatisticsController {

    @FXML
    private Canvas aqiChart;
    @FXML
    private MFXButton downloadBtn;
    @FXML
    private MFXButton toggleSO2;
    @FXML
    private MFXButton toggleCO;
    @FXML
    private MFXButton togglePM;

    private Map<String, double[]> provinceData = new HashMap<>();
    private boolean showSO2 = true;
    private boolean showCO = true;
    private boolean showPM = true;

    private final Color COLOR_SO2 = Color.rgb(231, 76, 60);
    private final Color COLOR_CO = Color.rgb(52, 152, 219);
    private final Color COLOR_PM = Color.rgb(46, 204, 113);

    public void initialize() {
        loadAqiFinishData();
        downloadBtn.setOnAction(e -> downloadChart());
        toggleSO2.setOnAction(e -> toggleData("SO2"));
        toggleCO.setOnAction(e -> toggleData("CO"));
        togglePM.setOnAction(e -> toggleData("PM"));
        drawChart();
    }

    private void loadAqiFinishData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AqiFinish[] arr = mapper.readValue(
                    new File("src/main/resources/NepDatas/JSONData/aqi_finish.json"),
                    AqiFinish[].class
            );

            Map<String, List<AqiFinish>> map = new HashMap<>();
            for (AqiFinish f : arr) {
                map.computeIfAbsent(f.getProviceName(), k -> new ArrayList<>()).add(f);
            }

            for (Map.Entry<String, List<AqiFinish>> e : map.entrySet()) {
                String prov = e.getKey();
                List<AqiFinish> list = e.getValue();
                double so2Sum = 0, coSum = 0, pmSum = 0;
                for (AqiFinish f : list) {
                    so2Sum += f.getSo2();
                    coSum += f.getCo();
                    pmSum += f.getPm();
                }
                double[] data = new double[]{
                        so2Sum / list.size(),
                        coSum / list.size(),
                        pmSum / list.size()
                };
                provinceData.put(prov, data);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void drawChart() {
        GraphicsContext gc = aqiChart.getGraphicsContext2D();
        double w = aqiChart.getWidth(), h = aqiChart.getHeight();
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, w, h);
        drawAxes(gc, w, h);
        if (provinceData.isEmpty()) {
            gc.setFill(Color.GRAY);
            gc.fillText("没有数据", w / 2 - 40, h / 2);
            return;
        }
        int n = provinceData.size();
        double barW = 60, gap = (w - 100 - n * barW) / (n + 1), x0 = 80;
        int i = 0;
        for (Map.Entry<String, double[]> e : provinceData.entrySet()) {
            String prov = e.getKey();
            double[] d = e.getValue();
            double x = x0 + i * (barW + gap);
            double yBase = h - 60;
            gc.setFill(Color.BLACK);
            gc.fillText(prov, x + barW / 2 - 15, yBase + 20);

            if (showSO2) {
                double ht = mapValueToHeight(d[0], 0, 200, yBase);
                gc.setFill(COLOR_SO2);
                gc.fillRect(x, yBase - ht, barW / 3, ht);
                gc.strokeRect(x, yBase - ht, barW / 3, ht);
                gc.fillText(String.format("%.1f", d[0]), x, yBase - ht - 5);
            }
            if (showCO) {
                double ht = mapValueToHeight(d[1], 0, 200, yBase);
                gc.setFill(COLOR_CO);
                gc.fillRect(x + barW / 3, yBase - ht, barW / 3, ht);
                gc.strokeRect(x + barW / 3, yBase - ht, barW / 3, ht);
                gc.fillText(String.format("%.1f", d[1]), x + barW / 3, yBase - ht - 5);
            }
            if (showPM) {
                double ht = mapValueToHeight(d[2], 0, 200, yBase);
                gc.setFill(COLOR_PM);
                gc.fillRect(x + 2 * barW / 3, yBase - ht, barW / 3, ht);
                gc.strokeRect(x + 2 * barW / 3, yBase - ht, barW / 3, ht);
                gc.fillText(String.format("%.1f", d[2]), x + 2 * barW / 3, yBase - ht - 5);
            }
            i++;
        }
    }

    private void drawAxes(GraphicsContext gc, double w, double h) {
        double y0 = h - 60;
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(50, y0, w - 20, y0);
        gc.strokeLine(50, y0, 50, 30);
        gc.setFill(Color.BLACK);
        int maxVal = 200;
        int step = 20;
        int steps = maxVal / step;
        for (int i = 0; i <= steps; i++) {
            double y = y0 - (y0 - 30) * i / steps;
            gc.strokeLine(45, y, 50, y);
            gc.fillText(String.valueOf(i * step), 20, y + 5);
        }
        gc.fillText("省份", w / 2 - 30, y0 + 40);
    }

    private double mapValueToHeight(double v, double min, double max, double base) {
        if (v <= min) return 0;
        if (v >= max) return base - 30;
        return (v - min) / (max - min) * (base - 30);
    }

    private void downloadChart() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("保存图表");
            chooser.getExtensionFilters().addAll(
                    new ExtensionFilter("PNG图片", "*.png"),
                    new ExtensionFilter("JPEG图片", "*.jpg")
            );
            chooser.setInitialFileName("aqi数据分析图表");
            File file = chooser.showSaveDialog(aqiChart.getScene().getWindow());
            if (file != null) {
                WritableImage snap = aqiChart.snapshot(null, null);
                BufferedImage img = new BufferedImage(
                        (int) snap.getWidth(), (int) snap.getHeight(), BufferedImage.TYPE_INT_ARGB
                );
                for (int y = 0; y < img.getHeight(); y++)
                    for (int x = 0; x < img.getWidth(); x++) {
                        Color c = snap.getPixelReader().getColor(x, y);
                        int argb = javafx.scene.paint.Color.rgb(
                                (int) (c.getRed() * 255), (int) (c.getGreen() * 255), (int) (c.getBlue() * 255), c.getOpacity()
                        ).hashCode();
                        img.setRGB(x, y, argb);
                    }
                String ext = file.getName().substring(file.getName().lastIndexOf('.') + 1).toLowerCase();
                ImageIO.write(img, ext.equals("jpg") ? "jpg" : "png", file);
                showAlert("成功", "图表已成功保存到：" + file.getAbsolutePath());
            }
        } catch (IOException e) {
            showAlert("错误", "保存图表时出错：" + e.getMessage());
        }
    }

    private void toggleData(String type) {
        switch (type) {
            case "SO2":
                showSO2 = !showSO2;
                toggleSO2.setText(showSO2 ? "隐藏SO₂" : "显示SO₂");
                break;
            case "CO":
                showCO = !showCO;
                toggleCO.setText(showCO ? "隐藏CO" : "显示CO");
                break;
            case "PM":
                showPM = !showPM;
                togglePM.setText(showPM ? "隐藏PM" : "显示PM");
                break;
        }
        drawChart();
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
