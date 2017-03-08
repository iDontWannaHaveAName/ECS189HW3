import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by siyangzhang on 3/6/17.
 */
public class TestAdmin {
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    //Test classExists()
    @Test
    public void testMakeClass1() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        assertTrue(this.admin.classExists("Test1", 2017));
    }

    //no instructor can be assigned to more than two courses in a year
    @Test
    public void testMakeClass3() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.admin.createClass("Test2", 2017, "Instructor1", 15);
        this.admin.createClass("Test3", 2017, "Instructor1", 15);
        assertFalse("no instructor can be assigned to more than two courses in a year",
                this.admin.classExists("Test3", 2017));
    }

    //Calendar year in which the course is to be taught, cannot be in the past
    @Test
    public void testMakeClass4() {
        this.admin.createClass("Test1", 2016, "Instructor1", 15);
        assertFalse("Calendar year in which the course is to be taught, cannot be in the past",
                this.admin.classExists("Test", 2016));
    }

    //The className/year pair must be unique
    @Test
    public void testMakeClass5() {
        this.admin.createClass("Test2", 2017, "Instructor1", 30);
        this.admin.createClass("Test2", 2017, "Instructor2", 30);
        assertFalse("The className/year pair must be unique",
                this.admin.getClassInstructor("Test2",2017).equals("Instructor2"));
    }

    //Class name cannot be empty
    @Test
    public void testMakeClass6() {
        this.admin.createClass("", 2017, "Instructor1", 15);
        assertFalse("Class name cannot be empty",
                this.admin.classExists("", 2017));
    }

    //Instructor name cannot be empty
    @Test
    public void testMakeClass7() {
        this.admin.createClass("Test1", 2017, "", 15);
        assertFalse("Instructor name cannot be empty",
                this.admin.getClassInstructor("", 2017).equals(""));
    }

    //Year cannot be before 2017
    @Test
    public void testMakeClass8() {
        this.admin.createClass("TestA", 2000, "LecturerA", 15);
        assertFalse("Year cannot be before 2017",
                this.admin.classExists("TestA", 2000));
    }

    //Test getClassCapacity()
    @Test
    public void testMakeClass9() {
        this.admin.createClass("Test2", 2017, "Instructor2", 30);
        assertTrue(this.admin.getClassCapacity("Test2", 2017) == 30);
    }

    //Test getClassInstructor()
    @Test
    public void testMakeClass10() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        assertTrue(this.admin.getClassInstructor("Test1", 2017).equals("Instructor1"));
    }

    //Capacity cannot be less than 0
    @Test
    public void testMakeClass11() {
        this.admin.createClass("Test2", 2017, "Instructor2", -1);
        assertFalse("Capacity cannot be less than 0",
                this.admin.getClassCapacity("Test2", 2017) <= 0);
    }

    //Test changeCapacity()
    @Test
    public void testMakeClass13() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.admin.changeCapacity("Test1", 2017, 50);
        assertTrue(this.admin.getClassCapacity("Test1",2017) == 50);
    }

    //New capacity of this class, must be at least equal to the number of students enrolled
    @Test
    public void testMakeClass14() {
        this.admin.createClass("Test1", 2017, "Instructor1", 3);
        this.student.registerForClass("Mike","Test1",2017);
        this.student.registerForClass("Jane","Test1",2017);
        this.admin.changeCapacity("Test1",2017,1);
        assertFalse("New capacity of this class, must be at least equal to the number of students enrolled",
                this.admin.getClassCapacity("Test1",2017) < 2);
    }
}
