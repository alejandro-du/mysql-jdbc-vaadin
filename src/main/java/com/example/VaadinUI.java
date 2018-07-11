package com.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("")
public class VaadinUI extends VerticalLayout {

    private CustomerService service;
    private Customer customer;

    private ComboBox<CustomerService> serviceComboBox = new ComboBox<>("Service");

    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private Button save = new Button("Save", e -> saveCustomer());

    public VaadinUI(CustomerService1 service1, CustomerService2 service2) {

        serviceComboBox.setItems(service1, service2);
        serviceComboBox.setItemLabelGenerator(s -> s.getClass().getSimpleName());
        serviceComboBox.setWidth("220px");
        serviceComboBox.addValueChangeListener(e -> {
            service = e.getValue();
            updateGrid();
        });

        grid.setColumns("firstName", "lastName");
        grid.addSelectionListener(e -> updateForm());

        add(serviceComboBox, grid, firstName, lastName, save);
        setMargin(true);
        setSpacing(true);

        serviceComboBox.setValue(service1);
    }

    private void updateGrid() {
        grid.setItems(service.findAll());
        setFormVisible(false);
    }

    private void updateForm() {
        if (grid.asSingleSelect().isEmpty()) {
            setFormVisible(false);
        } else {
            customer = grid.asSingleSelect().getValue();
            Binder<Customer> binder = new Binder<>(Customer.class);
            binder.bindInstanceFields(this);
            binder.setBean(customer);
            setFormVisible(true);
        }
    }

    private void setFormVisible(boolean visible) {
        firstName.setVisible(visible);
        lastName.setVisible(visible);
        save.setVisible(visible);
    }

    private void saveCustomer() {
        service.update(customer);
        updateGrid();
    }

}
