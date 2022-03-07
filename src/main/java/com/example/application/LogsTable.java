package com.example.application;

import com.example.application.model.LogItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializablePredicate;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogsTable extends Grid<LogItem> {

    protected final List<LogItem> items = new ArrayList<>();

    public LogsTable() {
        setSizeFull();
        setItems(items);

        addColumn(LogItem::getId)
                .setKey("ID")
                .setHeader(new FilteredGridHeaderCell("ID",
                        event -> addFilter(data -> StringUtils.containsIgnoreCase(
                                data.getId().toString(), event.getSource().getValue()))))
                .setWidth("80px")
                .setResizable(true)
                .setSortable(true);

        addColumn(LogItem::getLevel)
                .setKey("LEVEL")
                .setHeader(new FilteredGridHeaderCell("LEVEL",
                        event -> addFilter(data -> StringUtils.containsIgnoreCase(
                                data.getLevel(), event.getSource().getValue()))))
                .setWidth("100px")
                .setResizable(true)
                .setSortable(true);

        addColumn(LogItem::getDate)
                .setKey("DATE")
                .setHeader(new FilteredGridHeaderCell("DATE",
                        event -> addFilter(data -> StringUtils.containsIgnoreCase(
                                data.getDate(), event.getSource().getValue()))))
                .setWidth("140px")
                .setResizable(true)
                .setSortable(true);

        addColumn(LogItem::getThread)
                .setKey("THREAD")
                .setHeader(new FilteredGridHeaderCell("THREAD",
                        event -> addFilter(data -> StringUtils.containsIgnoreCase(
                                data.getThread(), event.getSource().getValue()))))
                .setWidth("150px")
                .setResizable(true)
                .setSortable(true);

        addColumn(LogItem::getLocation)
                .setKey("LOCATION")
                .setHeader(new FilteredGridHeaderCell("LOCATION",
                        event -> addFilter(data -> StringUtils.containsIgnoreCase(
                                data.getLocation(), event.getSource().getValue()))))
                .setWidth("600px")
                .setResizable(true)
                .setSortable(true);

        addComponentColumn(model -> {
            Label label = new Label(model.getInfo());
            label.setTitle(model.getInfo());
            return label;
        })
                .setKey("INFO")
                .setHeader(new FilteredGridHeaderCell("INFO",
                        event -> addFilter(data -> StringUtils.containsIgnoreCase(
                                data.getInfo(), event.getSource().getValue()))))
                .setAutoWidth(true)
                .setResizable(true)
                .setSortable(true);
    }

    public void addItem(LogItem item) {
        if (item == null) return;
        items.add(item);
    }

    public void addItems(List<LogItem> items) {
        if (items == null || items.isEmpty()) return;
        this.items.addAll(items);
    }

    public void clearAll() {
        items.clear();
        getDataProvider().refreshAll();
    }

    public void addFilter(SerializablePredicate<LogItem> filter) {
        Objects.requireNonNull(filter, "Filter cannot be null");
        getListDataProvider().addFilter(filter);
    }

    public ListDataProvider<LogItem> getListDataProvider() {
        return (ListDataProvider<LogItem>) getDataProvider();
    }
}
