import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by siyangzhang on 3/6/17.
 */
public class TestStudent {
    private IStudent student;
    private IAdmin admin;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.student = new Student();
        this.admin = new Admin();
        this.instructor = new Instructor();
    }

    @Test
    public void testRegisterForClass1() {
        this.admin.createClass("TestB",2046,"Jack",2);
        this.student.registerForClass("Mike","Math",2046);
        assertTrue(this.student.isRegisteredFor("Mike","Math",2046));
    }

    //Class does not exist
    @Test
    public void testRegisterForClass4() {
        this.admin.createClass("TestC",2046,"Jack",2);
        this.student.registerForClass("Mike","Math",2046);
        assertFalse("Class does not exist", this.admin.classExists("TestB",2046) &&
                this.student.isRegisteredFor("Mike","Math",2046));
    }

    //Met class capacity
    @Test
    public void testRegisterForClass5() {
        this.admin.createClass("TestB",2046,"Jack",1);
        this.student.registerForClass("Mike","Math",2046);
        this.student.registerForClass("Jane","Math",2046);
        assertFalse("Met class capacity", this.admin.classExists("TestB",2046) &&
                this.student.isRegisteredFor("Jane","Math",2046));
    }

    //Student name cannot be empty
    @Test
    public void testRegisterForClass2() {
        this.student.registerForClass("","Math",2017);
        assertFalse("Student name cannot be empty",
                this.student.isRegisteredFor("","Math",2017));
    }

    //Class name cannot be empty
    @Test
    public void testRegisterForClass3() {
        this.student.registerForClass("Mike","",2017);
        assertFalse("Class name cannot be empty",
                this.student.isRegisteredFor("Mike","",2017));
    }

    @Test
    public void testDropClass1() {
        this.admin.createClass("Math",2017,"Jack",10);
        this.student.registerForClass("Mike","Math",2017);
        if(this.admin.classExists("Math",2017) &&
                this.student.isRegisteredFor("Mike","Math",2017)){
            this.student.dropClass("Mike","Math",2017);
        }
        assertFalse(this.student.isRegisteredFor("Mike","Math",2017));
    }

    //Student is not registered
    @Test
    public void testDropClass2() {
        this.admin.createClass("Math",2017,"Jack",10);
        this.student.registerForClass("Mike","Art",2017);
        if(this.admin.classExists("Math",2017) &&
                this.student.isRegisteredFor("Mike","Math",2017)){
            this.student.dropClass("Mike","Math",2017);
        }
        assertFalse("Student is not registered", this.student.isRegisteredFor("Mike","Math",2017));
    }

    //Class has ended
    @Test
    public void testDropClass3() {
        this.admin.createClass("Art",2017,"Jack",10);
        this.student.registerForClass("Mike","Math",2017);
        if(this.admin.classExists("Math",2017) &&
                this.student.isRegisteredFor("Mike","Math",2017)){
            this.student.dropClass("Mike","Math",2017);
        }
        assertFalse("Class has ended", this.student.isRegisteredFor("Mike","Math",2017));
    }

    @Test
    public void testSubmitHomework1() {
        this.instructor.addHomework("Nick","Physics",2050,"project1","measurement");
        this.student.registerForClass("Jane","Physics",2050);
        this.admin.createClass("Physics",2050,"Nick",2);
        this.student.submitHomework("Jane","project1","answer","Physics",2050);
        assertTrue(this.student.hasSubmitted("Jane","project1","Physics",2050));
    }

    //Homework does not exist when submitting homework
    @Test
    public void testSubmitHomework2() {
        this.instructor.addHomework("Nick","Physics",2050,"project3","measurement");
        this.student.registerForClass("Jane","Physics",2050);
        this.admin.createClass("Physics",2050,"Nick",2);
        this.student.submitHomework("Jane","project1","answer","Physics",2050);
        assertFalse("Homework does not exist when submiting homework",
                this.instructor.homeworkExists("Physics",2050,"project1") &&
                this.student.isRegisteredFor("Jane","Physics",2050) &&
                this.admin.classExists("Physics",2050) &&
                this.student.hasSubmitted("Jane","project1","Physics",2050));
    }

    //Student is not registered when submitting homework
    @Test
    public void testSubmitHomework3() {
        this.instructor.addHomework("Nick","Physics",2050,"project1","measurement");
        this.student.registerForClass("Judy","Physics",2050);
        this.admin.createClass("Physics",2050,"Nick",2);
        this.student.submitHomework("Jane","project1","answer","Physics",2050);
        assertFalse("Student is not registered when submitting homework",
                this.instructor.homeworkExists("Physics",2050,"project1") &&
                        this.student.isRegisteredFor("Jane","Physics",2050) &&
                        this.admin.classExists("Physics",2050) &&
                        this.student.hasSubmitted("Jane","project1","Physics",2050));
    }

    //Class is not taught in the current year
    @Test
    public void testSubmitHomework4() {
        this.instructor.addHomework("Nick","Physics",2050,"project1","measurement");
        this.student.registerForClass("Jane","Physics",2050);
        this.admin.createClass("Physics",2055,"Nick",2);
        this.student.submitHomework("Jane","project1","answer","Physics",2050);
        assertFalse("Class is not taught in the current year",
                this.instructor.homeworkExists("Physics",2050,"project1") &&
                        this.student.isRegisteredFor("Jane","Physics",2050) &&
                        this.admin.classExists("Physics",2050) &&
                        this.student.hasSubmitted("Jane","project1","Physics",2050));
    }
}
