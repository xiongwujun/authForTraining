package com.gdhengdian.auth.common;


/**
 * 权限代码
 * 开头为１，表示只有教师有权限 001
 * 开头为２，表示只有学生权限 010
 * 开头为３，表示只有管理员没权限 011
 * 开头为４，表示只有管理员有权限 100
 * 开头为５，表示只有教师没权限 101
 * 开头为６，表示只有管理和教师没有权限 110
 * 开头为７，表示三者皆有权限 111
 *
 * @author junmov guoyancheng@junmov.cn
 * @date 2018-10-10
 */
public final class AuthCodeConstant {

    public final static int USER_INFO_MODIFY = 700;
    /**
     * ClassController
     */
    public final static int LIST_CLASSES = 701;
    public final static int GET_CLASS = 702;
    public final static int ADD_CLASS = 400;
    public final static int DELETE_CLASS = 401;
    public final static int UPDATE_CLASS = 402;
    public final static int ADD_CLASS_BY_FILE = 403;
    public final static int EXPORT_CLASS_FILE = 404;
    /**
     * CourseController//7 & 1=
     */
    public final static int LIST_COURSES = 703;
    public final static int GET_COURSES = 704;
    public final static int ADD_COURSES = 405;
    public final static int DELETE_COURSES = 406;
    public final static int UPDATE_COURSES = 407;
    public final static int ADD_COURSES_BY_FILE = 408;
    public final static int EXPORT_COURSES_FILE = 409;

    /**
     * ReportController
     */
    public final static int LIST_ALL_REPORTS = 410;
    public final static int LIST_REPORTS_OF_TASK = 300;
    public final static int GET_REPORT_IN_USER_IN_TASK = 301;
    public final static int GET_REPORT_BY_ID = 302;
    public final static int ADD_REPORT = 201;
    public final static int DELETE_REPORT = 411;
    public final static int UPDATE_REPORT = 303;

    /**
     * ScheduleController
     */
    public final static int GET_SCHEDULES_OF_STUDENT = 202;
    public final static int GET_SCHEDULES_OF_TEACHER = 100;
    public final static int LIST_ALL_SCHEDULES = 412;
    public final static int GET_SCHEDULE = 705;
    public final static int ADD_SCHEDULE = 413;
    public final static int DELETE_SCHEDULE = 414;
    public final static int UPDATE_SCHEDULE = 415;
    public final static int EXPORT_SCHEDULES_FILE = 416;

    /**
     * ScoreController
     */
    public final static int EXPORT_SCORES_FILE = 101;
    public final static int LIST_SCHEDULE_SCORES = 303;
    public final static int LIST_STUDENT_SCHEDULE_SCORES = 304;

    /**
     * TaskController
     */
    public final static int LIST_TASKS = 706;
    public final static int GET_TASK = 305;
    public final static int ADD_TASK = 102;
    public static final int DELETE_TASK = 417;
    public static final int UPDATE_TASK = 103;

    /**
     * userController
     */
    public final static int LIST_USERS = 500;
    public static final int GET_USER = 707;
    public static final int DELETE_USER = 418;
    public final static int ADD_STUDENT_BY_FILE = 419;
    public final static int ADD_TEACHER_BY_FILE = 420;
    public final static int EXPORT_STUDENTS_FILE = 421;
    public final static int EXPORT_TEACHERS_FILE = 422;
    public static final int ADD_SCHEDULE_FILE = 423;
    public static final int SAVE_USER_TOKEN = 424;
}
