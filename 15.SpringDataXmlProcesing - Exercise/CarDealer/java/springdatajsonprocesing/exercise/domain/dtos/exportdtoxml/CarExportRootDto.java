package springdatajsonprocesing.exercise.domain.dtos.exportdtoxml;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportRootDto {

    @XmlElement(name = "car")
    private List<CarExportDtoXml> cars;

    public CarExportRootDto() {
    }

    public List<CarExportDtoXml> getCars() {
        return cars;
    }

    public void setCars(List<CarExportDtoXml> cars) {
        this.cars = cars;
    }
}
