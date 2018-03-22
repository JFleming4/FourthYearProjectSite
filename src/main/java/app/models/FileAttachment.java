package app.models;

import javax.persistence.*;

@Entity
public class  FileAttachment {
    /**
     * Enumeration of 4th Year Project required files to be submitted
     */
    public enum FileAttachmentType {
        PROPOSAL(0),
        DRAFT(1),
        FINAL_REPORT(2);

        private int type;

        FileAttachmentType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        /**
         * Gets the enum value based on an integer type
         * @param type The type integer
         * @return The enumeration instance associated with the integer type
         */
        public static FileAttachmentType getFileAttachmentType (int type) {
            for (FileAttachmentType t: FileAttachmentType.values()) {
                if(type == t.getType())
                    return t;
            }

            return null;
        }

        /**
         * @param type The request type integer
         * @return True if the type integer corresponds to a valid FileAttachmentType enumeration
         */
        public static boolean isValidFileAttachmentType(int type) {
            return getFileAttachmentType(type) != null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String assetUrl;
    private FileAttachmentType projectAssetType;

    @ManyToOne
    private Project project;

    public FileAttachment(String assetUrl, FileAttachmentType projectAssetType, Project project) {
        this.assetUrl = assetUrl;
        this.projectAssetType = projectAssetType;
        setProject(project);
    }


    public FileAttachment() {}

    /**
     * Compare an unknown object to this FileAttachment object
     * @param obj Unknown object
     * @return Boolean whether or not the objects are the same
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof FileAttachment)) return false;

        FileAttachment file = (FileAttachment) obj;

        return id == file.id
                && assetUrl.equals(file.assetUrl)
                && projectAssetType.equals(file.projectAssetType)
                && project.equals(file.project);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetUrl() {
        return assetUrl;
    }

    public void setAssetUrl(String assetUrl) {
        this.assetUrl = assetUrl;
    }

    public FileAttachmentType getProjectAssetType() {
        return projectAssetType;
    }

    public void setProjectAssetType(FileAttachmentType projectAssetType) {
        this.projectAssetType = projectAssetType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        if (project == null) return;
        this.project = project;
        this.project.addFile(this);
    }
}
