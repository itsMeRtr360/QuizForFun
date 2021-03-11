package com.example.quizforfun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class databasehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "quizData";
    public static final String TABLE_NAME = "userTable";
    public static final String TABLE_NAME1="scoreTable";
    public static final String TABLE_NAME2="GeoQuestions";
    public static final String TABLE_NAME3="LitQuestions";
    public static final String TABLE_NAME4="MathQuestions";
    public static final String COL1 = "UserID";
    public static final String COL2 = "Name";
    public static final String COL3 = "Email";
    public static final String COL4 = "Password";
    public static final String COL5= "Area";
    public static final String COL6="Score";
    public static final String COL7="Date";
    public static final String COL8="QUESTION";
    public static final String COL9="OPTIONS1";
    public static final String COL10="OPTIONS2";
    public static final String COL11="OPTIONS3";
    public static final String COL12="OPTIONS4";
    public static final String COL13="ANS";

    private static final String CREATE_TABLE_USER_TABLE="CREATE TABLE "+TABLE_NAME+
            "(USERID VARCHAR PRIMARY KEY,"
            +"NAME,EMAIL VARCHAR,PASSWORD);";
    private static final String CREATE_TABLE_SCORE_TABLE=" CREATE TABLE "+TABLE_NAME1+
            "(USERID,"+
        "AREA,SCORE,DATE);";
    private static final String CREATE_GEOTABLE_QUESTIONS_TABLE="CREATE TABLE "+TABLE_NAME2+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, QUESTION, OPTIONS1, OPTIONS2, OPTIONS3, OPTIONS4, ANS)";
    private static final String CREATE_LITTABLE_QUESTIONS_TABLE="CREATE TABLE "+TABLE_NAME3+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, QUESTION, OPTIONS1, OPTIONS2, OPTIONS3, OPTIONS4, ANS)";
    private static final String CREATE_MATHTABLE_QUESTIONS="CREATE TABLE "+TABLE_NAME4+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT,QUESTION,ANS)";

    public databasehelper(@Nullable Context context, @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USER_TABLE);
        db.execSQL(CREATE_TABLE_SCORE_TABLE);
        db.execSQL(CREATE_GEOTABLE_QUESTIONS_TABLE);
        db.execSQL(CREATE_LITTABLE_QUESTIONS_TABLE);
        db.execSQL(CREATE_MATHTABLE_QUESTIONS);
        loadGeoQuestions(db);
        loadLitQuestions(db);
        loadMathQuestions(db);
    }

    private void loadMathQuestions(SQLiteDatabase db) {
        ContentValues contentValues=new ContentValues();
        String[] Questions={
                "2*6/3+5-2 = ?",
                "6519 + 1697 = ?",
                "2876 + 6049 = ?",
                "4835 + 3981 = ?",
                "3874 + 2936 = ?",
                "4432 - 2044 = ?",
                "7895 - 4576 = ?",
                "9643 - 4312 = ?",
                "765 * 482 = ?",
                "986 * 134 = ?",
                "756 * 983 = ?",
                "132 * 189 = ?",
                "8448 / 64 = ?",
                "101376 / 132 = ?",
                "62699 / 91 = ?"
                            };
        String[] ans={
                "7",
                "8216",
                "8925",
                "8816",
                "6810",
                "2388",
                "3319",
                "5331",
                "368730",
                "132214",
                "743148",
                "24948",
                "132",
                "768",
                "689"
        };
        int i=0;
        while(i<15)
        {
            contentValues.put(COL8, Questions[i]);
            contentValues.put(COL13, ans[i]);
            db.insert(TABLE_NAME4,null,contentValues);
            i++;
        }
    }

    public void createTable(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }
    public boolean insertDataUserTable(String userID,String name,String password, String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,userID);
        contentValues.put(COL2,name);
        contentValues.put(COL3,email);
        contentValues.put(COL4,password);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public void loadGeoQuestions(SQLiteDatabase myDB)
    {
        ContentValues contentValues=new ContentValues();
        String[] Questions={
                "What is the capital of Thailand?",
                "Which is the highest mountain in the world by elevation?",
                "Where might one find the Acropolis?",
                "Near what Chinese city are the terracotta warriors buried?",
                "Where is Hadrian’s Wall?",
                "Where is the Temple of the Tooth found?",
                "In what city would one find the Brandenburg Gate?",
                "Where might one find a leaning tower?",
                "The highest mountain in Iran is:",
                "On what ocean does Oman lie?",
                "How many continents are there?",
                "What is the largest continent by land area?",
                "How many oceans are there?",
                "Which is the largest country in the world by land area?",
                "Which is the largest country in the world by population?"
        };
        String[] op1={
                "A. Hongkong",
                "A. Mount everest",
                "A. Rome",
                "A) Beijing",
                "A) England",
                "A) India",
                "A) Vienna",
                "A) Pisa",
                "A) Mount Everest",
                "A) Atlantic",
                "A) 4",
                "A) Antarctica",
                "A) 5",
                "A) China",
                "A) India"};
        String[] op2={
                "B. Bangkok",
                "B. K2",
                "B. Beijing",
                "B) Xian",
                "B) Italy",
                "B) Thailand",
                "B) Paris",
                "B) Katmandu",
                "B) Pamir Mountain",
                "B) Indian",
                "B) 8",
                "B) Asia",
                "B) 3",
                "B) Brazil",
                "B) Great Britain"
        };
        String[] op3={
                "C. Abudhabi",
                "C. Makalu",
                "C. Moscow",
                "C) Nanjing",
                "C) Yemen",
                "C) France",
                "C) Berlin",
                "C) Shanghai",
                "C) Mount Damavand",
                "C) Southern",
                "C) 7",
                "C) Africa",
                "C) 7",
                "C) United States",
                "C) China"
        };
        String[] op4={
                "D. phuket",
                "D. Broad Peak",
                "D. Athens",
                "D) Shanghai",
                "D) France",
                "D) Sri Lanka",
                "D) London",
                "D) Montreal",
                "D) Lenin Peak",
                "D) Pacific",
                "D) 6",
                "D) Europe",
                "D) 4",
                "D) Russia",
                "D) United States"
        };
        String[] ans={
                "B. Bangkok",
                "A. Mount everest",
                "D. Athens",
                "B) Xian",
                "A) England",
                "D) Sri Lanka",
                "C) Berlin",
                "A) Pisa",
                "B) Mount Damavand",
                "B) Indian",
                "C) 7",
                "B) Asia",
                "A) 5",
                "D) Russia",
                "C) China"
        };
        int i=0;
        while(i<15) {
            contentValues.put(COL8, Questions[i]);
            contentValues.put(COL9, op1[i]);
            contentValues.put(COL10, op2[i]);
            contentValues.put(COL11, op3[i]);
            contentValues.put(COL12, op4[i]);
            contentValues.put(COL13, ans[i]);
            myDB.insert(TABLE_NAME2,null,contentValues);
            i++;
        }
    }
    public void loadLitQuestions (SQLiteDatabase myDB)
    {
        ContentValues contentValues=new ContentValues();
        String[] Questions={
                "Who translated the entire Mahabharata into English?",
                "Who is called the father of English classical comedy?",
                "The dictionary definition of a word.",
                "A statement which can contain two or more meanings.",
                "A statement which lessens or minimizes the importance of what is meant.",
                "Characters that change during the course of a story.",
                "Characters that have many traits or aspects to their personality.",
                "In literature, an extreme form of realism that developed in France in the 19th Century.",
                "Language that is native to people (as opposed to learned language) and is used as everyday speech.",
                "The method a writer uses to reveal the personality of a character in a literary work.",
                "A writer creates unreal characters and situations and asks the reader to pretend that they are real in a fictional work.",
                "An author's choice of words.",
                "A brief quotation whcih appears at the beginning of a literary work.",
                "An overused expression.",
                "Systematic study of the nature of literature and the methods for analyzing literature."};
        String[] op1={
                "A. RK Narayan",
                "A. Thomas Heywood",
                "A. Conotation",
                "A. Ambiguity",
                "A. Verisimilitude",
                "A.Flat Character",
                "A. Flat Character",
                "A. Naturalism",
                "A. Jarg",
                "A. Characterization",
                "A. Narrator",
                "A. Epic",
                "A. Epic",
                "A. Motif",
                "A. In Medias Res"
        };
        String[] op2={
                "B. P.Lal",
                "B. Thomas Middleton",
                "B. Figure of Speech",
                "B. Anecdote",
                "B. Understatement",
                "B.Round Character",
                "B. Round Character",
                "B. Malapropism",
                "B. Verisimilitude",
                "B. Simile",
                "B. Verisimilitude",
                "B. Diction",
                "B. Epigraph",
                "B. Pun",
                "B. Hamartia"
        };
        String[] op3={
                "C. Vikram Seth",
                "C. Ben Jonson",
                "C. Denotation",
                "C. Epigraph",
                "C. Colloquialism",
                "C.Static Character",
                "C. Static Character",
                "C. Modernism",
                "C. Figure of Speech",
                "C. Metaphor",
                "C. Point of View",
                "C. Syntax",
                "C. Euphemism",
                "C. Oxymoron",
                "C. Hubris"
        };
        String[] op4={
                "D. None of the mentioned",
                "D. William Shakespeare",
                "D. Epithet",
                "D. Foil",
                "D. Hyperbole",
                "D. Dynamic Character",
                "D. Dynamic Character",
                "D. Postmodernism",
                "D. Vernacular",
                "D. Foil",
                "D. Vernacular",
                "D. Genre",
                "D. Epithet",
                "D. Cliché",
                "D. Literary Theory"
        };
        String[] ans={
                "B. P.Lal",
                "C. Ben Jonson",
                "C. Denotatio",
                "A. Ambiguity",
                "B. Understatement",
                "D. Dynamic Character",
                "B. Round Character",
                "A. Naturalism",
                "D. Vernacular",
                "A. Characterization",
                "B. Verisimilitude",
                "B. Diction",
                "B. Epigraph",
                "D. Cliché",
                "D. Literary Theory"};
        int i=0;
        while(i<15) {
            contentValues.put(COL8, Questions[i]);
            contentValues.put(COL9, op1[i]);
            contentValues.put(COL10, op2[i]);
            contentValues.put(COL11, op3[i]);
            contentValues.put(COL12, op4[i]);
            contentValues.put(COL13, ans[i]);
            myDB.insert(TABLE_NAME3,null,contentValues);
            i++;
        }
    }
    public boolean insertDataScoreTable(String userID, String area, String sysDate, int score) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,userID);
        contentValues.put(COL5,area);
        contentValues.put(COL6,score);
        contentValues.put(COL7, sysDate);
        long result=db.insert(TABLE_NAME1,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public int getTotalScore(String userName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT SUM(SCORE) AS TOTALSCORE FROM SCORETABLE " +
                "WHERE USERID='"+userName+"'",null);
        if(cursor.moveToFirst())
        {
            return cursor.getInt(0);
        }
        else
            return 0;
    }
    public Cursor viewAllRecordsUserTable()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
    public Cursor getLitQuestion(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME3+" WHERE ID="+id,null);
        return res;
    }
    public Cursor getGeoQuestion(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME2+" WHERE ID='"+id+"'",null);
        return res;
    }
    public Cursor getMathQuestion(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME4+" WHERE ID='"+id+"'",null);
        return res;
    }
    public Cursor viewAllRecordsScoreTable()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME1,null);
        return res;
    }
    public Cursor viewAllRecordsScoreSortedByDate(String userName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME1+" WHERE USERID='"+userName+"' ORDER BY DATE ",null);
        return res;
    }
    public Cursor viewAllRecordsScoreByArea(String userName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME1+" WHERE USERID='"+userName+"' ORDER BY AREA ",null);
        return res;
    }
    public Cursor viewRecordUserTable(String userName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String[] projection=new String[]{COL1,COL2,COL3,COL4};
        String selection=COL1+" = ?";
        String[] selectionArguments=new String[]{userName};
        Cursor res=db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArguments,
                null,
                null,
                null
        );
        return res;
    }
    public Cursor viewRecordsUserTableByEmail(String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String[] projection=new String[]{COL1,COL2,COL3,COL4};
        String selection=COL3+" = ?";
        String[] selectionArguments=new String[]{email};
        Cursor res=db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArguments,
                null,
                null,
                null
        );
        return res;
    }
    public Cursor viewRecordScoreTable(String userName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String[] projection=new String[]{COL1,COL5,COL6,COL7};
        String selection=COL1+" = ?";
        String[] selectionArguments=new String[]{userName};
        Cursor res=db.query(
                TABLE_NAME1,
                projection,
                selection,
                selectionArguments,
                null,
                null,
                null
        );
        return res;

    }
    public boolean updateUserTable(String userName,@Nullable String name,@Nullable String email,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put(COL1,userName);
        contentvalues.put(COL2,name);
        contentvalues.put(COL3,email);
        contentvalues.put(COL4,password);
        db.update(TABLE_NAME,contentvalues,"USERID=?",new String[]{userName});
        return true;
    }


}
