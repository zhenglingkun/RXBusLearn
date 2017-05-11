package zlk.com.rxbuslearn.pinnedheaderlistview;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyiq on 2016/12/30.
 */

public class CustomerBean implements Serializable {

    private long id; // 联系人id
    private String contactName; // 联系人姓名
    private String contactBz; // 联系人类型,C-客户,S-供应商,A-客户和供应商 = ['C', 'S', 'A']
    private double debt;
    private int bills;
    private String phone;
    private String remark;
    private String date;
    private String namePinyin;
    private long createUser; // 创建用户id
    private List<String> phones;
    private String letter;
    private boolean isShowLetter;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isShowLetter() {
        return isShowLetter;
    }

    public void setShowLetter(boolean showLetter) {
        isShowLetter = showLetter;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(long createUser) {
        this.createUser = createUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public int getBills() {
        return bills;
    }

    public void setBills(int bills) {
        this.bills = bills;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContactBz() {
        return contactBz;
    }

    public void setContactBz(String contactBz) {
        this.contactBz = contactBz;
    }
}
