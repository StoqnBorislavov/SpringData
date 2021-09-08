package springdatajsonprocesing.exercise.domain.dtos.exportdtoxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerExportToSalesRootDto {

    @XmlElement(name = "customer")
    private List<CustomerExportToSalesDto> customers;

    public CustomerExportToSalesRootDto() {
    }

    public List<CustomerExportToSalesDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerExportToSalesDto> customers) {
        this.customers = customers;
    }
}
