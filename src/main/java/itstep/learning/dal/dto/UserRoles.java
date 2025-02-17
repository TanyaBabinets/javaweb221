
package itstep.learning.dal.dto;

import com.google.inject.Inject;
import com.google.inject.Singleton;

//@Singleton
public class UserRoles {
    private String id;
    private String description;
    private boolean canCreate;
            private boolean canRead;
            private boolean canUpdate;
            private boolean canDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCanCreate() {
        return canCreate;
    }

    public void setCanCreate(boolean canCreate) {
        this.canCreate = canCreate;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
//@Inject
    public UserRoles(String id, String description, boolean canCreate, boolean canRead, boolean canUpdate, boolean canDelete) {
        this.id = id;
        this.description = description;
        this.canCreate = canCreate;
        this.canRead = canRead;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;
    }
}
