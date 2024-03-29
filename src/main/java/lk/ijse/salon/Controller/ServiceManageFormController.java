package lk.ijse.salon.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.salon.bo.custom.ServiceBO;
import lk.ijse.salon.bo.custom.impl.ServiceBOImpl;
import lk.ijse.salon.dto.ServiceDto;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class ServiceManageFormController {
    @FXML
    private ComboBox<String> cmbServiceType;

    @FXML
    private TextField txtSerciceDescription;

    @FXML
    private TextField txtServiceId;

    @FXML
    private TextField txtServiceName;

    @FXML
    private TextField txtServicePrice;

    ServiceBO serviceBO = new ServiceBOImpl();

    public void initialize(){
        ObservableList oblist= FXCollections.observableArrayList("Nail",
                "Body",
                "Hair",
                "Foot",
                "Face",
                "Hand");
        cmbServiceType.setItems(oblist);
    }

    @FXML
    void btnServicesave(ActionEvent event) {
        String service_id = txtServiceId.getText();
        String pricee = txtServicePrice.getText();
        String service_type = cmbServiceType.getValue();
        String Name = txtServiceName.getText();
        String description = txtSerciceDescription.getText();

        boolean isServiceValidate = validateService();
        if (!isServiceValidate) {
            return;
        }
        var dto = new ServiceDto(service_id, pricee, service_type, description, Name);

        try {
            boolean isSaved = serviceBO.saveService(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Service Save Successfully").show();

            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateService() {
        String S_id = txtServiceId.getText();
        boolean sidValidation = Pattern.compile("[S][0-9]{3,}").matcher(S_id).matches();
        if (!sidValidation) {
            txtServiceId.setStyle("-fx-border-color: red;");
            return false;

        }else {
            txtServiceId.setStyle("-fx-border-color: #08ff00;");
        }

        String price = txtServicePrice.getText();
        boolean priceValidation = Pattern.compile("[0-9]{3,}").matcher(price).matches();
        if (!priceValidation){
            txtServicePrice.setStyle("-fx-border-color: #ff0000;");
            return false;
        }
        else{
            txtServicePrice.setStyle("-fx-border-color: #15ff00;");
        }

        String Name = txtServiceName.getText();
        boolean Namevalidation = Pattern.compile("[A-Za-z .]{3,}").matcher(Name).matches();
        if (!Namevalidation){
            txtServiceName.setStyle("-fx-border-color: #ff0000;");
            return false;
        }
        else{
            txtServiceName.setStyle("-fx-border-color: #15ff00;");
        }

        String descrition = txtSerciceDescription.getText();
        boolean desciptionvalidation = Pattern.compile("[A-Za-z .]{3,}").matcher(Name).matches();
        if (!desciptionvalidation){
            txtSerciceDescription.setStyle("-fx-border-color: #ff0000;");
            return false;
        }
        else{
            txtServiceName.setStyle("-fx-border-color: #15ff00;");
        }
        return true;
    }
 @FXML
    void btnServiceClear(ActionEvent event) {
     txtSerciceDescription.setText("");
     txtServiceName.setText("");
     txtServiceId.setText("");
     txtServicePrice.setText("");
    }

    @FXML
    void btnServiceDelete(ActionEvent event) {
        String service_id=txtServiceId.getText();
        try{
            boolean isDeleted=serviceBO.deleteService(service_id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Service Delete Sucessfully").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnServiceupdate(ActionEvent event) {
        String service_id = txtServiceId.getText();
        String pricee = txtServicePrice.getText();
        String service_type = cmbServiceType.getTypeSelector();
        String Name = txtServiceName.getText();
        String description = txtServiceName.getText();

        boolean isServiceValidate = validateService();
        if (!isServiceValidate) {
            return;
        }
        var dto = new ServiceDto(service_id, pricee, service_type, description, Name);

        try {
            boolean isUpdated = serviceBO.updateService(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Service Updateda yakow").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
