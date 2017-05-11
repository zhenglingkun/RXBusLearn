package zlk.com.rxbuslearn.pinnedheaderlistview;

import java.util.List;

/**
 * Created by ice on 2017/5/11 0011.
 * this is a xxx for
 */

public class PinnedHeaderBean {

    private List<CustomerBean> section;
    private String header;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<CustomerBean> getSection() {
        return section;
    }

    public void setSection(List<CustomerBean> section) {
        this.section = section;
    }

}
