package com.app.LMS.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ELMSHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String dbName = "ELMS.db";
    private static final int dbVersion = 1;

    private static final String tableManager = "Manager";
    private static final String tableEmp = "Employee";
    private static final String tableLeave = "Leave";
    private static final String tableLeaveBackup = "LeaveBackup";
    private static final String tableAcceptedRequests = "AcceptedRequest";
    private static final String tableRejectedRequests = "RejectedRequest";

    private static final String m_col1_id = "m_id";
    private static final String m_col2_name = "m_name";
    private static final String m_col3_password = "m_password";

    private static final String e_col1_id = "e_id";
    private static final String e_col2_name = "e_name";
    private static final String e_col3_email = "e_mail";
    private static final String e_col4_password = "e_password";
    private static final String e_col5_phone = "e_phone";
    private static final String e_col6_address = "e_address";
    private static final String e_col7_gender = "e_gender";
    private static final String e_col8_dept = "e_department";
    private static final String e_col9_salary = "e_salary";
    private static final String e_col10_joiningDate = "e_joiningDate";
    private static final String e_col11_leaves = "e_leaves";

    private static final String l_col1_id = "_id";
    private static final String l_col2_eid = "le_id";
    private static final String l_col3_reason = "l_reason";
    private static final String l_col4_days = "l_leaves";
    private static final String l_col5_fromDate = "l_fromDate";
    private static final String l_col6_toDate = "l_toDate";

    private static final String bl_col1_id = "_id";
    private static final String bl_col2_eid = "ble_id";
    private static final String bl_col3_reason = "bl_reason";
    private static final String bl_col4_days = "bl_leaves";
    private static final String bl_col5_fromDate = "bl_fromDate";
    private static final String bl_col6_toDate = "bl_toDate";

    private static final String ar_col1_id = "_id";
    private static final String ar_col2_lid = "arl_id";
    private static final String ar_col3_eid = "are_id";
    private static final String ar_col4_reason = "ar_reason";

    private static final String rr_col1_id = "_id";
    private static final String rr_col2_lid = "rrl_id";
    private static final String rr_col3_eid = "rre_id";
    private static final String rr_col4_reason = "rr_reason";

    private static final String createManagerTable =
            "create table " + tableManager + "(" +
                    m_col1_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    m_col2_name + " VARCHAR(50), " +
                    m_col3_password + " VARCHAR(30) )";

    private static final String createEmpTable =
            "create table " + tableEmp + "(" +
                    e_col1_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    e_col2_name + " VARCHAR(50), " +
                    e_col3_email + " VARCHAR(30), " +
                    e_col4_password + " VARCHAR(30), " +
                    e_col5_phone + " VARCHAR(20), " +
                    e_col6_address + " VARCHAR(255), " +
                    e_col7_gender + " VARCHAR(30), " +
                    e_col8_dept + " VARCHAR(50), " +
                    e_col9_salary + " DOUBLE, " +
                    e_col10_joiningDate + " DATE, " +
                    e_col11_leaves + " INTEGER)";

    private static final String createLeaveTable =
            "create table " + tableLeave + "(" +
                    l_col1_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    l_col2_eid + " INTEGER, " +
                    l_col3_reason + " VARCHAR(255), " +
                    l_col4_days + " INTEGER, " +
                    l_col5_fromDate + " DATE, " +
                    l_col6_toDate + " DATE, " +
                    "FOREIGN KEY(" + l_col2_eid + ") REFERENCES " + tableEmp + "(" + e_col1_id + "))";

    private static final String createLeaveBackupTable =
            "create table " + tableLeaveBackup + "(" +
                    bl_col1_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    bl_col2_eid + " INTEGER, " +
                    bl_col3_reason + " VARCHAR(255), " +
                    bl_col4_days + " INTEGER," +
                    bl_col5_fromDate + " DATE, " +
                    bl_col6_toDate + " DATE, " +
                    "FOREIGN KEY(" + bl_col2_eid + ") REFERENCES " + tableEmp + "(" + e_col1_id + "))";

    private static final String createAcceptedRequestsTable =
            "create table " + tableAcceptedRequests + "(" +
                    ar_col1_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    ar_col2_lid + " INTEGER, " +
                    ar_col3_eid + " INTEGER, " +
                    ar_col4_reason + " VARCHAR(255), " +
                    "FOREIGN KEY(" + ar_col2_lid + ") REFERENCES " + tableLeave + "(" + l_col1_id + "), " +
                    "FOREIGN KEY(" + ar_col3_eid + ") REFERENCES " + tableEmp + "(" + e_col1_id + "))";


    private static final String createRejectedRequestsTable =
            "create table " + tableRejectedRequests + "(" +
                    rr_col1_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    rr_col2_lid + " INTEGER, " +
                    rr_col3_eid + " INTEGER, " +
                    rr_col4_reason + " VARCHAR(255), " +
                    "FOREIGN KEY(" + rr_col2_lid + ") REFERENCES " + tableLeave + "(" + l_col1_id + "), " +
                    "FOREIGN KEY(" + rr_col3_eid + ") REFERENCES " + tableEmp + "(" + e_col1_id + "))";

    public ELMSHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createManagerTable);
        db.execSQL(createEmpTable);
        db.execSQL(createLeaveTable);
        db.execSQL(createAcceptedRequestsTable);
        db.execSQL(createRejectedRequestsTable);
        db.execSQL(createLeaveBackupTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tableManager);
        db.execSQL("drop table if exists "+tableEmp);
        db.execSQL("drop table if exists "+tableLeave);
        db.execSQL("drop table if exists "+tableAcceptedRequests);
        db.execSQL("drop table if exists "+tableRejectedRequests);
        db.execSQL("drop table if exists "+tableLeaveBackup);
        onCreate(db);
    }

    public Boolean addManager(String username, String password){
        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(m_col2_name, username);
        cv.put(m_col3_password, password);

        long check = db.insert(tableManager, null, cv);
        return (check > 0);
    }

    public Boolean addEmployee(String name, String email, String password, String phone, String address, String gender, String dept, double salary, String joinDate, int leaves){
        db= this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(e_col2_name, name);
        cv.put(e_col3_email, email);
        cv.put(e_col4_password, password);
        cv.put(e_col5_phone, phone);
        cv.put(e_col6_address, address);
        cv.put(e_col7_gender, gender);
        cv.put(e_col8_dept, dept);
        cv.put(e_col9_salary, salary);
        cv.put(e_col10_joiningDate, joinDate);
        cv.put(e_col11_leaves, leaves);

        long check = db.insert(tableEmp,null,cv);
        return (check > 0);
    }

    public boolean checkManagerName(String name) {
        String[] cols = {m_col2_name};
        String[] match = {name};

        db = this.getReadableDatabase();
        Cursor cursor = db.query(tableManager, cols, m_col2_name + "=?", match, null, null, null);
        return (cursor.getCount() > 0);
    }

    public boolean checkEmployeeName(String name) {
        String[] cols = {e_col2_name};
        String[] match = {name};

        db = this.getReadableDatabase();
        Cursor cursor = db.query(tableEmp, cols, e_col3_email + "=?", match, null, null, null);
        return (cursor.getCount() > 0);
    }

    public Boolean checkManagerLogin(String username, String password){
        String[] cols = {m_col2_name, m_col3_password};
        String[] selectArgs = {username, password};

        db= this.getReadableDatabase();

        Cursor cur = db.query(tableManager, cols, m_col2_name + "=? AND " + m_col3_password + "=?", selectArgs, null, null, null);

        return (cur.getCount() > 0);
    }

    public Cursor checkEmployeeLogin(String username, String password){
        String[] cols = {e_col1_id, e_col2_name, e_col4_password, e_col11_leaves};
        String[] match = {username, password};

        db = this.getReadableDatabase();
        Cursor cur = db.query(tableEmp, cols, e_col3_email + "=? AND " + e_col4_password + "=?", match, null, null, null);
        return cur;
    }

    public Boolean addLeave(int id, String reason , int leaves, String fromDate, String toDate){

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(l_col2_eid, id);
        cv.put(l_col3_reason, reason);
        cv.put(l_col4_days, leaves);
        cv.put(l_col5_fromDate, fromDate);
        cv.put(l_col6_toDate, toDate);

        ContentValues bcv = new ContentValues();
        bcv.put(bl_col2_eid, id);
        bcv.put(bl_col3_reason, reason);
        bcv.put(bl_col4_days, leaves);
        bcv.put(bl_col5_fromDate, fromDate);
        bcv.put(bl_col6_toDate, toDate);

        db.insert(tableLeaveBackup, null, bcv);
        long check = db.insert(tableLeave,null,cv);
        return (check > 0);
    }

    public Cursor viewEmployees(){
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableEmp;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getLeaveRequests() {
        db = this.getReadableDatabase();
        //String MY_QUERY = "SELECT " + tableLeave + "." + l_col1_id + ", " + tableLeave + "." + l_col3_reason + ", " + tableLeave + "." + l_col4_days + " FROM " + tableLeave + " INNER JOIN " + tableEmp + " ON " + tableEmp + "." + e_col1_id + " = " + tableLeave + "." + l_col2_eid;

        String query = "SELECT e.e_name, l.l_reason, l.l_leaves, e_leaves, e_salary, e.e_id, l._id FROM Leave l INNER JOIN Employee e ON l.le_id = e.e_id";

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void updateLeaves(String emp_id, int t_leaves){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(e_col11_leaves,t_leaves);

        db.update(tableEmp, cv, e_col1_id + "=?", new String[]{emp_id});
    }

    public void addToAcceptedLeaves(String eid, String lid, String reason){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ar_col2_lid, lid);
        cv.put(ar_col3_eid, eid);
        cv.put(ar_col4_reason, reason);

        db.insert(tableAcceptedRequests, null, cv);
    }

    public void addToRejectedLeaves(String eid, String lid, String reason){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(rr_col2_lid, lid);
        cv.put(rr_col3_eid, eid);
        cv.put(rr_col4_reason, reason);

        db.insert(tableRejectedRequests, null, cv);
    }

    public void deleteLeaveRequest(String id){
        db = this.getWritableDatabase();
        db.delete(tableLeave, l_col1_id + "=?", new String[]{id});
    }

    public Cursor checkIntoAcceptedLeaves(String eid){
        db= this.getReadableDatabase();

        String query = "SELECT ar._id, ar.ar_reason, e.e_salary FROM AcceptedRequest ar INNER JOIN Employee e ON ar.are_id = e.e_id WHERE ar.are_id = " + eid;

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor checkIntoRejectedLeaves(String eid){
        db = this.getReadableDatabase();

        String query = "SELECT rr._id, rr.rr_reason, e.e_salary FROM RejectedRequest rr INNER JOIN Employee e ON rr.rre_id = e.e_id WHERE rr.rre_id = " + eid;

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}