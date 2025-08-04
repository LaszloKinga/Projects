package edu.bbte.idde.lkim2156.desktop.presentation;

import edu.bbte.idde.lkim2156.backend.dao.NotFoundException;
import edu.bbte.idde.lkim2156.backend.model.Webshop;
import edu.bbte.idde.lkim2156.backend.service.WebshopService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class WebshopTable {
    private static JTextField orderDateField = new JTextField();
    private static JTextField addressField = new JTextField();
    private static JTextField totalAmountField = new JTextField();
    private static JTextField paymentMethodField = new JTextField();
    private static JCheckBox shippedCheckBox = new JCheckBox();
    private static WebshopService webshopService = new WebshopService();
    private static DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Order Date", "Address", "Total Amount", "Payment Method", "Shipped"},
            0);
    private static JTable table = new JTable(tableModel);
    private static JTextField idField = new JTextField();
    private static JButton updateButton = new JButton("Update Order");
    private static JButton deleteButton = new JButton("Delete Order");
    private static JButton addButton = new JButton("Add Order");

    public WebshopTable() {

        JFrame frame = new JFrame("Webshop Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(960, 750);
        frame.setLayout(new BorderLayout());


        JScrollPane scrollPane = new JScrollPane(this.table);
        frame.add(scrollPane, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(this.idField);

        formPanel.add(new JLabel("Order Date:"));
        formPanel.add(this.orderDateField);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(this.addressField);

        formPanel.add(new JLabel("Total Amount:"));
        formPanel.add(this.totalAmountField);

        formPanel.add(new JLabel("Payment Method:"));
        formPanel.add(this.paymentMethodField);

        formPanel.add(new JLabel("Shipped:"));
        formPanel.add(this.shippedCheckBox);


        Color red = new Color(240, 139, 139);
        Color green = new Color(193, 240, 139);
        Color blue = new Color(139, 240, 213);
        this.addButton.setBackground(green);
        this.updateButton.setBackground(blue);
        this.deleteButton.setBackground(red);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(this.addButton);
        buttonPanel.add(this.updateButton);
        buttonPanel.add(this.deleteButton);


        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);


        frame.setVisible(true);

    }

    public void buttonListener() {

        this.listAllOrders();

        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        this.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
    }


    public void listAllOrders() {
        this.tableModel.setRowCount(0);
        List<Webshop> webshops = this.webshopService.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Webshop webshop : webshops) {
            this.tableModel.addRow(new Object[]{
                    webshop.getId(),
                    webshop.getOrderDate().format(formatter),
                    webshop.getAddress(),
                    webshop.getTotalAmount(),
                    webshop.getPaymentMethod(),
                    webshop.isShipped()
            });
        }
    }


    public void addOrder() {
        Webshop webshop = new Webshop();
        try {
            // String datum -> LocalDate konverzio
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate orderDate = LocalDate.parse(this.orderDateField.getText(), formatter);
            webshop.setOrderDate(orderDate);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
            return;
        }
        webshop.setAddress(this.addressField.getText());
        webshop.setTotalAmount(Double.parseDouble(this.totalAmountField.getText()));
        webshop.setPaymentMethod(this.paymentMethodField.getText());
        webshop.setShipped(this.shippedCheckBox.isSelected());
        this.webshopService.create(webshop);
        listAllOrders();
    }

    public void updateOrder() {

        if (this.idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the ID of the order to update.");
            return;
        }
        try {
            Integer id = Integer.parseInt(this.idField.getText());
            Webshop webshop = this.webshopService.findById(id);
            if (webshop == null) {
                JOptionPane.showMessageDialog(null, "Order with ID " + id + " not found.");
                return;
            }
            // A beviteli mezok alapjan frissitunk
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate orderDate = LocalDate.parse(this.orderDateField.getText(), formatter);
                webshop.setOrderDate(orderDate);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
                return;
            }

            webshop.setAddress(this.addressField.getText());
            webshop.setTotalAmount(Double.parseDouble(this.totalAmountField.getText()));
            webshop.setPaymentMethod(this.paymentMethodField.getText());
            webshop.setShipped(this.shippedCheckBox.isSelected());
            this.webshopService.updateById(id, webshop);
            listAllOrders();
            JOptionPane.showMessageDialog(null, "Order updated successfully.");
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, "Order with this ID not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID. Please enter a valid number.");
        }
    }


    public void deleteOrder() {
        try {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow != -1) {
                Integer id = (Integer) this.tableModel.getValueAt(selectedRow, 0);
                this.webshopService.deleteById(id);
                listAllOrders();
            }
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, "Order with this ID can't be deleted.");
        }
    }


}