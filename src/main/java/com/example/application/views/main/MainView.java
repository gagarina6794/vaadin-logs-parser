package com.example.application.views.main;

import com.example.application.LogsTable;
import com.example.application.model.LogItem;
import com.example.application.service.LogsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Main")
@Route(value = "")
public class MainView extends VerticalLayout {

    private final LogsService logsService;

    private static final String LOGS_PATH = "src/main/resources/META-INF/resources/logs/";
    private MemoryBuffer memoryBuffer = new MemoryBuffer();
    private Upload singleFileUpload = new Upload(memoryBuffer);

    private Button readDataFromFile;
    private Button showData;
    private LogsTable logsTable = new LogsTable();

    public MainView(LogsService logsService) {
        this.logsService = logsService;
        init();
    }

    public void init() {
        setSizeFull();
        singleFileUpload.setAutoUpload(true);
        singleFileUpload.setMaxFiles(1);
        singleFileUpload.setMaxFileSize(10_000_000);
        singleFileUpload.addSucceededListener(event -> {
            // Get information about the uploaded file
            InputStream fileData = memoryBuffer.getInputStream();
            String fileName = event.getFileName();

            try {
                File logsDirectory = new File(LOGS_PATH);
                for (File file : logsDirectory.listFiles()) {
                    file.delete();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            File file = new File(LOGS_PATH + fileName);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                IOUtils.copy(fileData, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        readDataFromFile = new Button("Read data from log file");
        readDataFromFile.addClickListener(e -> {
            logsService.removeAll();
            File logsDirectory = new File(LOGS_PATH);

            try {
                for (File file : logsDirectory.listFiles()) {
                    List<String> stringList = FileUtils.readLines(file, StandardCharsets.UTF_8);
                    AtomicReference<StringBuilder> sb = new AtomicReference<>(new StringBuilder());
                    stringList.forEach(line -> {
                        try {
                            String[] levelSplit = line.split("\\|");
                            String[] levelSplitLeft = levelSplit[0].split("\\]");
                            String level = levelSplitLeft[levelSplitLeft.length - 1].trim();
                            String[] dateSplit = levelSplit[1].split("\\[");
                            String date = dateSplit[0];
                            String[] threadSplit = dateSplit[1].split("\\]");
                            String thread = threadSplit[0];
                            String[] pathSplit = threadSplit[1].split(" : ");
                            String path = pathSplit[0];
                            String info = threadSplit[1].replace(path + " : ", "");

                            String info1 = sb.get().toString().trim();
                            if (!info1.isEmpty()) {
                                try {
                                    if (info1.contains("Start test") || info1.contains("Test finished")) {
                                        String[] dateSplitter = info1.split("Step");
                                        String date1 = dateSplitter[0].replace("[", "").replace("]", "").trim();
                                        if (date1.length() > 15) {date1 = "";}
                                        String[] locationSpliterator = info1.replace("*", "A").split("AAAAA");
                                        String location1 = locationSpliterator[1].replace("Test finished:", "").replace("Start test:", "").trim();

                                        LogItem previousLogItem = new LogItem("-", date1, "-", location1, info1);
                                        logsService.saveLog(previousLogItem);
                                        sb.set(new StringBuilder());
                                    } else {
                                        LogItem previousLogItem = new LogItem("-", "-", "-", "-", info1);
                                        logsService.saveLog(previousLogItem);
                                        sb.set(new StringBuilder());
                                    }
                                } catch (Exception ex) {
                                    LogItem previousLogItem = new LogItem("-", "-", "-", "-", info1);
                                    logsService.saveLog(previousLogItem);
                                    sb.set(new StringBuilder());
                                }
                            }

                            LogItem logItem = new LogItem(level, date, thread, path, info);
                            logsService.saveLog(logItem);
                        } catch (Exception ex) {
                            sb.get().append(line);
                        }
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        showData = new Button("show data", e -> {
            logsTable.addItems(logsService.getAll());
            logsTable.getDataProvider().refreshAll();
        });

        setMargin(true);
        setJustifyContentMode(JustifyContentMode.START);

        add(singleFileUpload, readDataFromFile, showData, logsTable);
    }

}
