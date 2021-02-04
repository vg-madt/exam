package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DatabaseInitClass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myDatabase.db";

    public static final String TABLE_NAME = "userregister";
    public static final String ID = "ID";
    public static final String usernameCol = "username";
    public static final String passwordCol = "password";
    public static final String score = "score";

    public static final String QUES_TABLE_NAME = "questionTable";
    public static final String TEST_NAME = "testname";
    public static final String QUESTION = "question";
    public static final String QID = "QID";
    public static final String OPTION1 = "answer1";
    public static final String OPTION2 = "answer2";
    public static final String OPTION3 = "answer3";
    public static final String ANSWER = "answerNumber";


    public static final String  SCORE_TABLE_NAME = "scorelist";



    public DatabaseInitClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//creates the tables on Database creation
        db.execSQL("CREATE TABLE userregister(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE questionTable(QID INTEGER PRIMARY KEY AUTOINCREMENT,testname TEXT, question TEXT, answer1 TEXT, answer2 TEXT, answer3 TEXT, answerNumber INTEGER)");
        db.execSQL("CREATE TABLE scorelist(SID INTEGER PRIMARY KEY AUTOINCREMENT,testname TEXT, username TEXT, score INTEGER DEFAULT -1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(("DROP TABLE IF EXISTS " +TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +QUES_TABLE_NAME));
        db.execSQL(("DROP TABLE IF EXISTS " +SCORE_TABLE_NAME));
        onCreate(db);
    }

//gets all the test values to display in spinner
    public ArrayList<String> getAllTests(){
        ArrayList<String> test = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + QUES_TABLE_NAME;
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String u;
                u = c.getString(c.getColumnIndex("testname"));
                if(!test.contains(u)){
                    test.add(u);
                }

            }while(c.moveToNext());
        }
        c.close();
        return test;
    }

//adds questions to the database
    public long addToDb(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean check = checkIfQuesExists(question);
        if(check == false) {
            ContentValues cv = new ContentValues();
            cv.put(TEST_NAME, question.getTestname());
            cv.put(QUESTION, question.getQuestion());
            cv.put(OPTION1, question.getOption1());
            cv.put(OPTION2, question.getOption2());
            cv.put(OPTION3, question.getOption3());
            cv.put(ANSWER, question.getAnswerNumber());
            long res = db.insert(QUES_TABLE_NAME, null, cv);
            return res;
        }
        else return -1;
    }

    //checks if the question exists in the database already
    public boolean checkIfQuesExists(Question question){
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+QUES_TABLE_NAME +" WHERE testname = " +
                "'"+question.getTestname()+"' and question = '"+question.getQuestion()+"' and answer1 = '"+question.getOption1()+
                "' and answer2 = '"+question.getOption2()+"' and answer3 = '"+question.getOption3()+"' and answerNumber = '"+question.getAnswerNumber()+"'";
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //adds the score of the user on completing a test
    public long addScore(String testName, String username, Integer score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("testname",testName);
        cv.put("username", username);
        cv.put("score", score);
        long res = db.insert(SCORE_TABLE_NAME, null, cv);
        return res;
    }

    //adds the user and password on registration
    public long addUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("username",username);
        contentvalues.put("password",password);
        long res = db.insert("userregister",null,contentvalues);
        //db.close();
        return res;

    }

    //validates the user and password on login
    public boolean validate(String username, String password){
        String [] columns = { ID };
        SQLiteDatabase db = this.getReadableDatabase();
        String select = usernameCol + "=? " + "and " + passwordCol + "=?";
        String [] selectionArguments = {username, password};
        Cursor cursor = db.query(TABLE_NAME,columns,select,selectionArguments,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }

//gets all the questions of a test from database
    public ArrayList<Question> getAllTheQuestions(String testname){
        ArrayList<Question> questions = new ArrayList<>();
        SQLiteDatabase ndb = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + QUES_TABLE_NAME + " WHERE testname = '"+testname+"'";
        Cursor c = ndb.rawQuery(SELECT_QUERY, null);

        if(c.moveToFirst()){
            do{
                Question q = new Question();
                q.setQuestion(c.getString(c.getColumnIndex(QUESTION)));
                q.setOption1(c.getString(c.getColumnIndex(OPTION1)));
                q.setOption2(c.getString(c.getColumnIndex(OPTION2)));
                q.setOption3(c.getString(c.getColumnIndex(OPTION3)));
                q.setAnswerNumber(c.getInt(c.getColumnIndex(ANSWER)));
                q.setTestname(c.getString(c.getColumnIndex(TEST_NAME)));
                questions.add(q);
            }while (c.moveToNext());
        }
        c.close();
        return questions;
    }

//gets all the score of the users
    public ArrayList<Integer> getAllScore(String testname){
        ArrayList<Integer> scores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + SCORE_TABLE_NAME+" WHERE testname = '"+testname+"'";
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                Integer s;
                s = c.getInt(c.getColumnIndex("score"));
                scores.add(s);

            }while(c.moveToNext());
        }
        c.close();
        return scores;
    }

    //gets all the users
    public ArrayList<String> getAllUsers(String testname){
        ArrayList<String> scores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + SCORE_TABLE_NAME+" WHERE testname = '"+testname+"'";
        Cursor c = db.rawQuery(SELECT_QUERY,null);

        if(c.moveToFirst()){
            do{
                String s;
                s = c.getString(c.getColumnIndex("username"));
                scores.add(s);

            }while(c.moveToNext());
        }
        c.close();
        return scores;
    }

    //checks if a test is already taken by an user
    public boolean checkIfWritten(String testName,String user){
        String [] columns = { "SID" };
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+SCORE_TABLE_NAME +" WHERE username = '"+user+"' and testname = '"+testName+"'";
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}


