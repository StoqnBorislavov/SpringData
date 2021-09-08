package softuni.exam.models.dtos.xmls;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportXmlDto {

    @XmlElement
    private int id;

    public CarImportXmlDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
