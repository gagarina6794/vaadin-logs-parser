package com.example.application;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.DomEventListener;

public class FilteredGridHeaderCell extends FlexLayout {

    private final Label captionLabel;
    private final TextField field;

    public FilteredGridHeaderCell(String caption,
                                  HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>> valueChangeListener) {
        super();

        captionLabel = new Label(caption);
        field = getFilteredStringField(caption);
        field.addValueChangeListener(valueChangeListener);
        Image image = new Image("icons/filter.png", "filter");

        Button button = new Button(image);
        button.addClickListener(event -> changeFilterState(!field.isVisible()));

        initContextMenuEvent(this, contextEvent -> changeFilterState(true));

        add(captionLabel, field, button);
    }

    private void initContextMenuEvent(Component component, DomEventListener contextListener) {
        component.getElement().setAttribute("oncontextmenu", "return!1");
        component.getElement().addEventListener("contextmenu", contextListener);
    }

    private void changeFilterState(boolean showFilter) {
        captionLabel.setVisible(!showFilter);
        field.setVisible(showFilter);
        if (!showFilter) {
            field.clear();
        } else {
            field.focus();
        }
    }

    private TextField getFilteredStringField(String placeholder) {
        TextField field = new TextField();
        field.setVisible(false);
//        36px it is filter button size
        field.getElement().getStyle().set("width", "calc(100% - 36px)");
        field.setClearButtonVisible(true);
        field.setPlaceholder(placeholder);
        field.setValueChangeMode(ValueChangeMode.EAGER);
        return field;
    }

}