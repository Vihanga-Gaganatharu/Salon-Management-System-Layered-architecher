package lk.ijse.salon.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.salon.bo.custom.BookingBO;
import lk.ijse.salon.bo.custom.*;
import lk.ijse.salon.bo.custom.impl.*;
import lk.ijse.salon.dto.*;
import lk.ijse.salon.dto.tm.BookingTm;
import lk.ijse.salon.entity.Booking;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AppoinemntFormController implements Initializable {
    public ComboBox cmdCusid;
    public TextField txtCustomerName;
    public ComboBox cmbServiceId;
    public TextField txtServiceType;
    public TextField txtServicePrice;
    public TableColumn colcusId;
    public TableColumn colServieceId;
    public TableColumn colCustomerName;
    public TableColumn colServiceType;
    public TableColumn colServicePrice;
    public TableView tbl;
    public ComboBox cmbEmp;
    public TextField txtEmpName;
    public TextField txtAppoinemtTime;
    public DatePicker dtpDate;
    BookingBO bookingBO = new BookingBOImpl();
    CustomersBO customersBO = new CustomersBOImpl();
    EmployeesBO employeesBO = new EmployeesBOImpl();
    ServiceBO serviceBO = new ServiceBOImpl();
    ObservableList<BookingTm> list = FXCollections.observableArrayList();

    public void btnsaveAppoin(ActionEvent actionEvent) {
        BookingTm bookingTm = new BookingTm();
        bookingTm.setType(txtServiceType.getText());
        bookingTm.setName(txtCustomerName.getText());
        bookingTm.setPrice(txtServicePrice.getText());
        bookingTm.setCusId((String) cmdCusid.getValue());
        bookingTm.setSId((String) cmbServiceId.getValue());
        list.add(bookingTm);
        tbl.refresh();
    }

    public void bynBookingAppo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Booking dto = new Booking();
        dto.setB_id(nextId());
        dto.setDate(String.valueOf(dtpDate.getValue()));
        dto.setTime(txtAppoinemtTime.getText());
        //dto.setTime(new SimpleDateFormat("hh:mm:ss").format(new Date()));
        //dto.setDate(new SimpleDateFormat("yyyy-M-dd").format(new Date()));
        dto.setEmp_id((String) cmbEmp.getValue());
        dto.setC_id((String) cmdCusid.getValue());

        try {
            boolean b = bookingBO.placeOrder(dto, list);
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION,"Booking Confirme").show();
            }
        } catch (SQLException | ClassNotFoundException e ) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private String nextId() throws SQLException, ClassNotFoundException {
        return bookingBO.generateBookingId();
    }

    private void loadAllCustomer() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> customerDtos = customersBO.loadAllCustomers();
            for (CustomerDto dto : customerDtos) {
                obList.add(dto.getC_id());
            }
            cmdCusid.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadAllService() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ServiceDto> serList = serviceBO.loadAllService();

            for (ServiceDto serviceDto : serList) {
                obList.add(serviceDto.getService_id());
            }
            cmbServiceId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void cmbCusIdOnAction(ActionEvent event) {
        String id = String.valueOf(cmdCusid.getValue());
        try {
            CustomerDto dto = customersBO.searchCustomer(id);
            txtCustomerName.setText(dto.getFirst_name());

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbServiceIdOnAction(ActionEvent event) {
        String id = String.valueOf(cmbServiceId.getValue());
        try {
            ServiceDto dto = serviceBO.searchService(id);
            txtServicePrice.setText(dto.getPrice());
            txtServiceType.setText(dto.getService_type());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colcusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colServieceId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colServicePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbl.setItems(list);

        loadAllCustomer();
        loadAllService();
        loadAllEmployee();
    }
    private void loadAllEmployee() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> list = employeesBO.loadAllEmployees();
            for (EmployeeDto dto:list){
                obList.add(dto.getEmp_id());
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        cmbEmp.setItems(obList);
    }

    public void btnEmpIdOnAction(ActionEvent actionEvent) {
        try {
            EmployeeDto dto = employeesBO.searchEmployees((String) cmbEmp.getValue());
            txtEmpName.setText(dto.getFirst_name()+" "+dto.getLast_name());
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnCancel(ActionEvent actionEvent) {
    }
}
