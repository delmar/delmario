package ca.delmar.api.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by jinw on 08/10/13.
 */
public class Notices implements Serializable {

    Collection<Notice> notices;

    public Collection<Notice> getNotices() {
        return notices;
    }

    public void setNotices(Collection<Notice> notices) {
        this.notices = notices;
    }
}
