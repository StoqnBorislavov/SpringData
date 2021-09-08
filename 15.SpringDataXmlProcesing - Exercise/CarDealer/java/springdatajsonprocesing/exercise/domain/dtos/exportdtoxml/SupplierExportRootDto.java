package springdatajsonprocesing.exercise.domain.dtos.exportdtoxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierExportRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierExportDto> suppliers;

    public SupplierExportRootDto() {
    }

    public List<SupplierExportDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierExportDto> suppliers) {
        this.suppliers = suppliers;
    }
}
