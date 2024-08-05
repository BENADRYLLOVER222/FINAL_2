package first.second.third.fuckmylife.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "preview")
public class Preview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "preview_preview_image",
            joinColumns = @JoinColumn(name = "preview_id"),
            inverseJoinColumns = @JoinColumn(name = "preview_image_id")
    )
    private List<PreviewImage> previewImageList;

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PreviewImage> getPreviewImageList() {
        return previewImageList;
    }

    public void setPreviewImageList(List<PreviewImage> previewImageList) {
        this.previewImageList = previewImageList;
    }
}