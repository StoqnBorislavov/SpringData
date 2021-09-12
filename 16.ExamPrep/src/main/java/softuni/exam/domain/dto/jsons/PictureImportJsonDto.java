package softuni.exam.domain.dto.jsons;


import com.google.gson.annotations.Expose;

public class PictureImportJsonDto {
    @Expose
    private String url;

    public PictureImportJsonDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
