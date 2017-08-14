package com.example.welsley.volunteach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.welsley.volunteach.model.Branch;
import com.example.welsley.volunteach.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Welsley on 8/4/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    //public static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "volunteach.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_BRANCH = "branch";
    private static final String TABLE_HOURS = "hours";
    private static final String TABLE_COURSE = "course";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PASSWORD = "passWord";
    private static final String KEY_USER_FULLNAME = "fullName";
    private static final String KEY_USER_EMAIL = "email";

    // Branch Table Columns
    private static final String KEY_BRANCH_ID = "id";
    private static final String KEY_BRANCH_CODE = "code";
    private static final String KEY_BRANCH_NAME = "branchName";
    private static final String KEY_BRANCH_ADDRESS = "address";
    private static final String KEY_BRANCH_TELEPHONE = "telephone";
    private static final String KEY_BRANCH_POSTALCODE = "postalCode";

    // Hours Table Columns
    private static final String KEY_HOURS_ID = "id";
    private static final String KEY_HOURS_BRANCH_ID_FK = "branchId";
    private static final String KEY_HOURS_MONDAY = "monday";
    private static final String KEY_HOURS_TUESDAY = "tuesday";
    private static final String KEY_HOURS_WEDNESDAY = "wednesday";
    private static final String KEY_HOURS_THURSDAY = "thursday";
    private static final String KEY_HOURS_FRIDAY = "friday";
    private static final String KEY_HOURS_SATURDAY = "saturday";
    private static final String KEY_HOURS_SUNDAY = "sunday";

    // Course Table Columns
    private static final String KEY_COURSE_ID = "id";
    private static final String KEY_COURSE_NAME = "name";
    private static final String KEY_COURSE_USER_ID_FK = "userId";
    private static final String KEY_COURSE_BRANCH_ID_FK = "branchId";
    private static final String KEY_COURSE_TIME = "time";
    private static final String KEY_COURSE_DURATION = "duration";
    private static final String KEY_COURSE_DESC = "description";
    private static final String KEY_COURSE_INSTRUCTOR_ID = "instId";
    private static final String KEY_COURSE_BOOKED = "booked";

    private static DatabaseHelper sInstance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //initial database creation
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //Singleton instance method to make database instance across application lifecycle
    //Prevents leaking of activity context
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    //creation of database tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_PASSWORD + " TEXT," +
                KEY_USER_FULLNAME + " TEXT," +
                KEY_USER_EMAIL + " TEXT" +
                ")";

        String CREATE_BRANCH_TABLE = "CREATE TABLE " + TABLE_BRANCH +
                "(" +
                KEY_BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_BRANCH_CODE + " TEXT," +
                KEY_BRANCH_NAME + " TEXT," +
                KEY_BRANCH_ADDRESS + " TEXT," +
                KEY_BRANCH_TELEPHONE + " TEXT," +
                KEY_BRANCH_POSTALCODE + " TEXT" +
                ")";

        String CREATE_HOURS_TABLE = "CREATE TABLE " + TABLE_HOURS +
                "(" +
                KEY_HOURS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_HOURS_BRANCH_ID_FK + " INTEGER REFERENCES " + TABLE_BRANCH + "," + // Define a foreign key
                KEY_HOURS_MONDAY + " TEXT," +
                KEY_HOURS_TUESDAY + " TEXT," +
                KEY_HOURS_WEDNESDAY + " TEXT," +
                KEY_HOURS_THURSDAY + " TEXT," +
                KEY_HOURS_FRIDAY + " TEXT," +
                KEY_HOURS_SATURDAY + " TEXT," +
                KEY_HOURS_SUNDAY + " TEXT" +
                ")";

        String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE +
                "(" +
                KEY_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_COURSE_NAME + " TEXT," +
                KEY_COURSE_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                KEY_COURSE_BRANCH_ID_FK + " INTEGER REFERENCES " + TABLE_BRANCH + "," + // Define a foreign key
                KEY_COURSE_TIME + " TEXT," +
                KEY_COURSE_DURATION + " TEXT," +
                KEY_COURSE_DESC + " TEXT," +
                KEY_COURSE_INSTRUCTOR_ID + " TEXT," +
                KEY_COURSE_BOOKED + " TEXT" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_BRANCH_TABLE);
        db.execSQL(CREATE_HOURS_TABLE);
        db.execSQL(CREATE_COURSE_TABLE);
    }

    //update database if changes take place with tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCH);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOURS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
            onCreate(db);
        }
    }

    //CRUD Operations
    //USER Functions
    //addUser
    public void addUser(String username, String password, String name, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, username);
        values.put(KEY_USER_PASSWORD, password);
        values.put(KEY_USER_FULLNAME, name);
        values.put(KEY_USER_EMAIL, email);

        //long id = db.insert(TABLE_USERS, null, values);
        db.insert(TABLE_USERS, null, values);
        db.close();

        //Log.d(TAG, "user inserted" + id)
    }

    //getUser
    public boolean getUser(String username, String password){
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " where " + KEY_USER_NAME + " = "
                + "'"+username+"'" + " and " + KEY_USER_PASSWORD + " = " +"'"+password+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }
    //getUser
    public int getUserId(String username, String password){
        String selectQuery = "SELECT " + KEY_USER_ID + " FROM " + TABLE_USERS + " where " + KEY_USER_NAME + " = "
                + "'"+username+"'" + " and " + KEY_USER_PASSWORD + " = " +"'"+password+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        int userId = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            userId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return userId;
    }

    //getUser
    public String getUserFullname(int id){
        String selectQuery = "SELECT " + KEY_USER_FULLNAME + " FROM " + TABLE_USERS + " where " + KEY_USER_ID + " = "
                + "'"+id+"'";
        String result = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor user = db.rawQuery(selectQuery, null);
        if(user.moveToFirst()){
            result = user.getString(0);
        }

        return result;
    }

    //Get List Of Branches
    public List<Branch> getListContents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Branch branch = null;
        List<Branch> branchList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_BRANCH;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                branch = new Branch(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                branchList.add(branch);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return branchList;
    }

    //Get single Branch
    public Branch getBranch(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_BRANCH + " WHERE " + KEY_BRANCH_ID + " = "
                + "'"+id+"'";
        Branch branch = null;

        Cursor oneBranch = db.rawQuery(selectQuery, null);
        if(oneBranch != null && oneBranch.moveToFirst()){
            branch = new Branch(oneBranch.getInt(0), oneBranch.getString(1), oneBranch.getString(2), oneBranch.getString(3), oneBranch.getString(4), oneBranch.getString(5));
        }
        oneBranch.close();

        return branch;
    }

    //Get Branch hours
    public ArrayList<String> getBranchHours(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> hourList = new ArrayList<>();
        int i = 0;

        String selectQuery = "SELECT "+ KEY_HOURS_MONDAY + ", " + KEY_HOURS_TUESDAY + ", " + KEY_HOURS_WEDNESDAY + ", "
                + KEY_HOURS_THURSDAY + ", " + KEY_HOURS_FRIDAY + ", " + KEY_HOURS_SATURDAY + ", " + KEY_HOURS_SUNDAY
                + " FROM " + TABLE_HOURS + " WHERE " + KEY_HOURS_BRANCH_ID_FK + " = "
                + "'"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            while(i != cursor.getColumnCount()){
                hourList.add(cursor.getString(i));
                i++;
            }
        }
        cursor.close();

        return hourList;
    }

    //Get single Course
    public Course getCourse(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE + " WHERE " + KEY_COURSE_ID + " = "
                + "'"+id+"'";
        Course course = null;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null && cursor.moveToFirst()){
            course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8));
        }
        cursor.close();

        return course;
    }

    //Get List Of Courses
    public List<Course> getAllCourses(){
        SQLiteDatabase db = this.getReadableDatabase();
        Course course = null;
        int f = 0;

        List<Course> courseList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COURSE + " WHERE " + KEY_COURSE_BOOKED + " = " + "'"+f+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8));
                courseList.add(course);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return courseList;
    }

    //Get List Of User Booked Courses
    public List<Course> getAllUserCourses(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Course course = null;

        List<Course> courseList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_COURSE + " WHERE " + KEY_COURSE_USER_ID_FK + " = " + "'"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8));
                courseList.add(course);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return courseList;
    }

    //Get List Of Courses by Branch
    public List<Course> getCourseByBranch(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Course course = null;
        int f = 0;
        List<Course> courseList = new ArrayList<>();

        //String returns all courses belong to a single branch that have not been signed up for
        String selectQuery = "SELECT * FROM " + TABLE_COURSE + " WHERE " + KEY_COURSE_BRANCH_ID_FK + " = " +
                "'"+id+"'" + " AND " + KEY_COURSE_BOOKED + " = " + "'"+f+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                course = new Course(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8));
                courseList.add(course);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return courseList;
    }

    public void bookCourse(int userId, int courseId){
        SQLiteDatabase db = this.getWritableDatabase();
        int t = 1;
        String selectQuery = "UPDATE " + TABLE_COURSE + " SET " + KEY_COURSE_USER_ID_FK + " = " +
                "'"+userId+"'" + ", " + KEY_COURSE_BOOKED + " = " + "'"+t+"'" + " WHERE " + KEY_COURSE_ID + " = " + "'"+courseId+"'";

        db.execSQL(selectQuery);
        db.close();
    }

}
