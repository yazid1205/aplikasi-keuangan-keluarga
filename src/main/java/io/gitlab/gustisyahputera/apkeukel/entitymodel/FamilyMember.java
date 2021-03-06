package io.gitlab.gustisyahputera.apkeukel.entitymodel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;


@Table(name="member")
public class FamilyMember implements Serializable {

    public final static String createTableQuery =  // copied from definition.sql file
            "CREATE TABLE IF NOT EXISTS member (\n" +
            "       member_id  INTEGER PRIMARY KEY,\n" +
            "       full_name  TEXT    NOT NULL,\n" +
            "       birth_date TEXT,\n" +
            "       role       INTEGER DEFAULT 0, -- Enum(ORDINARY, ACCOUNTANT, CHIEF)\n" +
            "       pass_key   TEXT    DEFAULT NULL\n" +
            ");";
    public final static String dropTableQuery = "DROP TABLE IF EXISTS member";
    public final static String tableName = "member";

    public final static String memberIdColumn = "member_id";
    public final static String fullNameColumn = "full_name";
    public final static String birthDateColumn = "birth_date";
    public final static String roleColumn = "role";
    public final static String passkeyColumn = "pass_key";
    public final static String whereKeyClause = memberIdColumn + "=?";

    //region Constructors
    //==========================================================================
    public FamilyMember() {}

    public FamilyMember(String fullName, LocalDate birthDate,
                        Role role, String passkey) {
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setRole(role);
        this.setPassKey(passkey);
    }
    //endregion


    //region Properties
    //==========================================================================

    private int memberId;
    private String fullName;
    private LocalDate birthDate;
    private Role role;
    private String passKey;

    @Id
    @GeneratedValue
    @Column(name=memberIdColumn)
    public int getId() {
        return memberId;
    }

    @Column(name=fullNameColumn)
    public String getFullName() {
        return fullName;
    }

    @Transient
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Deprecated  // see [DATENORM]
    @Column(name=birthDateColumn)
    public String getBirthDate_() {
        return birthDate.toString();
    }

    @Transient
    public int getAge() {
        return Period.between(LocalDate.now(), this.birthDate).getYears();
    }

    @Enumerated  // to store the value as numeric in the database
    @Column(name=roleColumn)
    public Role getRole() {
        return role;
    }

    @Column(name=passkeyColumn)
    public String getPassKey() {
        return passKey;
    }

    public void setId(int memberId) {
        this.memberId = memberId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Deprecated  // see [DATENORM]
    public void setBirthDate_(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate);
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    /*
     * Notes
     *
     * [DATENORM] Norm loads date to pojo as String instead of LocalDate.
     * One trick for this situation is to make a pair of setter and getter
     * for a dummy property `birthDate_` which attached to the date
     * column in the database table.
     */
    //endregion


    //region Comparations
    //==========================================================================

    @Override
    public boolean equals(Object comparate_) {

        if (this == comparate_) {
            return true;
        }
        if (comparate_ == null) {
            return false;
        }
        if (this.getClass() != comparate_.getClass()) {
            return false;
        }
        FamilyMember comparate = (FamilyMember) comparate_;
        return this.hashCode() == comparate.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.memberId, this.fullName,
                this.birthDate, this.role, this.passKey);
    }
    //endregion

}
