package springdatajsonprocesing.exercise.domain.dtos.exportdtoxml;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarRootExportDtoForToyota {

    @XmlElement(name = "car")
    private List<CarExportDtoForToyota> cars;

    public CarRootExportDtoForToyota() {
    }

    public List<CarExportDtoForToyota> getCars() {
        return cars;
    }

    public void setCars(List<CarExportDtoForToyota> cars) {
        this.cars = cars;
    }
}
