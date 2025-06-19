package com.nep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NepmSo2LevelController {

    public BarChart barChart;
    @FXML
    private PieChart pieChart;

    @FXML
    private void initialize() {
        loadSO2Data();
        setupChartInteractions();
        loadProvinceCOData();
    }

    private static final Color[] levelColors = {
            Color.rgb(129, 212, 250),  // 一级：浅蓝
            Color.rgb(77, 182, 172),   // 二级：青绿
            Color.rgb(255, 224, 130),  // 三级：橙黄
            Color.rgb(255, 171, 145),  // 四级：浅红
            Color.rgb(239, 154, 154),  // 五级：粉红
            Color.rgb(186, 104, 200)   // 六级：紫色
    };

    private void loadProvinceCOData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> dataList = objectMapper.readValue(new File("src/main/resources/NepDatas/JSONData/aqi_finish.json"), List.class);

            // Map<省份, int[7]> 用于存储每个省份各个等级的数量（0 忽略）
            Map<String, int[]> provinceLevelMap = new java.util.HashMap<>();

            for (Map<String, Object> item : dataList) {
                String province = (String) item.get("proviceName");
                int level = ((Number) item.get("so2_level")).intValue();

                if (level < 1 || level > 6) continue;

                provinceLevelMap.putIfAbsent(province, new int[7]);
                provinceLevelMap.get(province)[level]++;
            }

            barChart.getData().clear();
            String[] levelNames = {"一级", "二级", "三级", "四级", "五级", "六级"};

            // 每个等级对应一个系列
            for (int level = 1; level <= 6; level++) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(levelNames[level - 1]);

                for (Map.Entry<String, int[]> entry : provinceLevelMap.entrySet()) {
                    String province = entry.getKey();
                    int count = entry.getValue()[level];

                    XYChart.Data<String, Number> dataNode = new XYChart.Data<>(province, count);
                    series.getData().add(dataNode);

                    int finalLevel = level;
                    dataNode.nodeProperty().addListener((obs, oldNode, newNode) -> {
                        if (newNode != null) {
                            Color color = levelColors[finalLevel - 1]; // 使用统一颜色
                            String css = String.format("-fx-bar-fill: rgb(%d,%d,%d);",
                                    (int) (color.getRed() * 255),
                                    (int) (color.getGreen() * 255),
                                    (int) (color.getBlue() * 255));
                            newNode.setStyle(css);
                        }
                    });
                    series.getData().add(new XYChart.Data<>(province, count));
                }
                barChart.getData().add(series);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSO2Data() {
        try {
            // 读取 JSON 文件
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> dataList = objectMapper.readValue(new File("src/main/resources/NepDatas/JSONData/aqi_finish.json"), List.class);
            // 提取 so2_level 数据
            int[] so2Levels = new int[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                so2Levels[i] = ((Number) dataList.get(i).get("so2_level")).intValue();
            }

            // 统计每个等级出现的次数
            int[] levelCounts = new int[7]; // 0-6级
            for (int level : so2Levels) {
                if (level >= 1 && level <= 6) {
                    levelCounts[level]++;
                }
            }

            // 准备图表数据
            ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
            String[] levelNames = {"", "一级", "二级", "三级", "四级", "五级", "六级"};

            int total = so2Levels.length;
            for (int i = 1; i <= 6; i++) {
                if (levelCounts[i] > 0) {
                    double percentage = (double) levelCounts[i] / total * 100;
                    PieChart.Data data = new PieChart.Data(
                            levelNames[i] + " (" + String.format("%.1f%%", percentage) + ")",
                            levelCounts[i]
                    );
                    chartData.add(data);
                }
            }

            // 设置图表数据
            pieChart.setData(chartData);

            // 监听数据集合变化，数据项节点创建后再设置样式
            chartData.addListener((ListChangeListener<PieChart.Data>) c -> {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (PieChart.Data addedData : c.getAddedSubList()) {
                            String levelName = addedData.getName().substring(0, 2);
                            int level = 0;
                            for (int i = 1; i <= 6; i++) {
                                if (levelNames[i].equals(levelName)) {
                                    level = i;
                                    break;
                                }
                            }
                            if (level >= 1 && level <= 6) {
                                Color color = levelColors[level - 1];
                                String colorCss = String.format(
                                        "rgb(%d, %d, %d)",
                                        (int) (color.getRed() * 255),
                                        (int) (color.getGreen() * 255),
                                        (int) (color.getBlue() * 255)
                                );
                                addedData.getNode().setStyle("-fx-pie-color: " + colorCss + ";");
                            }
                        }
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupChartInteractions() {
        Pane parentPane = (Pane) pieChart.getParent();

        // 统一创建提示框，只用一个 Text 和一个 Rectangle
        Text countText = new Text();
        Rectangle bgRect = new Rectangle();
        bgRect.setFill(Color.WHITE);
        bgRect.setOpacity(0.85);
        bgRect.setArcWidth(10);
        bgRect.setArcHeight(10);

        StackPane label = new StackPane(bgRect, countText);
        label.setVisible(false);
        label.setMouseTransparent(true); // 关键：不参与事件捕捉

        parentPane.getChildren().add(label); // 提前添加，避免动态布局触发 redraw

        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().setOnMouseEntered(event -> {
                event.consume(); // 防止事件向上冒泡
                data.getNode().setScaleX(1.1);
                data.getNode().setScaleY(1.1);

                String levelName = data.getName().substring(0, 2);
                int count = (int) data.getPieValue();
                countText.setText(levelName + "\n" + count + "个城市");
                countText.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

                // 调整背景大小
                bgRect.setWidth(countText.getLayoutBounds().getWidth() + 20);
                bgRect.setHeight(countText.getLayoutBounds().getHeight() + 14);

                // 获取位置
                Bounds nodeBounds = data.getNode().localToScene(data.getNode().getBoundsInLocal());
                Bounds paneBounds = parentPane.localToScene(parentPane.getBoundsInLocal());
                double offsetX = nodeBounds.getMinX() - paneBounds.getMinX();
                double offsetY = nodeBounds.getMinY() - paneBounds.getMinY();

                double centerX = offsetX + nodeBounds.getWidth() / 2;
                double centerY = offsetY + nodeBounds.getHeight() / 2;

                label.setLayoutX(centerX - bgRect.getWidth() / 2);
                label.setLayoutY(centerY - bgRect.getHeight() / 2);

                label.setVisible(true);
            });

            data.getNode().setOnMouseExited(event -> {
                event.consume();
                data.getNode().setScaleX(1.0);
                data.getNode().setScaleY(1.0);
                label.setVisible(false);
            });
        }
    }
}